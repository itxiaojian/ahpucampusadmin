package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.MessageComment;
import com.stylefeng.guns.modular.system.service.IMessageCommentService;

/**
 * 帖子评论控制器
 *
 * @author fengshuonan
 * @Date 2019-01-25 11:52:07
 */
@Controller
@RequestMapping("/messageComment")
public class MessageCommentController extends BaseController {

    private String PREFIX = "/system/messageComment/";

    @Autowired
    private IMessageCommentService messageCommentService;

    /**
     * 跳转到帖子评论首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "messageComment.html";
    }

    /**
     * 跳转到添加帖子评论
     */
    @RequestMapping("/messageComment_add")
    public String messageCommentAdd() {
        return PREFIX + "messageComment_add.html";
    }

    /**
     * 跳转到修改帖子评论
     */
    @RequestMapping("/messageComment_update/{messageCommentId}")
    public String messageCommentUpdate(@PathVariable Integer messageCommentId, Model model) {
        MessageComment messageComment = messageCommentService.selectById(messageCommentId);
        model.addAttribute("item",messageComment);
        LogObjectHolder.me().set(messageComment);
        return PREFIX + "messageComment_edit.html";
    }

    /**
     * 获取帖子评论列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return messageCommentService.selectList(null);
    }

    /**
     * 新增帖子评论
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MessageComment messageComment) {
        messageCommentService.insert(messageComment);
        return SUCCESS_TIP;
    }

    /**
     * 删除帖子评论
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageCommentId) {
        messageCommentService.deleteById(messageCommentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改帖子评论
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MessageComment messageComment) {
        messageCommentService.updateById(messageComment);
        return SUCCESS_TIP;
    }

    /**
     * 帖子评论详情
     */
    @RequestMapping(value = "/detail/{messageCommentId}")
    @ResponseBody
    public Object detail(@PathVariable("messageCommentId") Integer messageCommentId) {
        return messageCommentService.selectById(messageCommentId);
    }
}
