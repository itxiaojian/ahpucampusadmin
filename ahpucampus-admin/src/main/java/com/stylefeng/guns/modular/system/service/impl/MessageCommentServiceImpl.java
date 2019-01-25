package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.MessageComment;
import com.stylefeng.guns.modular.system.dao.MessageCommentMapper;
import com.stylefeng.guns.modular.system.service.IMessageCommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
