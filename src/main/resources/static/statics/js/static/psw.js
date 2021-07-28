$(function(){	
	checkLogin();
});
var header = {};
var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
if(typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
$("#btn_mod").click(function() {
	//var username=$.cookie("username")
	var password = $("#password").val();
	var password1 = $("#password1").val();	
	
	if(null == password || "" == password) {		
		layer.msg('输入新密码！', {
			icon: 5
		});
		return;
	}

	if(null == password1 || "" == password1) {		
		layer.msg('输入确认密码！', {
			icon: 5
		});
		return;
	}
	$.ajax({
		type: "put",
		headers: header,
		url: GWDTZ_BASE_URL + '/login/modpsw',
		data: {
			//username:username,
			password: password,
			password1: password1
		},
		dataType: "json",	
		success: function(data) {
			if(data.msg == "ok") {				
				location.href = "login.html";
			} else {
				layer.msg(data.msg, {
					icon: 5
				});
			}
		},
		error: function(data) {
			layer.msg(data.msg, {
				icon: 5
			});
		}

	});
});