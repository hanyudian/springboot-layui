var header = {};
var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("username");

layui.use(['layer','form', 'table'], function () {
	var $ = layui.jquery,
		layer = layui.layer,
		form = layui.form,
		table = layui.table;


	table.render({
		elem: '#currentTableId',
		even: true,  //单双行
		headers: header,
		method: 'get',
		first: true, //显示首页
		last: true,  //显示尾页
		url: '/gwdtz/getcontentlist',
		request: {  //固定格式
			pageName: 'pageNum',
			limitName: 'pageSize'
		},
		toolbar: '#toolbarDemo',
		defaultToolbar: ['filter', 'exports', 'print', {
			title: '提示',
			layEvent: 'LAYTABLE_TIPS',
			icon: 'layui-icon-tips'
		}],
		cols: [[
			{type: "checkbox", width: 50},
			{field: 'id', width: 80, title: '序号', sort: true, align: "center"},
			{field: 'title', width: 400, title: '资料名称', align: "center"},
			{field: 'updatetime', width: 150, title: '修改时间', sort: true, align: "center"},
			{field: 'publishuser', width: 150, title: '修改人', align: "center"},
			{title: '操作', minWidth: 60, toolbar: '#currentTableBar', align: "center"}
		]],
		limits: [10, 15, 20, 25, 50, 100],
		limit: 15,
		page: true,
		skin: 'line'
	});

	// 监听搜索操作
	form.on('submit(data-search-btn)', function (data) {
		var result = JSON.stringify(data.field);
		layer.alert(result, {
			title: '最终的搜索信息'
		});

		//执行搜索重载
		table.reload('currentTableId', {
			page: {
				curr: 1
			}
			, where: {
				searchParams: result
			}
		}, 'data');

		return false;
	});

	/**
	 * toolbar监听事件
	 */
	table.on('toolbar(currentTableFilter)', function (obj) {
		if (obj.event === 'add') {  // 监听添加操作
			var index = layer.open({
				title: '添加资料',
				type: 2,
				shade: 0.2,
				maxmin:true,
				shadeClose: true,
				area: ['70%', '70%'],
				content: '../../../../view/sys/ContentEdit.html'
			});
			$(window).on("resize", function () {
				layer.full(index);
			});
		} else if (obj.event === 'delete') {  // 监听删除操作
			var checkStatus = table.checkStatus('currentTableId')
				, data = checkStatus.data;
			layer.alert(JSON.stringify(data));
		}
	});

	//监听表格复选框选择
	table.on('checkbox(currentTableFilter)', function (obj) {
		console.log(obj)
	});

	table.on('tool(currentTableFilter)', function (obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			layer.msg("资源丢失了呀",{
				icon: 5
			});
		} else if (obj.event === 'edit') {
			var index = layer.open({
				title: '编辑资料',
				type: 2,
				shade: 0.2,
				maxmin:true,
				shadeClose: true,
				area: ['70%', '70%'],
				content: '../../../../view/sys/ContentEdit.html'
			});
			$(window).on("resize", function () {
				layer.full(index);
			});
			return false;
		} else if (obj.event === 'delete') {
			layer.confirm('真的删除行么', function (index) {
				obj.del();
				layer.close(index);
			});
		} else if (obj.event === 'download') {
			layer.msg("正在下载中。。。",{
				icon: 16,
				time: 2000,
				shade: [0.5, '#000', true]
			});
		}
	});


});
