/**
 * 初始化信息附件管理详情对话框
 */
var MessageFileInfoDlg = {
    messageFileInfoData : {}
};

/**
 * 清除数据
 */
MessageFileInfoDlg.clearData = function() {
    this.messageFileInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageFileInfoDlg.set = function(key, val) {
    this.messageFileInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageFileInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MessageFileInfoDlg.close = function() {
    parent.layer.close(window.parent.MessageFile.layerIndex);
}

/**
 * 收集数据
 */
MessageFileInfoDlg.collectData = function() {
    this
    .set('id')
    .set('messageId')
    .set('fileName')
    .set('uuName')
    .set('url');
}

/**
 * 提交添加
 */
MessageFileInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageFile/add", function(data){
        Feng.success("添加成功!");
        window.parent.MessageFile.table.refresh();
        MessageFileInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageFileInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MessageFileInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageFile/update", function(data){
        Feng.success("修改成功!");
        window.parent.MessageFile.table.refresh();
        MessageFileInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageFileInfoData);
    ajax.start();
}

$(function() {

});
