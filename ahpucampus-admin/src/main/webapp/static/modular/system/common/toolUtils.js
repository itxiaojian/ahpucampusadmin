/**
 * 常用下拉框查询
 **/

//获取医院列表
function getHospCityList() {
    /* 获取医院信息 */
    $.post(Feng.ctxPath + "/phdistance/getHospCityList").done(function(data) {
        if(data.success){
            var data=data.data;
            var str = '<option value="" address="" city="">请选择医院</option>';
            if (data.length > 0) {
                for ( var i in data) {
                    str += '<option value="' + data[i].code + '" address="' + data[i].address + '"  city="' + data[i].city + '">' + data[i].name + '</option>'
                }
                $('#hospitalNo').html(str);
            }

            $('#hospitalNo').on('change', function() {
                $("#hospitalName").val($('#hospitalNo').find("option:selected").text());
            });
            $("#hospitalNo").val($("#sethospitalNo").val());
            $("#hospitalNo").chosen();
            $("#hospitalName").val($("#sethospitalName").val());
        }
    });
}

//获取生产中心列表
function getCenterCityList() {
    /* 获取生产中心信息 */
    $.post(Feng.ctxPath + "/phdistance/getCenterCityList").done(
        function(data) {
            if (data.success) {
                var data = data.data
                var str = '<option value="" address=""  city=""></option>';
                if (data.length > 0) {
                    for ( var i in data) {
                        str += '<option value="' + data[i].code
                            + '" city="' + data[i].city + '" address="'
                            + data[i].address + '">' + data[i].name
                            + '</option>'
                    }
                    $('#prodNo').html(str);
                    $("#prodNo").chosen();
                }
            }
        });
}

//获取当前时间的加addNum时间
function getLocalTime(addNum,id) {
    var year,month,day,today,thatDay;
    var today = new Date();
    year = today.getFullYear();
    month = today.getMonth() + 1;
    day = today.getDate();
    if(day <= addNum) {
        if(month > 1) {
            month = month - 1;
        } else {
            year = year - 1;
            month = 12;
        }
    }
    today.setDate(today.getDate() + addNum);
    year = today.getFullYear();
    month = today.getMonth() + 1;
    day = today.getDate();
    thatDay = year + "-" + (month < 10 ? ('0' + month) : month) + "-" + (day < 10 ? ('0' + day) : day);
    $("#"+id+"").val(thatDay);
}

//时间初始化，singleFlag是否单个时间，id：day，否则即是开始结束时间，id：beginTime，endTime
function dataInit(format,singleFlag){
    if(singleFlag){
        $('#day').datetimepicker({
            format : 'yyyy-mm-dd',
            weekStart : 1,
            autoclose : true,
            startView : 2,
            minView : 2,
            forceParse : false,
            language : 'zh'
        });
        getLocalTime(0,'day');
    }else{
        $('#beginTime').datetimepicker({
            format: format,
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            todayBtn : true,
            forceParse: false,
            language: 'zh'
        }).on("click",function(){
            $("#beginTime").datetimepicker("setEndDate",$("#endTime").val())
        });
        $('#endTime').datetimepicker({
            format: format,
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            todayBtn : true,
            forceParse: false,
            language: 'zh'
        }).on("click",function(){
            $("#endTime").datetimepicker("setStartDate",$("#beginTime").val())
        });
    }
}


//获取指定name字典列表
function getDictByNameList(name,key) {
    if(name == '' || typeof name == undefined){
        return;
    }
    /* 获取药品信息信息 */
    $.post(Feng.ctxPath + "/dict/getDictByNameList?name="+name).done(function(data) {
        var str = '<option value="">请选择...</option>';
        var value =  $('#'+key+"Val").val();
        if (data.length > 0) {
            for ( var i in data) {
                str += '<option value="' + data[i].num + '">' + data[i].name + '</option>'
            }
            $('#'+key).html(str);

            // $('#'+key+'Code').on('change', function() {
            //     $("#"+key).val($('#'+key+'Code').find("option:selected").val());
            // });
            // $('#'+key+'Code').val($("#set"+key+"Code").val());
            // // $('#'+key+'Code').chosen();
            $("#"+key).val(value);

        }
    });
};

//html转义
function HTMLEncode(html) {
    var temp = document.createElement("div");
    (temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
    var output = temp.innerHTML;
    temp = null;
    return output;
};

//转义后的string转成html
function HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}

//加法
function accAdd(arg1,arg2){

    var r1,r2,m;

    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}

    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}

    m=Math.pow(10,Math.max(r1,r2));

    return (accMul(arg1,m)+accMul(arg2,m))/m;

}
