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
import com.stylefeng.guns.modular.system.model.MessageFile;
import com.stylefeng.guns.modular.system.service.IMessageFileService;

/**
 * 信息附件管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 01:11:13
 */
@Controller
@RequestMapping("/messageFile")
public class MessageFileController extends BaseController {

    private String PREFIX = "/system/messageFile/";

    @Autowired
    private IMessageFileService messageFileService;

    /**
     * 跳转到信息附件管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "messageFile.html";
    }

    /**
     * 跳转到添加信息附件管理
     */
    @RequestMapping("/messageFile_add")
    public String messageFileAdd() {
        return PREFIX + "messageFile_add.html";
    }

    /**
     * 跳转到修改信息附件管理
     */
    @RequestMapping("/messageFile_update/{messageFileId}")
    public String messageFileUpdate(@PathVariable Integer messageFileId, Model model) {
        MessageFile messageFile = messageFileService.selectById(messageFileId);
        model.addAttribute("item",messageFile);
        LogObjectHolder.me().set(messageFile);
        return PREFIX + "messageFile_edit.html";
    }

    /**
     * 获取信息附件管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return messageFileService.selectList(null);
    }

    /**
     * 新增信息附件管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MessageFile messageFile) {
        messageFileService.insert(messageFile);
        return SUCCESS_TIP;
    }

    /**
     * 删除信息附件管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageFileId) {
        messageFileService.deleteById(messageFileId);
        return SUCCESS_TIP;
    }

    /**
     * 修改信息附件管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MessageFile messageFile) {
        messageFileService.updateById(messageFile);
        return SUCCESS_TIP;
    }

    /**
     * 信息附件管理详情
     */
    @RequestMapping(value = "/detail/{messageFileId}")
    @ResponseBody
    public Object detail(@PathVariable("messageFileId") Integer messageFileId) {
        return messageFileService.selectById(messageFileId);
    }
}
