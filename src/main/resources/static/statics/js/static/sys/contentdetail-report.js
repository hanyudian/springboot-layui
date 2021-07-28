$(function() {
	Contentdetail();
});
var moduleid=RequestParameter()["moduleid"];
var deptid=RequestParameter()["deptid"];
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
function Contentdetail() {
		$.ajax({
		headers: header,	
		dataType: 'json',
		processData: true,
		contentType: 'application/json',
		type: 'get',
		url: GWDTZ_BASE_URL + '/content/selectContentByModuleid',
		data:{
			moduleid:moduleid,
			deptid:deptid
		},
		success: function(data) {
			$("#title").html(data.contentDO.title);
			var subtitle= "发布时间：" + data.contentDO.updatetime + "　　　　　　发布用户：" + data.contentDO.publishuser + "　　　　　　浏览次数：" + data.contentDO.visittimes;
			$("#subtitle").html(subtitle);
			var imgurl=GWDTZ_BASE_URL+data.contentDO.imageurl;
			if(data.contentDO.imageurl!=null){
				$("#img").html('<img src=\''+imgurl+'\'>');
			}
			$("#contents").html(data.contentDO.contents);

			var infoHtml = '';
			var list = data.attachlist;
			if(list.length!=0)
			{
				$.each(list, function(i, row) {
					var tri="tr_"+i;
					if(i % 2 == 0) infoHtml += '<tr class="odd" id=\"'+tri+'\">';
					else infoHtml += '<tr id=\"'+tri+'\">';
					infoHtml += '<td>' + row.attachrealname + '</td>';
					infoHtml += '<td>' + row.attachtype + '</td>';
					infoHtml += '<td>' + row.attachsize + '</td>';
					var attachabsoluteurl=row.attachabsoluteurl;
					infoHtml += '<td><a href=\"' + attachabsoluteurl+ '\" target="_blank" class="btn-attach">附件</a>&emsp;';
					infoHtml += '</td></tr>';
				});
				$("#infoList").html(infoHtml);
			}
			else {
				$("#tb_users").attr("style","display:none");
			}


		}
	});
	
}