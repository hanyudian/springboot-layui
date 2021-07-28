var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var styleId = RequestParameter()["styleId"];
$(function () {
    // moduleManageurlListInit();
    // $("#modulename").on("change",function () {
    //     moduleManageurlListInit();
    // });
    iconInit();
    $("#iconname").on("change",function () {
        iconInit();
    });
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

// //绑定导航地址字典列表
function iconInit() {

    $("#icon").val( $("#iconname").val());
    $("#iconcss").attr("class",$("#iconname").val() );
    $("#iconname").html("");
    $.ajax({
        headers: header,
        dataType: 'json',
        contentType: 'application/json',
        type: 'get',
        url: GWDTZ_BASE_URL + '/front/selectModuleIconList',
        success: function(data) {
            var list = data.moduleIconList;
            // alert(list);
            $("#iconname").append($("<option value=''>请选择</option>"));
            $.each(list, function(i, row) {
                $("#iconname").append($("<option value=\""+row.icon+"\">"+row.name+"</option>"));
            });
        }

    });
}
// //绑定栏目管理页面
// function moduleManageurlListInit() {
//     $("#href").val( $("#modulename").val());
//     $("#modulename").html("");
//     $.ajax({
//         headers: header,
//         dataType: 'json',
//         contentType: 'application/json',
//         type: 'get',
//         url: GWDTZ_BASE_URL + '/front/selectModuleManageUrlList',
//         success: function(data) {
//             var list = data.moduleManageUrlList;
//             // alert(list);
//             console.log(list)
//             $("#modulename").append($("<option value=''>请选择</option>"));
//             $.each(list, function(i, row) {
//                 $("#modulename").append($("<option value=\""+row.manageurl+"\">"+row.modulename+"</option>"));
//             });
//         }
//     });
// }
//如果styleId不为-1，则为编辑，绑定数据
function Bind(styleId){
    ajaxGet("/module/selectModuleByPrimaryKey", function(ajaxRet) {
        $("input[name='id']").val(ajaxRet.data.id);
        $("input[name='title']").val(ajaxRet.data.title);
        $("input[name='pid']").val(ajaxRet.data.pid);
        $("input[name='href']").val(ajaxRet.data.href);
        // $("#modulename option[value='"+ajaxRet.data.modulename+"']").attr("selected",true);
        $("#iconname option[value='"+ajaxRet.data.icon+"']").attr("selected",true);
        // $("#deptid option[value='"+ajaxRet.data.deptid+"']").attr("selected",true);
        $("input[name='icon']").val(ajaxRet.data.icon);
        $("#iconcss").attr("class",ajaxRet.data.icon );
        $("input[name='deptid']").val(ajaxRet.data.deptid);
        $("input[name='deptname']").val(ajaxRet.data.deptname);
    }, {"id": styleId});
}

//表单验证及提交
function initForm() {
    if(styleId == -1){
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
                title: {
                    required: true
                },
                deptname: {
                    required: true
                },
                icon: {
                    required: true
                }

            },
            messages: {
                title: {
                    required:"请输入栏目名称"
                },
                deptname: {
                    required: "请选择部门"
                },
                icon: {
                    required: "请选择图标"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/module/moduleAdd',
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
                title: {
                    required: true
                },
                deptname: {
                    required: true
                },
                icon: {
                    required: true
                }

            },
            messages: {
                title: {
                    required:"请输入栏目名称"
                },
                deptname: {
                    required: "请选择部门"
                },
                icon: {
                    required: "请选择图标"
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    headers: header,
                    url:GWDTZ_BASE_URL + '/module/moduleEdit',
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


function openDeptwin() {
    // window.open("<c:url value='/dept/selOneDept'/>", "", "width=300,height=400,toolbar=no,menubar=no");
    layer.open({
        type: 2,
        title: '选择部门',
        shadeClose: false, //点击遮罩关闭层
        area: ['780px', '500px'],
        fixed: true,
        skin: 'layui-layer-rim',
        //content: ["<c:url value='/promiseContent/toAddPromiseContent?id="+id+"&year="+year+"&quarter="+quarter+"' />"],
        content:'deptOneSel.html',
        success:function (layero,index) {
            //var body=layer.getChildFrame("body",index);
            // body.contents().find("#deptid").val(styleId);//****给新打开的弹窗中的input赋值
            // body.contents().find("#pid").val(nodeId);//****给新打开的弹窗中的input赋值
        },
        end:function () {//弹出层销毁时调用
            // window.location.reload();
            //styleListInit();
            // loadTree();
        }
    });
}