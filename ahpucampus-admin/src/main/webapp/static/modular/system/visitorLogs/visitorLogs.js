/**
 * 浏览记录管理初始化
 */
var VisitorLogs = {
    id: "VisitorLogsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
VisitorLogs.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '浏览记录类型', field: 'logType', visible: true, align: 'center', valign: 'middle'},
            {title: '帖子id', field: 'messageId', visible: true, align: 'center', valign: 'middle'},
            {title: '浏览人openId', field: 'openId', visible: true, align: 'center', valign: 'middle'},
            {title: '浏览时头像链接', field: 'avatar', visible: true, align: 'center', valign: 'middle'},
            {title: '昵称', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间，浏览时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
VisitorLogs.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        VisitorLogs.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加浏览记录
 */
VisitorLogs.openAddVisitorLogs = function () {
    var index = layer.open({
        type: 2,
        title: '添加浏览记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/visitorLogs/visitorLogs_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看浏览记录详情
 */
VisitorLogs.openVisitorLogsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '浏览记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/visitorLogs/visitorLogs_update/' + VisitorLogs.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除浏览记录
 */
VisitorLogs.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/visitorLogs/delete", function (data) {
            Feng.success("删除成功!");
            VisitorLogs.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("visitorLogsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询浏览记录列表
 */
VisitorLogs.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    VisitorLogs.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = VisitorLogs.initColumn();
    var table = new BSTable(VisitorLogs.id, "/visitorLogs/list", defaultColunms);
    table.setPaginationType("client");
    VisitorLogs.table = table.init();
});
