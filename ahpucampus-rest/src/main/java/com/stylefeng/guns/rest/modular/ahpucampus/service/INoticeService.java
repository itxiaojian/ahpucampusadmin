package com.stylefeng.guns.rest.modular.ahpucampus.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Notice;

import java.util.Map;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 获取关于菜单信息
     */

    Map<String,Object> getAboutInfo();
}
