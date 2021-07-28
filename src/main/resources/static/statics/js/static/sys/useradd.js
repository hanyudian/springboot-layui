var header = {};
var token = sessionStorage.getItem("gwdtz_token");
var usernamecookie = sessionStorage.getItem("gwdtz_username");
if (typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var userUserid = RequestParameter()["userUserid"];
$(function () {
    roleListInit();
    if (userUserid != -1) {
        Bind(userUserid);
        $("#username").attr("readOnly", true);
    }
    //表单验证及提交
    initForm();

});

// //绑定权限
function roleListInit() {
    $("#roleid").html("");
    $.ajax({
        headers: header,
        dataType: 'json',
        contentType: 'application/json',
        type: 'get',
        url: GWDTZ_BASE_URL + '/user/selectRoleList',
        success: function (data) {
            var list = data.RoleList;
            $("#roleid").append($("<option value=''>请选择</option>"));
            $.each(list, function (i, row) {
                $("#roleid").append($("<option value=\"" + row.id + "\">" + row.name + "</option>"));
            });
        }
    });
}

//如果userUserid不为-1，则为编辑，绑定数据
function Bind(userUserid) {
    ajaxGet("/user/selectUserById", function (ajaxRet) {
        $("input[name='userid']").val(ajaxRet.data.userid);
        $("input[name='username']").val(ajaxRet.data.username);
        $("input[name='deptid']").val(ajaxRet.data.deptid);
        $("input[name='deptname']").val(ajaxRet.data.deptname);
        $("input[name='realname']").val(ajaxRet.data.realname);
        $("input[name='phone']").val(ajaxRet.data.phone);
        $("#roleid option[value='" + ajaxRet.data.roleid + "']").attr("selected", true);
    }, {"userid": userUserid});
}

//表单验证及提交
function initForm() {
    if (userUserid == -1) {
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
                username: {
                    required: true,
                    remote: {
                        headers: header,
                        url: GWDTZ_BASE_URL + '/user/existUsername',
                        type: "get",
                        data: {
                            "username": function () {
                                if (userUserid == -1) {
                                    return $("#username").val();
                                } else {
                                    return false;
                                }

                            }

                        }
                    }
                },
                deptname: {
                    required: true

                }

            },
            messages: {

                username: {
                    required: "请输入用户名",
                    remote: "用户名已经存在"
                },
                deptname: {
                    required: "请选择部门"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {

                $(form).ajaxSubmit({
                    headers: header,
                    url: GWDTZ_BASE_URL + '/user/userAdd',
                    type: "post",
                    data: {
                        usernamecookie: usernamecookie
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.code == '200') {
                            parent.layer.msg(result.msg.toString(), {icon: 1});
                            parent.layer.close(index);
                        } else {
                            parent.layer.msg(result.msg.toString(), {icon: 0});
                        }
                    }
                });
            }
        });
    } else {
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
                username: {
                    required: true,
                    remote: {
                        headers: header,
                        url: GWDTZ_BASE_URL + '/user/existUsername',
                        type: "get",
                        data: {
                            "username": function () {
                                if (userUserid == -1) {
                                    return $("#username").val();
                                } else {
                                    return false;
                                }

                            }

                        }
                    }
                },
                deptname: {
                    required: true

                }

            },
            messages: {

                username: {
                    required: "请输入用户名",
                    remote: "用户名已经存在"
                },
                deptname: {
                    required: "请选择部门"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                console.log(form)
                $(form).ajaxSubmit({
                    headers: header,
                    url: GWDTZ_BASE_URL + '/user/userEdit',
                    type: "put",
                    data: {
                        usernamecookie: usernamecookie
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.code == '200') {
                            parent.layer.msg(result.msg.toString(), {icon: 1});
                            parent.layer.close(index);
                        } else {
                            parent.layer.msg(result.msg.toString(), {icon: 0});
                        }
                    }
                });
            }
        });
    }

}

function openDeptwin() {
    layer.open({
        type: 2,
        title: '选择部门',
        shadeClose: false, //点击遮罩关闭层
        area: ['780px', '500px'],
        fixed: true,
        skin: 'layui-layer-rim',
        content: 'deptOneSel.html',
        success: function (layero, index) {
        },
        end: function () {//弹出层销毁时调用
        }
    });
}