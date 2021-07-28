var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var contentid=RequestParameter()["contentid"];
var deptid=RequestParameter()["deptid"];
$(function () {
	Bind(contentid,deptid);
	//表单验证及提交
	initForm();

});
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//绑定数据
function Bind(contentid,deptid){
	ajaxGet("/content/selectRollbackByContentid", function(ajaxRet) {
		$("input[name='contentid']").val(contentid);
		$("input[name='deptid']").val(ajaxRet.contentDeptDO.deptid);
		$("textarea[name='complete']").val(ajaxRet.contentDeptDO.complete);
	}, {"contentid": contentid,"deptid": deptid});
}

//表单验证及提交
function initForm() {
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
				complete: {
					required: true
				}

			},
			messages: {

				complete: {
					required: "必填"
				}
			},
			onkeyup: false,
			focusCleanup: true,
			success: "valid",
			submitHandler: function (form) {

				$(form).ajaxSubmit({
					headers: header,
					url:GWDTZ_BASE_URL + '/content/rollbackEdit',
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