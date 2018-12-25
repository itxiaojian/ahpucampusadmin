/**
 * 信息管理管理初始化
 */
var Message = {
    id: "MessageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Message.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '发帖者openId', field: 'openId', visible: true, align: 'center', valign: 'middle'},
            {title: '消息类型（0失物，1寻物...）', field: 'messageType', visible: true, align: 'center', valign: 'middle'},
            {title: '事件描述', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '逻辑删除标示（0,显示，1删除）', field: 'isShow', visible: true, align: 'center', valign: 'middle'},
            {title: '经度', field: 'longitude', visible: true, align: 'center', valign: 'middle'},
            {title: '纬度', field: 'latitude', visible: true, align: 'center', valign: 'middle'},
            {title: '详细地址', field: 'detailAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '省份', field: 'province', visible: true, align: 'center', valign: 'middle'},
            {title: '市', field: 'city', visible: true, align: 'center', valign: 'middle'},
            {title: '区/县', field: 'district', visible: true, align: 'center', valign: 'middle'},
            {title: '镇', field: 'town', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '真实经度', field: 'realLongitude', visible: true, align: 'center', valign: 'middle'},
            {title: '真实纬度', field: 'realLatitude', visible: true, align: 'center', valign: 'middle'},
            {title: '真实省', field: 'realProvince', visible: true, align: 'center', valign: 'middle'},
            {title: '真实市', field: 'realCity', visible: true, align: 'center', valign: 'middle'},
            {title: '真实区', field: 'realDistrict', visible: true, align: 'center', valign: 'middle'},
            {title: '真实详细地址', field: 'realDetailAddress', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Message.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Message.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加信息管理
 */
Message.openAddMessage = function () {
    var index = layer.open({
        type: 2,
        title: '添加信息管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/message/message_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看信息管理详情
 */
Message.openMessageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '信息管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/message/message_update/' + Message.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除信息管理
 */
Message.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/message/delete", function (data) {
            Feng.success("删除成功!");
            Message.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("messageId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询信息管理列表
 */
Message.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Message.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Message.initColumn();
    var table = new BSTable(Message.id, "/message/list", defaultColunms);
    table.setPaginationType("client");
    Message.table = table.init();
});
