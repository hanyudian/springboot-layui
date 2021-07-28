layui.use(['table', 'form', 'layer', 'jquery', 'tree'], function () {
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
    var index = parent.layer.getFrameIndex(window.name);
    var $ = layui.jquery,
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
            data: {username: username},
            url: GWDTZ_BASE_URL + '/dept/deptOneSel',
            async: false, //表示同步执行
            success: function (data) {
                list = data.data;
            }
        });
        return list;
    }

    tree.render({
        elem: '#tree',
        id: 'demoId',
        data: getTree(),
        // accordion: true,
        edit: ['add', 'update', 'del'],
        operate: function (obj) {
            var type = obj.type,
                data = obj.data,
                elem = obj.elem,
                id = data.id;
            if (type == 'add') {
                addIndex = layer.open({
                    type: 1,
                    anim: 5,
                    resize: false,
                    title: '新增部门',
                    area: ['400px', '500px'],
                    content: $("#add-node"),
                    success: function (layero, index) {
                        $("#resetFormAdd").trigger("click");
                        $("#pid").val(id);
                    },
                    cancel: function (index, layero) {
                        tree.reload('demoId', {data: getTree()})
                    }
                });
                form.on("submit(add-btn)", function (data) {
                    $.ajax({
                        headers: header,
                        url: GWDTZ_BASE_URL + '/dept/deptAdd/' + username,
                        type: 'post',
                        data: data.field,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.close(addIndex);
                                layer.msg(result.msg.toString(), {icon: 6});
                                tree.reload('demoId', {data: getTree()});
                            } else {
                                layer.msg(result.msg.toString(), {icon: 5});
                            }
                        }
                    });
                });
            } else if (type == 'update') {
                editIndex = layer.open({
                    type: 1,
                    anim: 5,
                    resize: false,
                    title: '修改部门',
                    area: ['400px', '500px'],
                    content: $("#edit-node"),
                    success: function (layero, index) {
                        $("#deptid").val(id);
                        $("#deptname").val(data.title);
                    },
                    cancel: function (index, layero) {
                        tree.reload('demoId', {data: getTree()})
                    }
                });
                form.on("submit(edit-btn)", function (data) {
                    $.ajax({
                        headers: header,
                        url: GWDTZ_BASE_URL + '/dept/deptEdit/' + username,
                        type: 'put',
                        data: data.field,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.close(editIndex);
                                layer.msg(result.msg.toString(), {icon: 6});
                                tree.reload('demoId', {data: getTree()});
                            } else {
                                layer.msg(result.msg.toString(), {icon: 5});
                            }
                        }
                    });
                });
            } else if (type == 'del') {
                if (data.children != null) {
                    layer.confirm('真的删除该父节点及其子节点？', function (index) {
                        $.ajax({
                            headers: header,
                            type: "delete",
                            url: GWDTZ_BASE_URL + '/dept/deleteParentNodes/' + username + '/' + id,
                            dataType: "json",
                            error: function (xhr, msg, e) {
                            },
                            success: function (result) {
                                if (result == 0) {
                                    layer.msg("删除成功！", {icon: 6});
                                    tree.reload('demoId', {data: getTree()});
                                } else {
                                    layer.msg("删除失败！", {icon: 5});
                                }
                            }
                        });
                    });
                } else {
                    layer.confirm('真的删除该节点？', function (index) {
                        $.ajax({
                            headers: header,
                            type: "delete",
                            url: GWDTZ_BASE_URL + '/dept/deleteChildNode/' + username + '/' + id,
                            dataType: "json",
                            contentType: "application/json",
                            error: function (xhr, msg, e) {
                            },
                            success: function (result) {
                                if (result == 0) {
                                    layer.msg("删除成功！", {icon: 6});
                                    tree.reload('demoId', {data: getTree()});
                                } else {
                                    layer.msg("删除失败！", {icon: 5});
                                }
                            }
                        });
                    });
                }
            }
        }
    });


})