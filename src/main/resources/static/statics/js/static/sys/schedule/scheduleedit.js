var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var scheduleid = RequestParameter()["scheduleid"];
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if (typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");

$(function () {
    //表单验证及提交
    initForm();
});


//上传单个图片
function upload() {
    $.ajaxFileUpload({
        type: 'POST',
        url: GWDTZ_BASE_URL + '/upload/uploadLocalPic',
        async: true,
        secureuri: false,
        fileElementId: "addpicurl",
        dataType: 'json',
        data: {username: username},
        success: function (data) {
            if (data.success == 'true') {
                $("#addpicurl").val("");
                $("#imageurl").val(data.relativeurl);
                layer.msg(data.msg, {
                    icon: 1
                });
            } else {
                layer.msg(data.msg, {
                    icon: 0
                });
            }
        },
        error: function (data, status, e) { //服务器响应失败处理函数
            alert(data.msg);
            $("addpicurl").val("");
            layer.msg('返回失败!', {
                icon: 0
            });
        }
    });
}

function cleanup() {
    var url = $("#imageurl").val();
    if (url == "") {
        layer.msg("未上传图片", {
            icon: 1
        });
    } else {
        $.ajax({
            type: 'GET',
            url: GWDTZ_BASE_URL + '/upload/deleteZW',
            data: {
                url: url
            },
            contentType: "application/json",
            success: function () {
                $("#addpicurl").val("");
                $("#imageurl").val("");
                layer.msg("图片删除成功", {
                    icon: 1
                });
            },
            error: function (e) {
                layer.msg("请求失败", {
                    icon: 1
                });
            }
        });
    }
}

//上传多个文件
function uploadMutiFile() {
    $.ajaxFileUpload({
        type: 'POST',
        url: GWDTZ_BASE_URL + '/upload/uploadMethodMulti',
        async: true,
        secureuri: false,
        fileElementId: "adduploadfiles",
        dataType: 'json',
        data: {username: username},
        success: function (data) {
            if (data.success == 'true') {
                layer.msg(data.msg, {
                    icon: 1
                });
                var infoHtml = '';
                var dataArray = new Array();
                var objstr = "";
                $.each(data.filelist, function (i, row) {
                    $("#adduploadfiles").val("");
                    var attachrealname = row.filename;
                    var attachrelativeurl = row.relativeurl;
                    var attachtype = row.filetype;
                    var attachsize = row.filesize;
                    objstr += attachrealname + "," + attachrelativeurl + "," + attachtype + "," + attachsize + "|";
                    var obj = attachrealname + "," + attachrelativeurl + "," + attachtype + "," + attachsize + "|";
                    var tri = "tr_" + i;
                    if (i % 2 == 0) infoHtml += '<tr class="odd" id=\"' + tri + '\">';
                    else infoHtml += '<tr  id=\"' + tri + '\">';
                    infoHtml += '<td>' + row.filename + '</td>';
                    infoHtml += '<td>' + row.filetype + '</td>';
                    infoHtml += '<td>' + row.filesize + '</td>';

                    var url = row.absoluteurl;
                    infoHtml += '<td><a href=\"' + url + '\" target="_blank" class="btn-attach">附件</a>&emsp;';
                    infoHtml += '<a href="javascript:void(0);" class="btn-del1" onclick="deltr(' + i + ',\'' + attachrelativeurl + '\',\'' + obj + '\')">删除</a>';
                    infoHtml += '</td></tr>';

                });
                $("#infoList").append(infoHtml);
                $("#jsonArray").val($("#jsonArray").val() + objstr);
            } else {
                layer.msg(data.msg, {
                    icon: 0
                });
            }
        },
        error: function (data, status, e) { //服务器响应失败处理函数
            layer.msg('返回失败!', {
                icon: 0
            });
        }
    });
}

function delAttach(i, url, obj, id) {
    var jsonArray = $("#jsonArray").val();
    jsonArray = jsonArray.replace(obj, "");
    $("#jsonArray").val(jsonArray);
    $("#tr_" + i).hide();
    if (url == "") {
        layer.msg("未上传附件", {
            icon: 0
        });
    } else {
        $.ajax({
            type: 'GET',
            url: GWDTZ_BASE_URL + '/upload/deleteZW',
            data: {
                url: url
            },
            contentType: "application/json",
            success: function () {
                layer.msg("文件删除成功", {
                    icon: 1
                });
            },
            error: function (e) {
                layer.msg("请求失败", {
                    icon: 0
                });
            }
        });
    }
    //删除数据库中的附件
    //alert("删除数据库中的附件"+id);
    ajaxRequest("delete", "/schedule/deleteByAttachId", function (ajaxRet) {
        layer.msg(ajaxRet.msg, {
            icon: 1
        });
        contentListInit();
    }, {"attachid": id.toString()}, undefined);
}

function deltr(i, url, obj) {
    var jsonArray = $("#jsonArray").val();
    jsonArray = jsonArray.replace(obj, "");
    $("#jsonArray").val(jsonArray);
    $("#tr_" + i).hide();
    if (url == "") {
        layer.msg("未上传附件", {
            icon: 0
        });
    } else {
        $.ajax({
            type: 'GET',
            url: GWDTZ_BASE_URL + '/upload/deleteZW',
            data: {
                url: url
            },
            contentType: "application/json",
            success: function () {
                layer.msg("文件删除成功", {
                    icon: 1
                });
            },
            error: function (e) {
                layer.msg("请求失败", {
                    icon: 0
                });
            }
        });
    }
}

//表单验证及提交
function initForm() {
    $("#infoForm").validate({
        errorPlacement: function (error, element) {
            if (error != null && error.text() != "") {
                layer.tips(error.text(), "#" + element.attr("id"), {
                    tips: [2, '#78BA32'],
                    tipsMore: true
                });
            }
        },
        ignore: "",
        rules: {
            title: {
                required: true
            },
            type: {
                required: true
            },
            open: {
                required: true
            }
        },
        messages: {
            title: {
                required: "请输入标题"
            },
            type: {
                required: "请选择"
            },
            open: {
                required: "请选择"
            }
        },
        onkeyup: false,
        focusCleanup: true,
        success: "valid",
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                headers: header,
                url: GWDTZ_BASE_URL + '/schedule/scheduleAdd',
                type: "post",
                dataType: "json",
                data: {
                    username: username
                },
                success: function (result) {
                    if (result.code == '200') {
                        parent.layer.msg(result.msg.toString(), {
                            icon: 1
                        });
                        parent.layer.close(index);
                    } else {
                        parent.layer.msg(result.msg.toString(), {
                            icon: 0
                        });
                    }
                }
            });
        }
    });
}

