/**
 * 初始化通知详情对话框
 */
var NoticeInfoDlg = {
    noticeInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        },
        type: {
            validators: {
                notEmpty: {
                    message: '类型不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
NoticeInfoDlg.clearData = function () {
    this.noticeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoDlg.set = function (key, val) {
    this.noticeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
NoticeInfoDlg.close = function () {
    parent.layer.close(window.parent.Notice.layerIndex);
}

/**
 * 收集数据
 */
NoticeInfoDlg.collectData = function () {
    this.noticeInfoData['content'] = NoticeInfoDlg.editor.txt.html();
    this.set('id').set('title').set('type');
}

/**
 * 验证数据是否为空
 */
NoticeInfoDlg.validate = function () {
    $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    $('#noticeInfoForm').bootstrapValidator('validate');
    return $("#noticeInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 选择几种小程序类型后给与提示
 */
$('#type').on('change', function() {
    var type = $("#type").val();
    var tips = "无具体格式要求";
    if(type == '3'){
         tips = "#data#<Br/>#weibo#@安徽工程大学微校园#node#<Br/>#QQ#2295734808#node#<Br/>#wechat#ahpucampus1#node#<Br/>" +
             "#E-mail#225734808@qq.com#node#<Br/>#content#第一段内容#node#<Br/>#content#第二段内容依次类推#node#<Br/>#data#";
    };
    if(type == '4'){
        tips = "#data#<Br/>#version#v0.0.1公测版#node#<Br/>#time#2019.01.06#node#<Br/>" +
            "#content#更新点1<Br/>#items#<Br/>#item#具体功能点1#sonnode#<Br/>#item#具体功能点2#sonnode#<Br/>#items##node#<Br/>#data#";
    };
    $("#tips").html(tips);
});

/**
 * 提交添加
 */
NoticeInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/notice/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Notice.table.refresh();
        NoticeInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
NoticeInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/notice/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Notice.table.refresh();
        NoticeInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.start();
}

$(function () {
    Feng.initValidator("noticeInfoForm", NoticeInfoDlg.validateFields);

    getDictByNameList('通知类型','type');

    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    NoticeInfoDlg.editor = editor;
});
