layui.use(['table', 'form', 'layer', 'jquery'], function () {
	var header = {};
	var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
	if (typeof token != "undefined" && token != null) {
		header["Authorization"] = token;
	}
	var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
	var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		table = layui.table;


	table.render({
		elem: '#log',
		method: 'get',
		headers: header,
		title: '日志表',
		autoSort: false,
		cols: [[
			{field: 'id', title: 'ID', width: 100, sort: true, fixed: 'left', hide: true},
			{field: 'userid', title: '用户ID', width: 100, sort: true, fixed: 'left'},
			{field: 'username', title: '用户名'},
			{field: 'method', title: '接口'},
			{field: 'params', title: '参数'},
			{field: 'ip', title: 'IP'},
			{field: 'operation', title: '操作'},
			{field: 'time', title: '操作时间', sort: true},
		]],
		toolbar: '#headToolbar',
		defaultToolbar: ['filter', 'exports', 'print', {
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		url: GWDTZ_BASE_URL + '/log/getLogList',
		request: {
			pageName: 'pageNum',
			limitName: 'pageSize'
		},
		limit: 14,
		limits: [1, 3, 5, 10, 11, 20,  30, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000],
		page: true,
		loading: true,
		skin: 'row',
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
	});

	table.on('sort(log)', function (obj) {
		//将排好序的Data重载表格
		table.reload('log', {
			initSort: obj,
			where: {
				field: obj.field,
				order: obj.type
			}
		});
	});

	// 监听搜索操作
	form.on('submit(search)', function (data) {
		//执行搜索重载
		table.reload('log', {
			page: {
				pageNum: 1,
				pageSize: 14
			}
			, where: {
				name: data.field.name,
				ip: data.field.ip,
				operation: data.field.operation,
				time: data.field.time,
			}
		}, 'data');
		return false;
	});
})