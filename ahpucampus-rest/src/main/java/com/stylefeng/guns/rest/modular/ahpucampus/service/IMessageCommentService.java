package com.stylefeng.guns.rest.modular.ahpucampus.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageComment;

/**
 * <p>
 * 消息评论表 服务类
 * </p>
 *
 * @author sliu123
 * @since 2019-01-25
 */
public interface IMessageCommentService extends IService<MessageComment> {

    int insert4primarykey(MessageComment messageComment);

}
