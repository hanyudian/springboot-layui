layui.use([ 'form', 'layer', 'jquery', 'tree', 'treetable', 'iconPickerFa'], function () {
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    var usernamecookie = Decrypt(sessionStorage.getItem("gwdtz_username"));
    var index = parent.layer.getFrameIndex(window.name);
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        iconPickerFa = layui.iconPickerFa,
        treeTable = layui.treetable;
    var insTb = treeTable.render({
        elem: '#menu',
        id: 'menu',
        title: '栏目表',
        method: 'get',
        headers: header,
        tree: {
            iconIndex: 1,
            isPidData: true,
            // idName: 'id',
            // pidName: 'pid',
            // childName: 'child'
        },
        treeDefaultClose: false,
        treeLinkage: false,
        toolbar: '#headToolbar',
        page: false,
        even: true,
        skin: 'row',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        url: GWDTZ_BASE_URL + '/module/moduleAllList1',
        where: {
            usernamecookie: usernamecookie
        },
        cols: [[
            {type: 'numbers'},
            // {type: 'checkbox', title: '选择', fixed: 'left'},
            {field: 'title', title: '模块名'},
            {
                field: 'icon', title: '图标', width: '7%', templet: function (d) {
                    // console.log('<i class="' + d.icon + '"></i>')
                    return '<i class="' + d.icon + '"></i>'
                }
            },
            {field: 'href', title: '路径地址'},
            // {field: 'id', title: 'ID'},
            // {field: 'pid', title: 'PID'},
            {fixed: 'right', title: '操作', toolbar: '#bodyToolbar'}
        ]],
        parseData: function (res) {
            // console.log(res.data)
            return {
                "code": 0,
                "msg": res.msg,
                "data": res.data,
                "count": 0
            }
        }
    });

    iconPickerFa.render({
        // 选择器，推荐使用input
        elem: '#iconPickerAdd',
        // fa 图标接口
        url: GWDTZ_BASE_URL +  "/lib/font-awesome-4.7.0/less/variables.less",
        // 是否开启搜索：true/false，默认true
        search: true,
        // 是否开启分页：true/false，默认true
        page: false,
        // 每页显示数量，默认12
        limit: 786,
        // 点击回调
        click: function (data) {
            return data.icon
        },
        // 渲染成功后的回调
        success: function (d) {
            // console.log(d);
        }
    });

    iconPickerFa.render({
        // 选择器，推荐使用input
        elem: '#iconPickerEdit',
        // fa 图标接口
        url: GWDTZ_BASE_URL +  "/lib/font-awesome-4.7.0/less/variables.less",
        // 是否开启搜索：true/false，默认true
        search: false,
        // 是否开启分页：true/false，默认true
        page: true,
        // 每页显示数量，默认12
        limit: 786,
        // 点击回调
        click: function (data) {
            return data.icon
        },
        // 渲染成功后的回调
        success: function (d) {
            // console.log(d);
        }
    });


    treeTable.on('toolbar(menu)', function (obj) {
        switch (obj.event) {
            case 'expandAll':
                insTb.expandAll();
                break;
            case 'foldAll':
                insTb.foldAll();
                break;
        }
    });

    treeTable.on('tool(menu)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'add':
                addModule(data.id);
                break;
            case 'edit':
                editModule(data);
                break;
            case 'delete':
                deleteModule(data);
                break;
        }
    });

    function addModule(id) {
        addIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '新增模块',
            area: ['400px', '500px'],
            content: $("#add-node"),
            success: function (layero, index) {
                $("#resetFormAdd").trigger("click");
                $("#pid").val(id);
                // iconPickerFa.checkIcon('iconPickerAdd', null)
            }
        });

        form.on("submit(add-btn)", function (data) {
            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/module/moduleAdd/' + usernamecookie,
                type: 'post',
                data: data.field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(addIndex);
                        layer.msg(result.msg.toString(), {icon: 6});
                        treeTable.reload('menu');
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
            return false;
        });
    }

    function editModule(data) {
        console.log(data)
        editIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '修改模块',
            area: ['400px', '500px'],
            content: $("#edit-node"),
            success: function (layero, index) {
                $("#resetFormEdit").trigger("click");
                $.ajax({
                    headers: header,
                    url: GWDTZ_BASE_URL + '/module/selectModuleByPrimaryKey',
                    type: 'get',
                    async: false,
                    data: {id: data.id},
                    dataType: "json",
                    success: function (result) {
                        data.deptname = result.data.deptname;
                    }
                });
                form.val("edit-form", {
                    "id": data.id,
                    "pid": data.pid,
                    "title": data.title,
                    "href": data.href,
                    "target": data.target,
                    "deptname": data.deptname,

                });
                if(data.icon != null){
                    iconPickerFa.checkIcon('iconPickerEdit', data.icon.substring(3,data.icon.length))
                }else {
                    // iconPickerFa.checkIcon('iconPickerEdit', null)
                }
                form.render();
            },

        });

        form.on("submit(edit-btn)", function (data) {
            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/module/moduleEdit/' + usernamecookie,
                type: 'put',
                data: data.field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(editIndex);
                        layer.msg(result.msg.toString(), {icon: 6});
                        treeTable.reload('menu')
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
            return false;
        });
    }

    function deleteModule(data){
        console.log(data)
        if (data.children != null) {
            layer.confirm('真的删除该模块点及其子模块？', function (index) {
                $.ajax({
                    headers: header,
                    type: "delete",
                    url: GWDTZ_BASE_URL + '/module/removeParentNodes/' + usernamecookie + '/' + data.id,
                    dataType: "json",
                    error: function (xhr, msg, e) {
                    },
                    success: function (result) {
                        if (result == 0) {
                            layer.msg("删除成功！", {icon: 6});
                            treeTable.reload('menu');
                        } else {
                            layer.msg("删除失败！", {icon: 5});
                        }
                    }
                });
            });
        } else {
            layer.confirm('真的删除该模块？', function (index) {
                $.ajax({
                    headers: header,
                    type: "delete",
                    url: GWDTZ_BASE_URL + '/module/removeChildNode/' + usernamecookie + '/' + data.id,
                    dataType: "json",
                    contentType: "application/json",
                    error: function (xhr, msg, e) {
                    },
                    success: function (result) {
                        if (result == 0) {
                            layer.msg("删除成功！", {icon: 6});
                            treeTable.reload('menu');
                        } else {
                            layer.msg("删除失败！", {icon: 5});
                        }
                    }
                });
            });
        }
    }


});

function openDeptOneSel() {
    layer.open({
        type: 2,
        title: '选择部门',
        shade: 0.7,
        shadeClose: false, //点击遮罩关闭层
        anim: 5,
        area: ['780px', '500px'],
        // skin: 'layui-layer-rim',
        content: 'deptOneSel.html',
        success: function (layero, index) {
        },
        end: function () {//弹出层销毁时调用
        }
    });
}