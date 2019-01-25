package com.stylefeng.guns.rest.modular.ahpucampus.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageComment;

/**
 * <p>
 * 消息评论表 Mapper 接口
 * </p>
 *
 * @author sliu123
 * @since 2019-01-25
 */
public interface MessageCommentMapper extends BaseMapper<MessageComment> {

    int insert4primarykey(MessageComment messageComment);

}
