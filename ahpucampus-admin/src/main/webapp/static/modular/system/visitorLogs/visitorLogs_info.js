/**
 * 初始化浏览记录详情对话框
 */
var VisitorLogsInfoDlg = {
    visitorLogsInfoData : {}
};

/**
 * 清除数据
 */
VisitorLogsInfoDlg.clearData = function() {
    this.visitorLogsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VisitorLogsInfoDlg.set = function(key, val) {
    this.visitorLogsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VisitorLogsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VisitorLogsInfoDlg.close = function() {
    parent.layer.close(window.parent.VisitorLogs.layerIndex);
}

/**
 * 收集数据
 */
VisitorLogsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('logType')
    .set('messageId')
    .set('openId')
    .set('avatar')
    .set('nickName')
    .set('createTime');
}

/**
 * 提交添加
 */
VisitorLogsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/visitorLogs/add", function(data){
        Feng.success("添加成功!");
        window.parent.VisitorLogs.table.refresh();
        VisitorLogsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.visitorLogsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VisitorLogsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/visitorLogs/update", function(data){
        Feng.success("修改成功!");
        window.parent.VisitorLogs.table.refresh();
        VisitorLogsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.visitorLogsInfoData);
    ajax.start();
}

$(function() {

});
