var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if (typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//读取栏目id
var scheduleid = RequestParameter()["scheduleid"];
$(function () {
    scheduleUnFinishListInit();
});

var pager = new PagerView('pageBody');
pager.size = 6;
pager.index = 1;
pager.onclick = function (index) {
    scheduleUnFinishListInit();
};

function scheduleUnFinishListInit() {
    $("#infoList").html("");
    $.ajax({
        headers: header,
        dataType: 'json',
        contentType: 'application/json',
        type: 'get',
        url: GWDTZ_BASE_URL + '/schedule/getUnFinishScheduleListByScheduleId',
        data: {
            pageNum: pager.index,
            pageSize: pager.size,
            scheduleid: scheduleid
        },
        success: function (data) {
            // console.log(data)
            var infoHtml = '';
            var list = data.pageList;
            $.each(list, function (i, row) {
                if (i % 2 == 0) infoHtml += '<tr class="odd">';
                else infoHtml += '<tr>';
                infoHtml += '<td>' + row.date + '</td>';
                switch (row.flag) {
                    case 0:
                        infoHtml += '<td><input type="checkbox" sid="' + row.id + '" onchange="change(this)" lay-skin="switch" lay-text="ON|OFF"></td>';
                        break;
                    case 1:
                        infoHtml += '<td><input type="checkbox" sid="' + row.id + '" onchange="change(this)" lay-skin="switch" lay-text="ON|OFF" checked></td>';
                        break;
                }
                infoHtml += '</tr>';
            });
            $("#infoList").html(infoHtml);
            pager.itemCount = data.pageCount;
            pager.render();
        }
    });
}

function change(e) {
    var id = e.getAttribute("sid");
    e.setAttribute("readonly","readonly")
    var flag;
    if (e.checked) {
        flag = 1;
    } else {
        flag = 0;
    }
    $.ajax({
        headers: header,
        dataType: 'json',
        // contentType: 'application/json',
        type: 'put',
        url: GWDTZ_BASE_URL + '/schedule/scheduleFinishFlag',
        data: {
            id: id,
            flag: flag
        },
        success: function (result) {
            layer.msg(result.msg.toString(), {
                icon: 1
            });
            scheduleUnFinishListInit();
        }
    });
}