/**
 * 信息附件管理管理初始化
 */
var MessageFile = {
    id: "MessageFileTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MessageFile.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'messageId', visible: true, align: 'center', valign: 'middle'},
            {title: '文件原名', field: 'fileName', visible: true, align: 'center', valign: 'middle'},
            {title: '文件随机名', field: 'uuName', visible: true, align: 'center', valign: 'middle'},
            {title: '文件在服务器的绝对完整路径', field: 'url', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MessageFile.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MessageFile.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加信息附件管理
 */
MessageFile.openAddMessageFile = function () {
    var index = layer.open({
        type: 2,
        title: '添加信息附件管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/messageFile/messageFile_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看信息附件管理详情
 */
MessageFile.openMessageFileDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '信息附件管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/messageFile/messageFile_update/' + MessageFile.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除信息附件管理
 */
MessageFile.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/messageFile/delete", function (data) {
            Feng.success("删除成功!");
            MessageFile.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("messageFileId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询信息附件管理列表
 */
MessageFile.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MessageFile.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MessageFile.initColumn();
    var table = new BSTable(MessageFile.id, "/messageFile/list", defaultColunms);
    table.setPaginationType("client");
    MessageFile.table = table.init();
});
