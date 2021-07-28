layui.use(['table', 'form', 'layer', 'jquery'], function () {
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;


    table.render({
        elem: '#role',
        method: 'get',
        headers: header,
        title: '角色表',
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 100, sort: true, fixed: 'left'},
            {field: 'name', title: '角色'},
            {field: 'description', title: '描述'}
        ]],
        toolbar: '#headToolbar',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        url: GWDTZ_BASE_URL + '/role/getRoleList',
        request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        },
        limit: 14,
        limits: [1, 3, 5, 10, 20, 30, 50],
        page: true,
        loading: true,
        skin: 'line',
        even: true,
        // parseData: function (res) {
        //     console.log(res)
        //     return {
        //         "code": res.code,
        //         "msg": res.msg,
        //         "count": res.count,
        //         "data": res.data
        //     }
        // },
        // response: {
        //     statusName: 'code',
        //     statusCode: '0',
        //     msgName: 'msg',
        //     countName: 'count',
        //     dataName: 'data'
        // }
    })

    // 监听搜索操作
    form.on('submit(search)', function (data) {
        //执行搜索重载
        table.reload('role', {
            page: {
                pageNum: 1,
                pageSize: 14
            }
            , where: {
                name: data.field.name,
                description: data.field.description
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(role)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data;
        switch (obj.event) {
            case 'add':
                addRole();
                break;
            case 'edit':
                if (data.length === 0) {
                    layer.msg('请选择一行！');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个！');
                } else {
                    editRole(obj ,data);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行！');
                } else if (data.length > 1) {
                    layer.msg('只能同时删除一个！');
                } else {
                    deleteRole(data[0].id);
                }
                break;
        }
    });

    function addRole() {
        addIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '角色修改',
            area: ['500px', '400px'],
            content: $("#add_main"),
            success: function (layero, index) {
                $('#resetForm').trigger("click");
                // layui.form.render();
            }
        })
        form.on("submit(addBtn)", function (data) {
            // $('roleFormAdd')[0].reset();
            // layui.form.render();
            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/role/roleAdd',
                type: 'post',
                data: data.field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(addIndex);
                        layer.msg(result.msg.toString(), {icon: 6});
                        table.reload('role')
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
            return false;
        });
    }
    function editRole(obj, data) {
        editIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '角色修改',
            area: ['500px', '400px'],
            content: $("#edit_main"),
            success: function (index) {
                form.val("roleFormEdit", data[0]);
                form.render();
            }
        })
        form.on("submit(editBtn)", function (data) {
            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/role/roleEdit',
                type: 'put',
                data: data.field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(editIndex);
                        layer.msg(result.msg.toString(), {icon: 6});
                        table.reload('role')
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
            return false;
        });
    }
    function deleteRole(id) {
        layer.confirm('真的删除该行数据吗？', function (index) {
            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/role/deleteRoleById',
                type: 'delete',
                data: {
                    id: id,
                    username: username
                },
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(index);
                        layer.msg(result.msg.toString(), {icon: 6});
                        table.reload('role')
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
        })

    }
    //监听表格复选框选择
    table.on('checkbox(currentTableFilter)', function (obj) {
        console.log(obj)
    });

})