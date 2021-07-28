// layui.use(['table', 'form', 'layer', 'jquery', 'tree'], function () {
var header = {},
    token = Decrypt(sessionStorage.getItem("gwdtz_token")),
    usernamecookie = Decrypt(sessionStorage.getItem("gwdtz_username")),
    index = parent.layer.getFrameIndex(window.name), //获取窗口索引
    userid = RequestParameter()["userid"],
    layer = layui.layer,
    form = layui.form,
    table = layui.table,
    tree = layui.tree;
if (typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
$(function () {
    var layer = layui.layer,
        form = layui.form,
        table = layui.table,
        tree = layui.tree;
    tree.render({
        elem: '#tree',
        id: 'demoId',
        data: getTree(),
        showCheckbox: true,
        accordion: true,
    });
    bind(userid);

    form.on("submit(sumbitBtn)", function (data) {
        var checkData = tree.getChecked('demoId')
        var moduleids = getCheckedId(checkData);
        $.ajax({
            headers: header,
            url: GWDTZ_BASE_URL + '/user/userModuleEdit',
            type: 'post',
            data: {
                userid: userid,
                moduleids: moduleids,
                usernamecookie:usernamecookie
            },
            dataType: "json",
            success: function (result) {
                console.log(result)
                if (result.code=='200') {
                    parent.layer.msg(result.msg.toString(), {icon: 6});
                    parent.layer.close(index);
                } else {
                    parent.layer.msg(result.msg.toString(), {icon: 5});
                }
            }
        });

    });

    function bind(userid) {
        $.ajax({
            headers: header,
            type: "get",
            dataType: "json",
            data: {userid: userid},
            url: GWDTZ_BASE_URL + '/user/selectModuleById',
            async: false, //表示同步执行
            success: function (data) {
                tree.setChecked('demoId', data.data)
            }
        });
    }

    function getCheckedId(data) {
        var id = "";
        $.each(data, function (index, item) {
            if (id != "") {
                id = id + "," + item.id;
            }
            else {
                id = item.id;
            }
            //item 没有children属性
            if (item.children != null) {
                var i = getCheckedId(item.children);
                if (i != "") {
                    id = id + "," + i;
                }
            }
        });
        return id;
    }
});

function getTree() {
    var list;
    $.ajax({
        headers: header,
        type: "get",
        dataType: "json",
        traditional: true,
        data: {usernamecookie: usernamecookie},
        url: GWDTZ_BASE_URL + '/module/getUserModuleList',
        async: false, //表示同步执行
        success: function (data) {
            list = data.data;
        }
    });
    return list;
}


