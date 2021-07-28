var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
var flagname = RequestParameter()["flagname"];
if(flagname != null) {
	username = flagname;
}
//读取栏目id
var moduleId = RequestParameter()["id"];
$(function() {
	contentListInit();
});
var pager = new PagerView('pageBody');
pager.size = 17;
pager.index = 1;
pager.onclick = function(index) {
	contentListInit();
};
function contentListInit() {
	var strkey=$("#strkey").val();
	$("#infoList").html("");
	$.ajax({
		headers: header,
		dataType: 'json',
		//processData: true,
		contentType: 'application/json',
		type: 'get',
		url: GWDTZ_BASE_URL + '/content/getContentList',
		data: {
			pageNum: pager.index,
			pageSize: pager.size,
			username:username,
			modulepid:moduleId,
			strkey:strkey
		},
		success: function(data) {
			var infoHtml = '';
			var list = data.pageList;
			$.each(list, function(i, row) {
				var url="ContentDetail.html?contentId="+row.id;
				//alert(row.id);读取每列的内容
				if(i % 2 == 0) infoHtml += '<tr class="odd">';
				else infoHtml += '<tr>';
				infoHtml += '<td><input id="btSelet" name="btSelet" value=' + row.id + ' type="checkbox"></td>';
				infoHtml += '<td>' + row.title + '</td>';
				infoHtml += '<td>' + row.serial + '</td>';
				infoHtml += '<td>' + row.createtime + '</td>';
				infoHtml += '<td>' + row.implementtime + '</td>';
				infoHtml += '<td>' + row.deptname + '</td>';
				infoHtml += '<td>' + row.contents + '</td>';
				infoHtml += '<td><a class="btn-detail"  href=\"' + url+ '\" target="_blank" rel="opener">详情</a></td>';
				infoHtml += '</tr>';
			});
			$("#infoList").html(infoHtml);
			pager.itemCount = data.pageCount;
			pager.render();
		}
	});
}

function getIdSelections() {
	var chk_value = [];
	$('input[name="btSelet"]:checked').each(function() {
		chk_value.push($(this).val());
	});
	return chk_value;
}

function toPerform(contentId) {
	layer.open({
		type: 2,
		title: '添加规章',
		shadeClose: false, //点击遮罩关闭层
		area: ['100%', '100%'],
		fixed: true,
		skin: 'layui-layer-rim',
		//content: ["<c:url value='/promiseContent/toAddPromiseContent?id="+id+"&year="+year+"&quarter="+quarter+"' />"],
		content:'ContentEdit-gz.html?contentId='+contentId+'&moduleId='+moduleId,
		success:function (layero,index) {
			var body=layer.getChildFrame("body",index);
			body.contents().find("#id").val(contentId);
		},
		end:function () {//弹出层销毁时调用
			contentListInit();
		}
	});
}

//"新增"按钮
$("#btn-add").click(function() {
	var contentId=-1;
	toPerform(contentId);
});
//"编辑"按钮
$("#btn-upd").click(function() {
	var idSelections = getIdSelections();
	if(idSelections == ""){
		layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
			icon : 2
		});
	}else if(idSelections.length > 1){
		layer.msg('您勾选的数据超过一条，请只选择需要操作的一条数据', {
			icon : 2
		});
	}else{
		var contentId=idSelections;
		toPerform(contentId);
	}
});
//"删除"按钮
$(".btn-del").click(function() {
	var idSelections = getIdSelections();
	if(idSelections == ""){
		layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
			icon : 2
		});
		return ;
	}
	layer.confirm('请是否确定删除所勾选的数据？', {
		icon: 7,
		title: '提示'
	}, function() {
		layer.closeAll('dialog');
		ajaxRequest("delete", "/content/deleteByContentId", function(ajaxRet) {
			layer.msg(ajaxRet.msg, {
				icon : 1
			});
			contentListInit();
		}, {"id": idSelections.toString(),"username":username},undefined);
	});
});