package com.stylefeng.guns.rest.modular.ahpucampus.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Message;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
public interface IMessageService extends IService<Message> {

    int save4primarykey(Message message);

}
