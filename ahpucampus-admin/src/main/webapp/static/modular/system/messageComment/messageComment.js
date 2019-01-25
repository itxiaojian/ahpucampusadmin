/**
 * 帖子评论管理初始化
 */
var MessageComment = {
    id: "MessageCommentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MessageComment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '消息id', field: 'messageId', visible: true, align: 'center', valign: 'middle'},
            {title: '发起评论人openId', field: 'sendOpenId', visible: true, align: 'center', valign: 'middle'},
            {title: '发起评论人头像', field: 'sendAvatar', visible: true, align: 'center', valign: 'middle'},
            {title: '发起消息人昵称', field: 'sendNickName', visible: true, align: 'center', valign: 'middle'},
            {title: '收到评论人openId', field: 'receiveOpenId', visible: true, align: 'center', valign: 'middle'},
            {title: '收到评论人头像', field: 'receiveAvatar', visible: true, align: 'center', valign: 'middle'},
            {title: '收到评论人昵称', field: 'receiveNickName', visible: true, align: 'center', valign: 'middle'},
            {title: '评论内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '发起消息时间，以页面传来为准', field: 'time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MessageComment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MessageComment.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加帖子评论
 */
MessageComment.openAddMessageComment = function () {
    var index = layer.open({
        type: 2,
        title: '添加帖子评论',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/messageComment/messageComment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看帖子评论详情
 */
MessageComment.openMessageCommentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '帖子评论详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/messageComment/messageComment_update/' + MessageComment.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除帖子评论
 */
MessageComment.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/messageComment/delete", function (data) {
            Feng.success("删除成功!");
            MessageComment.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("messageCommentId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询帖子评论列表
 */
MessageComment.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MessageComment.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MessageComment.initColumn();
    var table = new BSTable(MessageComment.id, "/messageComment/list", defaultColunms);
    table.setPaginationType("client");
    MessageComment.table = table.init();
});
