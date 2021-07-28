layui.use(['table', 'form', 'layer', 'jquery', 'upload', 'laydate'], function () {
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
    var
        // $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate,
        table = layui.table;


    table.render({
        elem: '#content',
        method: 'get',
        headers: header,
        title: '角色表',
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 100, sort: true, fixed: 'left'},
            {field: 'title', title: '标题'},
            {field: 'serial', title: '文号'},
            {field: 'createtime', title: '创建时间', sort: true},
            {fixed: 'right', title: '操作', toolbar: '#bodyToolbar'}
        ]],
        toolbar: '#headToolbar',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        url: GWDTZ_BASE_URL + '/content/getContentList',
        request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        },
        where: {
            username: username
        },
        limit: 14,
        limits: [1, 3, 5, 10, 11, 20, 30, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000],
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
        table.reload('content', {
            page: {
                pageNum: 1,
                pageSize: 14
            }
            , where: {
                title: data.field.headline,
                serial: data.field.reference,
                createtime: data.field.time,
                username: username
            }
        }, 'data');

        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(content)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data;
        switch (obj.event) {
            case 'add':
                addContent();
                break;
            case 'edit':
                if (data.length === 0) {
                    layer.msg('请选择一行！');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个！');
                } else {
                    editContent(obj, data);
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

    function addContent() {
        var fileArray = new Array();
        addIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '添加内容',
            area: ['1000px', '800px'],
            content: $("#add_main"),
            success: function (layero, index) {
                $('#resetForm').trigger("click");

                //region 生成富文本编辑狂
                const E = window.wangEditor;
                window.editor = new E('#editor');
                // editor.config.height = 350;
                window.editor.config.height = 250;
                window.editor.config.uploadImgParams = {username: username}
                window.editor.config.uploadImgServer = GWDTZ_BASE_URL + '/upload/uploadEditorImg';
                window.editor.config.uploadFileName = 'file';
                window.editor.config.showLinkImg = false;
                window.editor.config.parseFilterStyle = true;
                window.editor.config.excludeMenus = [
                    'video',
                    'link',
                    'list',
                    'emoticon',
                    'code'
                ];
                window.editor.config.parseIgnoreImg = true;
                window.editor.config.uploadImgHooks = {
                    before: function (xhr) {
                        // console.log(xhr);
                        // return {
                        //     prevent: true,
                        //     msg: "aaaaaaaaaaaaaaaaaa"
                        // }
                    },
                    success: function (xhr) {
                        console.log("success", xhr);
                    },
                    fail: function (xhr) {
                        console.log("fail", xhr);
                    },
                    error: function (xhr) {
                        console.log("error", xhr);
                    },
                    timeout: function (xhr) {
                        console.log("timeout");
                    },
                    customerInsert: function (insertImgFn, result) {
                        console.log(result)
                    }
                }
                window.editor.config.onchange = function (newHtml) {
                    // console.log(newHtml)
                    $("#contentsAdd").val(newHtml)
                };
                window.editor.create();
                //endregion

                //region 附件上传

                $("#zyuploadAdd").zyUpload({
                    width: "840px",                 // 宽度
                    height: "440px",                 // 宽度
                    itemWidth: "140px",                 // 文件项的宽度
                    itemHeight: "115px",                 // 文件项的高度
                    url: GWDTZ_BASE_URL + "/upload/uploadFile?username=" + username,  // 上传文件的路径
                    fileType: ["jpg", "png", "jpeg", "txt", "js", "exe", "doc", "docx", "xls", "xlsx", "pdf", "sql", "ppt", "pptx"],// 上传文件的类型
                    fileSize: 51200000,                // 上传文件的大小
                    multiple: true,                    // 是否可以多个文件上传
                    dragDrop: true,                    // 是否可以拖动上传文件
                    tailor: true,                    // 是否可以裁剪图片
                    del: true,                    // 是否可以删除文件
                    finishDel: false,  				  // 是否在上传文件完成后删除预览
                    /* 外部获得的回调接口 */
                    onSelect: function (selectFiles, allFiles) {    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
                        console.info("当前选择了以下文件：");
                        console.info(selectFiles);
                    },
                    onDelete: function (file, files) {              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
                        console.info("当前删除了此文件：");
                        console.log(file)
                        console.info(file.name);
                        var find = undefined;
                        console.log(fileArray)
                        for (let i = 0; i <= fileArray.length - 1; i++) {
                            if (fileArray[i].index == file.index) {
                                find = fileArray[i];
                                fileArray = fileArray.slice(0, i).concat(fileArray.slice(i + 1, fileArray.length));
                                break;
                            }
                        }
                        console.log(find)
                        console.log(fileArray)
                        if (find != undefined) {
                            $.ajax({
                                type: 'delete',
                                url: GWDTZ_BASE_URL + '/upload/deleteZW',
                                data: {
                                    url: find.url
                                },
                                // contentType: "application/json",
                                success: function () {
                                    layer.msg("图片删除成功", {
                                        icon: 6
                                    });
                                },
                                error: function (e) {
                                    layer.msg("图片删除失败", {
                                        icon: 5
                                    });
                                }
                            });
                        } else {
                            layer.msg("图片清除成功", {
                                icon: 6
                            });
                        }
                    },
                    onSuccess: function (file, response) {          // 文件上传成功的回调方法
                        console.info("此文件上传成功：");
                        console.log(file)
                        fileArray.push(new uploadFile(file.index, file.name, JSON.parse(response).data[0]));
                        // console.info(file.name);
                        // console.info("此文件上传到服务器地址：");
                        console.info(response);
                        // attachments += JSON.parse(response).data + '|';
                        // $("#uploadInf").append("<p>上传成功，文件地址是：" + response + "</p>");
                    },
                    onFailure: function (file, response) {          // 文件上传失败的回调方法
                        console.info("此文件上传失败：");
                        // console.info(file.name);
                    },
                    onComplete: function (response) {           	  // 上传完成的回调方法
                        console.info("文件上传完成");
                        // console.info(response);
                        // $("#jsonArrayAdd").val(attachments);
                        // console.log(attachments)
                        // console.log(document.getElementById("jsonArrayAdd").value)
                        // attachments = "";
                        // $("#jsonArrayAdd").val(attachments);
                    }
                });
                //endregion

                //region 日期初始化
                laydate.render({
                    elem: '#test1',
                    value: new Date(),
                    isInitValue: true,
                    type: 'datetime'
                });
                //endregion

            },
            cancel: function (index, layero) {
                window.editor.destroy();
                window.editor = null;
                $("#zyupload").empty();
                $("#add_main").css("display", "none")
            }
        })
        form.on("submit(addBtn)", function (data) {
            var attachments = "";
            for (j = 0 , len = fileArray.length; j < len; j++){
                attachments += fileArray[j].name + "," + fileArray[j].url + "|";
            }
            data.field.jsonArray = attachments;

            $.ajax({
                headers: header,
                url: GWDTZ_BASE_URL + '/content/contentAdd?username=' + username,
                type: 'post',
                data: data.field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        window.editor.destroy();
                        window.editor = null;
                        $("#zyupload").empty();
                        $("#add_main").css("display", "none")
                        layer.close(addIndex);
                        layer.msg(result.msg.toString(), {icon: 6});
                        table.reload('content');
                    } else {
                        layer.msg(result.msg.toString(), {icon: 5});
                    }
                }
            });
            // return false;
        });
    }

    function editContent(obj, data) {
        editIndex = layer.open({
            type: 1,
            anim: 5,
            resize: false,
            title: '内容修改',
            area: ['1000px', '800px'],
            content: $("#edit_main"),
            success: function (index) {
                form.val("contentFormEdit", data[0]);
                form.render();
            }
        })
        // form.on("submit(editBtn)", function (data) {
        //     $.ajax({
        //         headers: header,
        //         url: GWDTZ_BASE_URL + '/role/roleEdit',
        //         type: 'put',
        //         data: data.field,
        //         dataType: "json",
        //         success: function (result) {
        //             if (result.code == 200) {
        //                 layer.close(editIndex);
        //                 layer.msg(result.msg.toString(), {icon: 6});
        //                 table.reload('role')
        //             } else {
        //                 layer.msg(result.msg.toString(), {icon: 5});
        //             }
        //         }
        //     });
        //     return false;
        // });
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