function fun() {
    var option = $("#type option:selected").val();
    var infoHtml = '';
    console.log(option)
    switch (option) {
        case "0":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            // var myDate = new Date();
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'datetime',
                    // min: myDate.toLocaleString()
                });
            })
            break;
        case "1":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'time'
                });
            })
            break;
        case "2":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">周期：</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="week" id="week">\n' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="0">星期日</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="1">星期一</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="2">星期二</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="3">星期三</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="4">星期四</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="5">星期五</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="6">星期六</option>\n' +
                '\t\t\t\t\t\t\t\t</select>\n' +
                '\t\t\t\t\t\t\t</div>';
            $("#change3").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'time'
                });
            });
            break;
        case "3":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">第几日：</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="day" id="day">' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n';
            for (var i = 1; i <= 28; i++) {
                infoHtml += '<option value="' + i + '">' + i + '日</option>'
            }
            infoHtml += '\t\t\t\t\t\t\t</select>';
            $("#change3").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'time'
                });
            });
            break;
        case "4":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">每季度:</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="quarter" id="quarter">\n' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="1">每季度第一个月</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="2">每季度第二个月</option>\n' +
                '\t\t\t\t\t\t\t\t\t<option value="3">每季度第三个月</option>\n' +
                '\t\t\t\t\t\t\t\t</select>\n' +
                '\t\t\t\t\t\t\t</div>';
            $("#change2").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">第几日：</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="day" id="day">' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n';
            for (var i = 1; i <= 28; i++) {
                infoHtml += '<option value="' + i + '">' + i + '日</option>'
            }
            infoHtml += '\t\t\t\t\t\t\t</select>';
            $("#change3").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'time'
                });
            });
            break;
        case "5":
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
            infoHtml += '<label class="control-label">每年:</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="year" id="year">\n' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n';
            for (var i = 1; i <= 12; i++) {
                infoHtml += '<option value="' + i + '">' + i + '月</option>';
            }
            infoHtml += '\t\t\t\t\t\t\t\t</select>\n' +
                '\t\t\t\t\t\t\t</div>'
            $("#change1").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">第几日：</label>\n' +
                '\t\t\t\t\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t\t\t\t\t<select class="form-control" name="day" id="day">' +
                '\t\t\t\t\t\t\t\t\t<option value="">请选择</option>\n';
            for (var i = 1; i <= 28; i++) {
                infoHtml += '<option value="' + i + '">' + i + '日</option>'
            }
            infoHtml += '\t\t\t\t\t\t\t</select>';
            $("#change3").html(infoHtml);
            infoHtml = '';
            infoHtml += '<label class="control-label">时间选择:</label>\n' +
                '\t\t\t<div class="col-sm-6">\n' +
                '\t\t\t\t<input type="text" name="calltime" id="calltime" autocomplete="off" class="form-control">\n' +
                '\t\t\t</div>';
            $("#change4").html(infoHtml);
            layui.use(['form', 'layedit', 'laydate'], function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#calltime',
                    type: 'time'
                });
            });
            break;
        default:
            $("#change1").empty();
            $("#change2").empty();
            $("#change3").empty();
            $("#change4").empty();
    }
}









