var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//读取栏目id
var modulepid = RequestParameter()["id"];
var mark = RequestParameter()["mark"];
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
		contentType: 'application/json',
		type: 'get',
		url: GWDTZ_BASE_URL + '/content/getContentList5',
		data: {
			pageNum: pager.index,
			pageSize: pager.size,
			username:username,
			modulepid:modulepid,
			strkey:strkey,
			mark:mark
		},
		success: function(data) {
			var infoHtml = '';
			var list = data.pageList;
			$.each(list, function(i, row) {
				var url="ContentDetail.html?contentId="+row.id;
				var currentTime = Date.now();
				var offsetTime = currentTime - (new Date(row.implementtime)).getTime();
				var date = new Date(row.implementtime);
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
				date.setMilliseconds(0);
				var timstamp = date.getTime();
				var offsetTime1 = currentTime - timstamp;
				var offsetDays = Math.floor(offsetTime / (3600*24*1e3))+1;
				//alert(row.id);读取每列的内容
				if(i % 2 == 0) infoHtml += '<tr class="odd">';
				else infoHtml += '<tr>';
				infoHtml += '<td><input id="btSelet" name="btSelet" value=' + row.id + ' type="checkbox"></td>';
				infoHtml += '<td>' + row.title + '</td>';
				infoHtml += '<td><a class="btn-dutyplant"  onclick="toPerform1(\'' + row.id + '\')">责任车间</a></td>';
				infoHtml += '<td>' + row.updatetime + '</td>';
				infoHtml += '<td>' + row.implementtime + '</td>';
				if(row.undo == null){infoHtml += '<td>' + '<span class="icon0">(0)</span>' + '</td>';}
				else if(row.undo == 0){infoHtml += '<td>' + '<span class="icon5">(0)</span>' + '</td>';}
				else if(offsetDays == 0 && offsetTime1 > 0){infoHtml += '<td>' + '<span class="icon11">' + "今日到期" +"("+ row.undo +")"+ '</span>' + '</td>';}
				else if(offsetDays > 0){infoHtml += '<td>' + '<span class="icon2">' + "逾期" + offsetDays + "天" +"("+ row.undo +")"+ '</span>' + '</td>';}
				else{
					infoHtml += '<td>' + '<span class="icon1">' +"("+ row.undo +")"+ '</span>' + '</td>';
				}
				infoHtml += '<td><a class="btn-detail"  href=\"' + url+ '\" target="_blank" rel="opener">详情</a></td>';
				infoHtml += '<td><a class="btn-complete"  onclick="toPerform2(\'' + row.id + '\')">落实情况</a></td>';
				infoHtml += '</tr>';
			});
			$("#infoList").html(infoHtml);
			pager.itemCount = data.pageCount;
			pager.render();
		}
	});
}

function toPerform1(contentid) {
	layer.open({
		type: 2,
		title: '选择车间',
		shadeClose: false, //点击遮罩关闭层
		area: ['820px', '550px'],
		fixed: true,
		skin: 'layui-layer-rim',
		content:'deptchoose.html?contentid='+contentid,
		success:function (layero,index) {
			var body=layer.getChildFrame("body",index);
			body.contents().find("#contentid").val(contentid);
		},
		end:function () {//弹出层销毁时调用
			contentListInit();
		}
	});
}
function toPerform2(contentid) {
	layer.open({
		type: 2,
		title: '落实情况',
		shadeClose: false, //点击遮罩关闭层
		area: ['80%', '95%'],
		fixed: true,
		skin: 'layui-layer-rim',
		content:'report-check.html?contentid='+contentid,
		success:function (layero,index) {
			var body=layer.getChildFrame("body",index);
			body.contents().find("#contentid").val(contentid);
		},
		end:function () {//弹出层销毁时调用
			contentListInit();
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
		title: '发布督办',
		shadeClose: false, //点击遮罩关闭层
		area: ['100%', '100%'],
		fixed: true,
		skin: 'layui-layer-rim',
		//content: ["<c:url value='/promiseContent/toAddPromiseContent?id="+id+"&year="+year+"&quarter="+quarter+"' />"],
		content:'ContentEdit-fbdb.html?contentId='+contentId+'&moduleId='+moduleId,
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
// "编辑"按钮
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
		//alert(styleId);
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