/**
 * 初始化帖子评论详情对话框
 */
var MessageCommentInfoDlg = {
    messageCommentInfoData : {}
};

/**
 * 清除数据
 */
MessageCommentInfoDlg.clearData = function() {
    this.messageCommentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageCommentInfoDlg.set = function(key, val) {
    this.messageCommentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageCommentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MessageCommentInfoDlg.close = function() {
    parent.layer.close(window.parent.MessageComment.layerIndex);
}

/**
 * 收集数据
 */
MessageCommentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('messageId')
    .set('sendOpenId')
    .set('sendAvatar')
    .set('sendNickName')
    .set('receiveOpenId')
    .set('receiveAvatar')
    .set('receiveNickName')
    .set('content')
    .set('time');
}

/**
 * 提交添加
 */
MessageCommentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageComment/add", function(data){
        Feng.success("添加成功!");
        window.parent.MessageComment.table.refresh();
        MessageCommentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageCommentInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MessageCommentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageComment/update", function(data){
        Feng.success("修改成功!");
        window.parent.MessageComment.table.refresh();
        MessageCommentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageCommentInfoData);
    ajax.start();
}

$(function() {

});
