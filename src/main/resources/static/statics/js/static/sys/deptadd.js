var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var styleId=RequestParameter()["styleId"];
$(function () {
    if(styleId!=-1){
        Bind(styleId);
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


//如果styleId不为-1，则为编辑，绑定数据
function Bind(styleId){
    ajaxGet("/dept/selectDeptById", function(ajaxRet) {
        // alert(ajaxRet.data.deptname);
        $("input[name='deptname']").val(ajaxRet.data.deptname);
    }, {"deptid": styleId});
}

//表单验证及提交
function initForm() {
    if (styleId == -1) {
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
                deptname: {
                    required: true
                }
            },
            messages: {
                deptname: {
                    required:"请输入部门名称"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/dept/DeptAdd',
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
                deptname: {
                    required: true
                }
            },
            messages: {
                deptname: {
                    required:"请输入部门名称"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/dept/DeptEdit',
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
