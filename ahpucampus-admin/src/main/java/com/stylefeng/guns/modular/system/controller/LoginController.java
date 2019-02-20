package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.code.kaptcha.Constants;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.exception.InvalidKaptchaException;
import com.stylefeng.guns.core.common.exception.WeiboBingdingException;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.node.MenuNode;
import com.stylefeng.guns.core.shiro.EasyTypeToken;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.ApiMenuFilter;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.weibo4j.Oauth;
import com.stylefeng.guns.core.weibo4j.Share;
import com.stylefeng.guns.core.weibo4j.http.AccessToken;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IMenuService;
import com.stylefeng.guns.modular.system.service.INoticeService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;

    @Autowired
    private INoticeService noticeService;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userService.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * weibo登录
     *
     * @return
     */
    @RequestMapping(value = "/weibo/login", method = RequestMethod.GET)
    public String login(Model model,String code) {
        logger.info("dayin yixia jinlai l ");
        String tips = "系统异常，请稍后重试";
        String weiboUid = "";
        try{
            if (StringUtils.isEmpty(code)) {
                logger.info("StringUtils.isEmpty(code)");
                tips = "code为空，重新登陆";
            } else {
                Subject currentUser = SecurityUtils.getSubject();
                if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
                    //已登录
                    logger.info("已登录");
                    tips =  "success";
                } else {
                    Oauth oauth = new Oauth();
                    AccessToken accessToken = oauth.getAccessTokenByCode(code);
                    Wrapper<User> userWrapper = new EntityWrapper<>();
                    weiboUid = accessToken.getUid();
                    userWrapper.eq("weiboUid",weiboUid);
                    User user = userService.selectOne(userWrapper);
                    if (user != null && !StringUtils.isEmpty(user.getPassword())) {
                        //已绑定后台账号
                        UsernamePasswordToken token = new EasyTypeToken(user.getName());
                        currentUser.login(token);
                        ShiroUser shiroUser = ShiroKit.getUser();
                        super.getSession().setAttribute("shiroUser", shiroUser);
                        super.getSession().setAttribute("username", shiroUser.getAccount());

                        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

                        ShiroKit.getSession().setAttribute("sessionFlag", true);
                        logger.info("/已绑定后台账号");
                        tips = "success";
                        String accesstoken = accessToken.getAccessToken();

                        logger.info("开始进入发微博啦");
                        Share share = new Share(accesstoken);

                        JSONObject jsonObject = share.Share("我登陆成功啦,查看。http://www.weibo.com",null,null);
                        logger.info("发微博返回{}",jsonObject);

                    } else {
                        //未绑定后台账号
                        logger.info("/未绑定后台账号");
                        tips = "binding";
                    }

                }
            }
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                logger.error("Unable to get the access token.");
                tips = "WeiboException:401,Unable to get the access token";
            } else {
                logger.error("WeiboException{}.",e);
                tips = "WeiboException:"+e.getStatusCode()+","+e.getMessage();
                e.printStackTrace();
            }
        } catch (ExcessiveAttemptsException e) {
            logger.error("登录失败{}", e.getMessage());//登录失败次数过多
            tips = "weibo登录失败次数过多";
        } catch (LockedAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已被锁定
            tips = "帐号已被锁定";
        } catch (DisabledAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已被禁用
            tips = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已过期
            tips = "帐号已过期";
        } catch (Exception e) {
            logger.error("登录失败{}", e.getMessage());
        }finally {
            logger.info("finally-tips{}",tips);
            if("success".equals(tips)){
                return REDIRECT + "/";
            }else if("binding".equals(tips)){
                model.addAttribute("weiboUid", weiboUid);
                model.addAttribute("weiboCode", code);
                model.addAttribute("bindingCode", MD5Util.encrypt(weiboUid));
                return "/binding.html";
            }else{
                model.addAttribute("tips", tips);
                return "/login.html";
            }

        }
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");
        String weiboUid = super.getPara("weiboUid");
        String bindingCode = super.getPara("bindingCode");

        //绑定微博校验uid是否被篡改
        if(StringUtils.isNotEmpty(weiboUid)&&StringUtils.isNotEmpty(bindingCode)){
            weiboUid = weiboUid.trim();
            bindingCode = bindingCode.trim();
            if(!bindingCode.equals(MD5Util.encrypt(weiboUid))){
                throw new WeiboBingdingException();
            }
        }

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new EasyTypeToken(username, password);

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        //更新weibouid进用户表
        if(StringUtils.isNotEmpty(weiboUid)&&StringUtils.isNotEmpty(bindingCode)){
            Wrapper<User> userWrapper = new EntityWrapper<>();
            userWrapper.eq("account",username);
            User user = userService.selectOne(userWrapper);
            user.setWeiboUid(weiboUid);
            userService.update(user,userWrapper);
        }

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 游客跳转到黑板
     */
    @RequestMapping("/weibo/blackboard")
    public String blackboard(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //已登录
            logger.info("已登录");
        } else {

            Subject currentUser_ = ShiroKit.getSubject();
            UsernamePasswordToken token = new EasyTypeToken("youke", "111111");
            currentUser_.login(token);
        }

        return REDIRECT + "/blackboard";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
