var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var roleId=RequestParameter()["roleId"];
$(function () {
    if(roleId!=-1){
        Bind(roleId);
    }
    //表单验证及提交
    initForm();

});
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//如果roleId不为-1，则为编辑，绑定数据
function Bind(roleId){
    ajaxGet("/role/selectRoleById", function(ajaxRet) {
        $("input[name='id']").val(ajaxRet.data.id);
        $("input[name='name']").val(ajaxRet.data.name);
        $("input[name='description']").val(ajaxRet.data.description);


    }, {"id": roleId});
}

//表单验证及提交
function initForm() {
    if(roleId == -1){
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
                name: {
                    required: true
                },
                description: {
                    required: true
                }
            },
            messages: {

                name: {
                    required: "请输入角色名称"
                },
                description: {
                    required:"请输入描述"
                }

            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {

                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/role/roleAdd',
                    type: "post",
                    dataType: "json",
                    data: {
                        username:username
                    },
                    success: function (result) {
                        if (result.code=='200') {
                            parent.layer.msg(result.msg.toString(), {icon: 1});
                            parent.layer.close(index);
                        } else {
                            parent.layer.msg(result.msg.toString(), {icon: 0});
                        }
                    }
                });
            }
        });
    }else {
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


                name: {
                    required: true
                },
                description: {
                    required: true
                }

            },
            messages: {

                name: {
                    required: "请输入角色名称"
                },
                description: {
                    required:"请输入描述"
                }

            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {

                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/role/roleEdit',
                    type: "put",
                    dataType: "json",
                    data: {
                        username:username
                    },
                    success: function (result) {
                        if (result.code=='200') {
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