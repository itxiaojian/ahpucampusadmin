package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.MessageCommentMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageComment;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 消息评论表 服务实现类
 * </p>
 *
 * @author sliu123
 * @since 2019-01-25
 */
@Service
public class MessageCommentServiceImpl extends ServiceImpl<MessageCommentMapper, MessageComment> implements IMessageCommentService {

    @Resource
    private MessageCommentMapper messageCommentMapper;

    @Override
    public int insert4primarykey(MessageComment messageComment){
        messageCommentMapper.insert4primarykey(messageComment);
        return messageComment.getId();
    }
}
