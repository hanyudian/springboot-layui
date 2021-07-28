var header = {};
var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var index = parent.layer.getFrameIndex(window.name);
var username = Decrypt(sessionStorage.getItem("gwdtz_username"));

$(function () {
	safetyDay();
});

layui.use(['form', 'layer'], function () {
	var form = layui.form,
		layer = layui.layer;
	form.verify({
		integer: [
			/^\+?[1-9]\d*$/
			,'请输入大于0的正整数'
		]
	})
	form.on('submit(demo1)', function (data) {
		$.ajax({
			headers: header,
			async:false,
			url: GWDTZ_BASE_URL + '/safetyDay/updateEvent',
			type: "put",
			data: {
				aDay: data.field.switchA,
				bDay: data.field.switchB,
				cDay: data.field.switchC,
				dDay: data.field.switchD,
				username: username
			},
			dataType: "json",
			success: function(result) {
				if (result.code=='200') {
					parent.layer.close(index);
					parent.layer.msg(result.msg.toString(), {icon: 1});

				} else {
					parent.layer.close(index);
					parent.layer.msg(result.msg.toString(), {icon: 0});

				}
			},
			error: function(data) {
				parent.layer.msg(data.msg, {
					icon: 5
				});
			}
		});
		return false;
	});
})

function safetyDay() {
	$.ajax({
		headers: header,
		async: false,
		url: GWDTZ_BASE_URL + '/safetyDay/getSafetyToday',
		type: "get",
		data: {
			username: username
		},
		dataType: "json",
		success: function (result) {
			if (result.code == '200') {
				$("input[name='switchA']").val(result.data.aday);
				$("input[name='switchB']").val(result.data.bday);
				$("input[name='switchC']").val(result.data.cday);
				$("input[name='switchD']").val(result.data.dday);
			} else {
				layer.msg(result.msg.toString(), {icon: 0});
			}
		},
		error: function (data) {
			layer.msg(data.msg, {
				icon: 5
			});
		}
	});
}