var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if (typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//读取栏目id
var persontype = RequestParameter()["persontype"];
$(function () {
    scheduleListInit();
});
var flagname = RequestParameter()["flagname"];
if (flagname != null) {
    username = flagname;
    $("#btn-add").css("display", "none");
    $("#btn-upd").css("display", "none");
    $("#btn-del").css("display", "none");
}
var pager = new PagerView('pageBody');
pager.size = 17;
pager.index = 1;
pager.onclick = function (index) {
    scheduleListInit();
};

function scheduleListInit() {
    var strkey = $("#strkey").val();
    $("#infoList").html("");
    $.ajax({
        headers: header,
        dataType: 'json',
        contentType: 'application/json',
        type: 'get',
        url: GWDTZ_BASE_URL + '/schedule/getScheduleList',
        data: {
            pageNum: pager.index,
            pageSize: pager.size,
            username: username,
            persontype: persontype,
            strkey: strkey
        },
        success: function (data) {
            var infoHtml = '';
            var list = data.pageList;
            var finishList = data.finishList;
            $.each(list, function (i, row) {
                var url = "ScheduleDetail.html?scheduleid=" + row.id;
                if (i % 2 == 0) infoHtml += '<tr class="odd">';
                else infoHtml += '<tr>';
                infoHtml += '<td><input id="btSelet" name="btSelet" value=' + row.id + ' type="checkbox"></td>';
                infoHtml += '<td>' + row.title + '</td>';
                switch (row.type) {
                    case 0:
                        infoHtml += '<td>指定日期</td>';
                        infoHtml += '<td>' + row.date + ' ' + row.time + '</td>';
                        break;
                    case 1:
                        infoHtml += '<td>日</td>';
                        infoHtml += '<td>' + '每日 ' + row.time + '</td>';
                        break;
                    case 2:
                        infoHtml += '<td>周</td>';
                        var week = null;
                        switch (row.week) {
                            case 0:
                                week = '星期日'
                            case 0:
                                week = '星期一'
                            case 0:
                                week = '星期二'
                            case 0:
                                week = '星期三'
                            case 0:
                                week = '星期四'
                            case 0:
                                week = '星期无'
                            default:
                                week = '星期六'
                        }
                        infoHtml += '<td>' + '每周' + week + ' ' + row.time + '</td>';
                        break;
                    case 3:
                        infoHtml += '<td>月</td>';
                        infoHtml += '<td>' + '每月' + row.day + '号 ' + row.time + '</td>';
                        break;
                    case 4:
                        infoHtml += '<td>季度</td>';
                        infoHtml += '<td>' + '每季度第' + row.quarter + '个月' + row.day + '号 ' + row.time + '</td>';
                        break;
                    case 5:
                        infoHtml += '<td>年</td>';
                        infoHtml += '<td>' + '每年' + row.year + '月' + row.day + '号 ' + row.time + '</td>';
                        break;
                };
                switch (row.open) {
                    case 0:
                        infoHtml += '<td><input type="checkbox" sid="' + row.id + '" onchange="change(this)" lay-skin="switch" lay-text="ON|OFF"></td>';
                        break;
                    case 1:
                        infoHtml += '<td><input type="checkbox" sid="' + row.id + '" onchange="change(this)" lay-skin="switch" lay-text="ON|OFF" checked></td>';
                        break;
                }
                infoHtml += '<td><a class="btn-detail"  href=\"' + url + '\" target="_blank" rel="opener">详情</a></td>';
                if (finishList[i] == 0) {
                    infoHtml += '<td><a class="btn-finished"  href="#" disabled="true" rel="opener">已完成</a></td>';
                } else {
                    infoHtml += '<td><a class="btn-unfinished"  href="#" disabled="true" onclick="openScheduleFinish(' + row.id + ')" rel="opener">未完成(' + finishList[i] + ')</a></td>';
                }

                infoHtml += '</tr>';
            });
            $("#infoList").html(infoHtml);
            pager.itemCount = data.pageCount;
            pager.render();
        }
    });
}

function getIdSelections() {
    var chk_value = [];
    $('input[name="btSelet"]:checked').each(function () {
        chk_value.push($(this).val());
    });
    return chk_value;
}

function toPerform(scheduleid) {
    //var year = $("#partyYear").val();
    //var quarter=$("#quarter").val();
    layer.open({
        type: 2,
        title: '添加资料',
        shadeClose: false, //点击遮罩关闭层
        area: ['100%', '100%'],
        fixed: true,
        skin: 'layui-layer-rim',
        //content: ["<c:url value='/promiseContent/toAddPromiseContent?id="+id+"&year="+year+"&quarter="+quarter+"' />"],
        content: 'ScheduleEdit.html?scheduleid=' + scheduleid,
        success: function (layero, index) {
            var body = layer.getChildFrame("body", index);
            body.contents().find("#id").val(scheduleid);
            body.contents().find("#persontype").val(persontype);
        },
        end: function () {//弹出层销毁时调用
            // window.location.reload();
            scheduleListInit();
        }
    });
}

//"新增"按钮
$("#btn-add").click(function () {
    // $("#modalAddorEditTitle").html("新增样式");
    // $("#modal").modal("show");
    var scheduleid = -1;
    toPerform(scheduleid);
});
//"编辑"按钮
// $("#btn-upd").click(function () {
//     var idSelections = getIdSelections();
//     if (idSelections == "") {
//         layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
//             icon: 2
//         });
//     } else if (idSelections.length > 1) {
//         layer.msg('您勾选的数据超过一条，请只选择需要操作的一条数据', {
//             icon: 2
//         });
//     } else {
//         var scheduleid = idSelections;
//         //alert(styleId);
//         toPerform(scheduleid);
//     }
// });
//"删除"按钮
$(".btn-del").click(function () {
    var idSelections = getIdSelections();
    if (idSelections == "") {
        layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
            icon: 2
        });
        return;
    }
    layer.confirm('请是否确定删除所勾选的数据？', {
        icon: 7,
        title: '提示'
    }, function () {
        layer.closeAll('dialog');
        ajaxRequest("delete", "/schedule/deleteScheduleById", function (ajaxRet) {
            layer.msg(ajaxRet.msg, {
                icon: 1
            });
            scheduleListInit();
        }, {"id": idSelections.toString()}, undefined);
    });
});

function openScheduleFinish(id) {
    layer.open({
        type: 2,
        title: "日程完成情况",
        shade: 0.8,
        anim: 5,
        resize: false,
        shadeClose: false, //点击遮罩关闭层
        area: ['300px', '400px'],
        fixed: true,
        content: 'ScheduleFinish.html?scheduleid=' + id,
        success: function (layero, index) {
            var body = layer.getChildFrame("body", index);
        },
        end: function () {//弹出层销毁时调用
            scheduleListInit();
        }
    });
}


function change(e) {
    var id = e.getAttribute("sid");
    var open;
    if (e.checked) {
        open = 1;
    } else {
        open = 0;
    }
    $.ajax({
        headers: header,
        dataType: 'json',
        // contentType: 'application/json',
        type: 'put',
        url: GWDTZ_BASE_URL + '/schedule/scheduleEdit',
        data: {
            id: id,
            open: open
        },
        success: function (result) {
            scheduleListInit();
            layer.msg(result.msg.toString(), {
                icon: 1
            });

        }
    });
}