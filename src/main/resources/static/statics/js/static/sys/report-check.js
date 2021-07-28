var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//读取栏目id
var contentid = RequestParameter()["contentid"];
$(function() {
	contentListInit();
});
function contentListInit() {
	$("#infoList").html("");
	$.ajax({
		headers: header,
		dataType: 'json',
		contentType: 'application/json',
		type: 'get',
		url: GWDTZ_BASE_URL + '/content/getCompleteList',
		data: {
			contentid:contentid
		},
		success: function(data) {
			var infoHtml = '';
			var list = data.list;
			$.each(list, function(i, row) {
				var url="ContentDetail-report.html?moduleid="+row.contentid+"&deptid="+row.deptid;
				if(i % 2 == 0) infoHtml += '<tr class="odd">';
				else infoHtml += '<tr>';
				infoHtml += '<td>' + row.deptname + '</td>';
				if(row.status == 1||row.status == 2){infoHtml += '<td>' + '<span class="icon1">未上报</span>' + '</td>';}
				else if(row.status == 3){infoHtml += '<td>' + '<span class="icon3">已上报</span>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-check"  href=\"' + url+ '\" target="_blank" rel="opener">查看</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-rollback"  onclick="toPerform1('+ row.contentid +',\''+ row.deptid +'\')">回退</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-done"  onclick="toPerform2('+ row.id +')">完成</a></td>';}
				else if(row.status == 4){infoHtml += '<td>' + '<span class="icon4">被退回</span>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-check"  href=\"' + url+ '\" target="_blank" rel="opener">查看</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-rollback"  onclick="toPerform1('+ row.contentid +',\''+ row.deptid +'\')">回退</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-done"  onclick="toPerform2('+ row.id +')">完成</a></td>';}
				else if(row.status == 5){infoHtml += '<td>' + '<span class="icon5">已完成</span>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					'<a class="btn-check"  href=\"' + url+ '\" target="_blank" rel="opener">查看</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					// '<a class="btn-qx"  onclick="toPerform1('+ row.contentid +',\''+ row.deptid +'\')">回退</a>' +
					'&emsp;&emsp;&emsp;&emsp;' +
					// '<a class="btn-qx"  onclick="toPerform2('+ row.id +')">完成</a>' +
					'</td>';}
				infoHtml += '</tr>';
			});
			$("#infoList").html(infoHtml);
		}
	});
}
function toPerform1(contentid,deptid) {
	layer.open({
		type: 2,
		title: '修改意见',
		shadeClose: false, //点击遮罩关闭层
		area: ['580px', '410px'],
		fixed: true,
		skin: 'layui-layer-rim',
		content:'rollback.html?contentid='+contentid+'&deptid='+deptid,
		success:function (layero,index) {
			var body=layer.getChildFrame("body",index);
			body.contents().find("#id").val(contentid);
		},
		end:function () {//弹出层销毁时调用
			contentListInit();
		}
	});
}
function toPerform2(id) {
	// alert(id);
	layer.confirm('请确定此督办事项是否已完成？', {
		icon: 7,
		title: '提示'
	}, function() {
		layer.closeAll('dialog');
		ajaxRequest("delete", "/content/updatestatus5", function(ajaxRet) {
			layer.msg(ajaxRet.msg, {
				icon : 1
			});
			contentListInit();
		}, {"id": id,"username":username},undefined);
	});
}


