package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.modular.system.model.MessageFile;
import com.stylefeng.guns.modular.system.dao.MessageFileMapper;
import com.stylefeng.guns.modular.system.service.IMessageFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息有关文件表 服务实现类
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
@Service
public class MessageFileServiceImpl extends ServiceImpl<MessageFileMapper, MessageFile> implements IMessageFileService {

}
