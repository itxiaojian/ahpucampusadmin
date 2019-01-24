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
import com.stylefeng.guns.modular.system.model.VisitorLogs;
import com.stylefeng.guns.modular.system.service.IVisitorLogsService;

/**
 * 浏览记录控制器
 *
 * @author fengshuonan
 * @Date 2019-01-24 20:07:19
 */
@Controller
@RequestMapping("/visitorLogs")
public class VisitorLogsController extends BaseController {

    private String PREFIX = "/system/visitorLogs/";

    @Autowired
    private IVisitorLogsService visitorLogsService;

    /**
     * 跳转到浏览记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "visitorLogs.html";
    }

    /**
     * 跳转到添加浏览记录
     */
    @RequestMapping("/visitorLogs_add")
    public String visitorLogsAdd() {
        return PREFIX + "visitorLogs_add.html";
    }

    /**
     * 跳转到修改浏览记录
     */
    @RequestMapping("/visitorLogs_update/{visitorLogsId}")
    public String visitorLogsUpdate(@PathVariable Integer visitorLogsId, Model model) {
        VisitorLogs visitorLogs = visitorLogsService.selectById(visitorLogsId);
        model.addAttribute("item",visitorLogs);
        LogObjectHolder.me().set(visitorLogs);
        return PREFIX + "visitorLogs_edit.html";
    }

    /**
     * 获取浏览记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return visitorLogsService.selectList(null);
    }

    /**
     * 新增浏览记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(VisitorLogs visitorLogs) {
        visitorLogsService.insert(visitorLogs);
        return SUCCESS_TIP;
    }

    /**
     * 删除浏览记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer visitorLogsId) {
        visitorLogsService.deleteById(visitorLogsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改浏览记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(VisitorLogs visitorLogs) {
        visitorLogsService.updateById(visitorLogs);
        return SUCCESS_TIP;
    }

    /**
     * 浏览记录详情
     */
    @RequestMapping(value = "/detail/{visitorLogsId}")
    @ResponseBody
    public Object detail(@PathVariable("visitorLogsId") Integer visitorLogsId) {
        return visitorLogsService.selectById(visitorLogsId);
    }
}
