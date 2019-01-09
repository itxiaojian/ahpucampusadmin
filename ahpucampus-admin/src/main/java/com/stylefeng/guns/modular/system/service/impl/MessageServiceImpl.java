package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.Message;
import com.stylefeng.guns.modular.system.dao.MessageMapper;
import com.stylefeng.guns.modular.system.service.IMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author sliu123
 * @since 2018-12-26
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
