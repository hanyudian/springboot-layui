layui.use(['table', 'form', 'layer', 'jquery', 'tree'], function () {
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
    var index = parent.layer.getFrameIndex(window.name);
    var
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        tree = layui.tree;
    function getTree() {
        var list;
        $.ajax({
            headers: header,
            type: "get",
            dataType: "json",
            traditional: true,
            data: { username: username },
            url: GWDTZ_BASE_URL + '/dept/deptOneSel',
            async: false, //表示同步执行
            success: function(data) {
                list = data.data;
            }
        });
        // console.log(list)
        return list;
    }
    tree.render({
        elem: '#tree',
        data: getTree(),
        onlyIconControl: true,
        click: function (obj) {
            // console.log(obj.data.title)
            window.parent.$("input[name='deptname']").val(obj.data.title);
            window.parent.$("input[name='deptid']").val(obj.data.id);
            parent.layer.close(index);
        }
    })


})