layui.use(['table', 'form', 'layer', 'jquery', 'tree'], function () {
	var header = {};
	var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
	if (typeof token != "undefined" && token != null) {
		header["Authorization"] = token;
	}
	var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
	var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		tree = layui.tree;


	table.render({
		elem: '#user',
		method: 'get',
		headers: header,
		title: '用户表',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'userid', title: 'ID', width: 100, sort: true, fixed: 'left', hide: true},
			{field: 'username', title: '用户名'},
			{field: 'deptname', title: '部门名称'},
			{field: 'phone', title: '电话'},
			{field: 'realname', title: '真实名'},
			{field: 'rolename', title: '角色名'},
			{fixed: 'right', title: '操作', toolbar: '#bodyToolbar'}
		]],
		toolbar: '#headToolbar',
		defaultToolbar: ['filter', 'exports', 'print', {
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		url: GWDTZ_BASE_URL + '/user/getUserList',
		request: {
			pageName: 'pageNum',
			limitName: 'pageSize'
		},
		where: {
			username: username
		},
		limit: 14,
		limits: [1, 3, 5, 10, 20, 25, 30, 50, 100, 200 ,500 ,1000],
		page: true,
		loading: true,
		skin: 'line',
		even: true,
		// done: function (res, curr, count) {
		// 	$("[data-field='userid']").css("display","none");
		// }
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
		console.log(data.field.username)
		//执行搜索重载
		table.reload('user', {
			page: {
				pageNum: 1,
				pageSize: 14,
			}
			, where: {
				username: username,
				name: data.field.userName,
				deptname: data.field.deptName,
				rolename: data.field.roleName,
			}
		}, 'data');

		return false;
	});

	/**
	 * toolbar头部监听事件
	 */
	table.on('toolbar(user)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id),
			data = checkStatus.data;
		switch (obj.event) {
			case 'add':
				addUser();
				break;
			case 'edit':
				if (data.length === 0) {
					layer.msg('请选择一行！');
				} else if (data.length > 1) {
					layer.msg('只能同时编辑一个！');
				} else {
					editUser(obj ,data);
				}
				break;
			case 'delete':
				if (data.length === 0) {
					layer.msg('请选择一行！');
				} else if (data.length > 1) {
					layer.msg('只能同时删除一个！');
				} else {
					deleteUser(data[0].userid);
				}
				break;
		}
	});

	/**
	 * toolbar行监听事件
	 */
	table.on('tool(user)', function (obj) {
		var data = obj.data;
		// console.log(data)
		switch (obj.event) {
			case 'accessSetting':
				layer.open({
					type: 2,
					title: '权限设置',
					shade: 0.7,
					shadeClose: false, //点击遮罩关闭层
					anim: 5,
					area: ['780px', '500px'],
					content: 'userModule.html?userid=' + data.userid,
					success: function (layero, index) {
					},
					end: function () {//弹出层销毁时调用
					}
				});
				break;
			case 'passwordReset':
				layer.confirm('请确定是否重置该用户密码？', {icon: 0, title: '提示'}, function (index) {
					$.ajax({
						headers: header,
						url: GWDTZ_BASE_URL + '/user/passwordReset',
						type: 'post',
						data: {
							userid: data.userid,
							username: username
						},
						dataType: "json",
						success: function (result) {
							if (result.code == 200) {
								layer.close(index);
								layer.msg(result.msg.toString(), {icon: 6});
								table.reload('user')
							} else {
								layer.msg(result.msg.toString(), {icon: 5});
							}
						}
					});
				})
				break;
		}
	});

	function addUser() {
		addIndex = layer.open({
			type: 1,
			anim: 5,
			resize: false,
			title: '用户修改',
			area: ['400px', '500px'],
			content: $("#add_main"),
			success: function (layero, index) {
				$("#resetFormAdd").trigger("click");
			}
		});

		$.ajax({
			headers: header,
			dataType: 'json',
			type: 'get',
			url: GWDTZ_BASE_URL + '/user/selectRoleList',
			success: function (data) {
				var list = data.RoleList;
				$("#iconSelectAdd").empty();
				$("#iconSelectAdd").append($("<option value=''>请选择</option>"));
				$.each(list, function (index, item){
					let option = new Option(item.name, item.id);
					$("#roleIdAdd").append(option);
				});
				form.render();
			}
		});
		form.verify({
			username: function (value) {
				var message = '';
				$.ajax({
					headers: header,
					url: GWDTZ_BASE_URL + '/user/existUsername',
					type: 'get',
					async: false,
					data: {username: value},
					dataType: "json",
					success: function (result) {
						if (result) {

						} else {
							message = "用户名已存在，请重新输入！"
						}
					}
				});
				if(message.length != 0){
					return message;
				}
			}
		})
		form.on("submit(addBtn)", function (data) {
			$.ajax({
				headers: header,
				url: GWDTZ_BASE_URL + '/user/userAdd/' + username,
				type: 'post',
				data: data.field,
				dataType: "json",
				success: function (result) {
					if (result.code == 200) {
						layer.close(addIndex);
						layer.msg(result.msg.toString(), {icon: 6});
						table.reload('user')
					} else {
						layer.msg(result.msg.toString(), {icon: 5});
					}
				}
			});
			return false;
		});
	}

	function editUser(obj, data) {
		editIndex = layer.open({
			type: 1,
			anim: 5,
			resize: false,
			title: '用户修改',
			area: ['400px', '500px'],
			content: $("#edit_main"),
			success: function (index) {
				form.val("userFormEdit", data[0]);
				$.ajax({
					headers: header,
					dataType: 'json',
					type: 'get',
					url: GWDTZ_BASE_URL + '/user/selectRoleList',
					success: function (result) {
						var list = result.RoleList;
						$("#roleIdEdit").empty();
						$("#roleIdEdit").append($("<option value=''>请选择</option>"));
						$.each(list, function (index, item){
							let option = new Option(item.name, item.id);
							$("#roleIdEdit").append(option);
						});
						// console.log(data.roleid)
						$("#roleIdEdit").val(data[0].roleid);
						form.render();
					}
				});
				form.render();
			},
			end: function () {
				table.reload('user')
			}
		})


		$('.customer-tips').on('click', function () {
			var that = this;
			layer.tips('别点啦，用户名不可修改哦！', that, {tips: 3 ,time: 2000} );
		})
		form.on("submit(editBtn)", function (data) {
			$.ajax({
				headers: header,
				url: GWDTZ_BASE_URL + '/user/userEdit/' + username,
				type: 'put',
				data: data.field,
				dataType: "json",
				success: function (result) {
					if (result.code == 200) {
						layer.close(editIndex);
						layer.msg(result.msg.toString(), {icon: 6});
						table.reload('user')
					} else {
						layer.msg(result.msg.toString(), {icon: 5});
					}
				}
			});
			return false;
		});
	}

	function deleteUser(id) {
		layer.confirm('真的删除该行数据吗？', function (index) {
			$.ajax({
				headers: header,
				url: GWDTZ_BASE_URL + '/user/deleteUserById',
				type: 'delete',
				data: {
					userid: id,
					username: username
				},
				dataType: "json",
				success: function (result) {
					if (result.code == 200) {
						layer.close(index);
						layer.msg(result.msg.toString(), {icon: 6});
						table.reload('user')
					} else {
						layer.msg(result.msg.toString(), {icon: 5});
					}
				}
			});
		})

	}

})

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


