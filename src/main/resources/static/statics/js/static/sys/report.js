var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var contentid = RequestParameter()["contentid"];
var modulePId = RequestParameter()["moduleId"];
var moduleid = RequestParameter()["contentid"];
var status=RequestParameter()["status"];
if(status==5){
	$("#btn-primary").css("display","none");
	$("#upFileBtn").css("display","none");
	$("#deteleBtn").css("display","none");
	$("#upMutiFileBtn").css("display","none");
}
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");

var callback = "view/sys/redirect.html";
var editor1;
var editor = KindEditor.ready(function(K) {
	editor1 = K.create('textarea[name="contents"]', {
		headers: header,
		uploadJson: GWDTZ_BASE_URL + '/upload/uploadimgFile?callBackPath=' + callback,
		fileManagerJson: '../../statics/js/public/plugins/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager: true,
		width: "85%",
		height: "300px",
		items: ['source', 'image', 'clearhtml', 'quickformat', '|', 'undo', 'redo', '|', 'fontname', 'fontsize', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyfull', 'bold', '|', 'table', '|', 'fullscreen', 'plainpaste'
		],
		afterCreate: function() {
			var self = this;
			K.ctrl(document, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			K.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
		}, //上传文件后执行的回调函数,获取上传图片的路径
		afterUpload: function(url) {
			console.log(url)
		},
		afterBlur: function() {
			this.sync();
		}
	});
	prettyPrint();
});
$(function() {
	if(status==3 || status==4 || status==5){
		Bind(moduleid,username);
	}
	// $("input[name='id']").val(-1);
	$("input[name='createtime']").val((new Date()).format("yyyy-MM-dd hh:mm:ss"));
	$("input[name='title']").val("督办落实情况");
	$("input[name='moduleid']").val(contentid);
	$("input[name='modulepid']").val(modulePId);
	//表单验证及提交
	initForm();

});
//绑定数据
function Bind(moduleid,username) {
	ajaxGet("/content/selectContentByModuleid1", function(ajaxRet) {
		// $("input[name='id']").val(contentId);
		$("input[name='publishuser']").val(ajaxRet.contentDO.publishuser);
		$("input[name='createtime']").val(ajaxRet.contentDO.createtime);
		$("input[name='title']").val(ajaxRet.contentDO.title);
		editor1.html(ajaxRet.contentDO.contents);
		$("input[name='imageurl']").val(ajaxRet.contentDO.imageurl);

		var infoHtml = '';
		var list = ajaxRet.attachlist;
		var strattachlist='';
		$.each(list, function(i, row) {
			var tri="tr_"+i;
			if(i % 2 == 0) infoHtml += '<tr class="odd" id=\"'+tri+'\">';
			else infoHtml += '<tr id=\"'+tri+'\">';
			infoHtml += '<td>' + row.attachrealname + '</td>';
			infoHtml += '<td>' + row.attachtype + '</td>';
			infoHtml += '<td>' + row.attachsize + '</td>';
			var attachabsoluteurl=row.attachabsoluteurl;
			var attachrelativeurl=row.attachrelativeurl;
			infoHtml += '<td><a href=\"' + attachabsoluteurl+ '\" target="_blank" class="btn-attach">附件</a>&emsp;';
			strattachlist+=row.attachrealname+","+row.attachrelativeurl+","+row.attachtype+","+row.attachsize+"|";
			if (status != 5){
			infoHtml += '<a href="javascript:void(0);" class="btn-del1" onclick="delAttach(\''+i+'\',\''+attachrelativeurl+'\',\''+ strattachlist+'\',\''+row.id+'\')">删除</a>';
			}
			infoHtml += '</td></tr>';
		});
		$("#infoList").html(infoHtml);
		$("#jsonArray").val(ajaxRet.strattachlist);
	}, {
		"moduleid": moduleid, "username":username
	});
}
//上传单个图片
function upload() {
	$.ajaxFileUpload({
		type: 'POST',
		url:GWDTZ_BASE_URL + '/upload/uploadLocalPic',
		async: true,
		secureuri: false,
		fileElementId: "addpicurl",
		dataType: 'json',
		data: {username:username},
		success: function(data) {
			if(data.success == 'true') {
				$("#addpicurl").val("");
				$("#imageurl").val(data.relativeurl);
				layer.msg(data.msg, {
					icon: 1
				});
			}
			else {
				layer.msg(data.msg, {
					icon: 0
				});
			}
		},
		error: function(data, status, e) { //服务器响应失败处理函数
			alert(data.msg);
			$("addpicurl").val("");
			layer.msg('返回失败!', {
				icon: 0
			});
		}
	});
}
function cleanup() {
	var url = $("#imageurl").val();
	if (url == "") {
		layer.msg("未上传图片", {
			icon: 1
		});
	} else {
		$.ajax({
			type: 'GET',
			url: GWDTZ_BASE_URL + '/upload/deleteZW',
			data: {
				url: url
			},
			contentType: "application/json",
			success: function () {
				$("#addpicurl").val("");
				$("#imageurl").val("");
				layer.msg("图片删除成功", {
					icon: 1
				});
			},
			error: function (e) {
				layer.msg("请求失败", {
					icon: 1
				});
			}
		});
	}
}
//上传多个文件
function uploadMutiFile() {
	$.ajaxFileUpload({
		type: 'POST',
		url:GWDTZ_BASE_URL + '/upload/uploadMethodMulti',
		async: true,
		secureuri: false,
		fileElementId: "adduploadfiles",
		dataType: 'json',
		data: {username:username},
		success: function(data) {
			if(data.success == 'true') {
				layer.msg(data.msg, {
					icon: 1
				});
				var infoHtml = '';
				var dataArray=new Array();
				var objstr="";
				$.each(data.filelist, function(i, row) {
					$("#adduploadfiles").val("");
					var attachrealname=row.filename;
					var attachrelativeurl=row.relativeurl;
					var attachtype=row.filetype;
					var attachsize=row.filesize;
					objstr+=attachrealname+","+attachrelativeurl+","+attachtype+","+attachsize+"|";
					var obj=attachrealname+","+attachrelativeurl+","+attachtype+","+attachsize+"|";
					var tri="tr_"+i;
					if(i % 2 == 0) infoHtml += '<tr class="odd" id=\"'+tri+'\">';
					else infoHtml += '<tr  id=\"'+tri+'\">';
					infoHtml += '<td>' + row.filename + '</td>';
					infoHtml += '<td>' + row.filetype + '</td>';
					infoHtml += '<td>' + row.filesize + '</td>';

					var url=row.absoluteurl;
					infoHtml += '<td><a href=\"' + url+ '\" target="_blank" class="btn-attach">附件</a>&emsp;';
					infoHtml += '<a href="javascript:void(0);" class="btn-del1" onclick="deltr('+i+',\''+attachrelativeurl+'\',\''+ obj+'\')">删除</a>';
					infoHtml += '</td></tr>';

				});
				$("#infoList").append(infoHtml);
				$("#jsonArray").val($("#jsonArray").val()+objstr);
				// if(contentId==-1)
				// {
				// 	$("#infoList").html(infoHtml);
				// 	$("#jsonArray").val(objstr);
				// }else
				// {
				// 	$("#infoList").append(infoHtml);
				// 	$("#jsonArray").val($("#jsonArray").val()+objstr);
				// }
				//

			}
			else {
				layer.msg(data.msg, {
					icon: 0
				});
			}
		},
		error: function(data, status, e) { //服务器响应失败处理函数
			layer.msg('返回失败!', {
				icon: 0
			});
		}
	});
}
function delAttach(i, url, obj, id) {
	var jsonArray = $("#jsonArray").val();
	jsonArray = jsonArray.replace(obj, "");
	$("#jsonArray").val(jsonArray);
	$("#tr_" + i).hide();
	if (url == "") {
		layer.msg("未上传附件", {
			icon: 0
		});
	} else {
		$.ajax({
			type: 'GET',
			url: GWDTZ_BASE_URL + '/upload/deleteZW',
			data: {
				url: url
			},
			contentType: "application/json",
			success: function () {
				layer.msg("文件删除成功", {
					icon: 1
				});
			},
			error: function (e) {
				layer.msg("请求失败", {
					icon: 0
				});
			}
		});
	}
	//删除数据库中的附件
	//alert("删除数据库中的附件"+id);
	ajaxRequest("delete", "/schedule/deleteByAttachId", function (ajaxRet) {
		layer.msg(ajaxRet.msg, {
			icon: 1
		});
		contentListInit();
	}, {"attachid": id.toString()}, undefined);
}

function deltr(i, url, obj) {
	var jsonArray = $("#jsonArray").val();
	jsonArray = jsonArray.replace(obj, "");
	$("#jsonArray").val(jsonArray);
	$("#tr_" + i).hide();
	if (url == "") {
		layer.msg("未上传附件", {
			icon: 0
		});
	} else {
		$.ajax({
			type: 'GET',
			url: GWDTZ_BASE_URL + '/upload/deleteZW',
			data: {
				url: url
			},
			contentType: "application/json",
			success: function () {
				layer.msg("文件删除成功", {
					icon: 1
				});
			},
			error: function (e) {
				layer.msg("请求失败", {
					icon: 0
				});
			}
		});
	}
}
//表单验证及提交
function initForm() {
		$("#infoForm").validate({
			errorPlacement: function(error, element) {
				if(error != null && error.text() != "") {
					layer.tips(error.text(), "#" + element.attr("id"), {
						tips: [2, '#78BA32'],
						tipsMore: true
					});
				}
			},
			ignore: "",
			rules: {
				contents: {
					required: true
				}
			},
			messages: {
				contents: {
					required: "请输入落实情况"
				}
			},
			onkeyup: false,
			focusCleanup: true,
			success: "valid",
			submitHandler: function(form) {
				$(form).ajaxSubmit({
					headers: header,
					url: GWDTZ_BASE_URL + '/content/contentAdd-report',
					type: "post",
					dataType: "json",
					data:{
						username:username,
						contentid:contentid
					},
					success: function(result) {
						if(result.code == '200') {
							parent.layer.msg(result.msg.toString(), {
								icon: 1
							});
							parent.layer.close(index);
						} else {
							parent.layer.msg(result.msg.toString(), {
								icon: 0
							});
						}
					}
				});
			}
		});
}



