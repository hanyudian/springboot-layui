$(function() {
	ScheduleDetail();
});
var scheduleid=RequestParameter()["scheduleid"];
// alert(contentId);
var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
function ScheduleDetail() {
		$.ajax({
		headers: header,	
		dataType: 'json',
		processData: true,
		contentType: 'application/json',
		type: 'get',
		url: GWDTZ_BASE_URL + '/schedule/selectScheduleDetailById',
		data:{id:scheduleid},
		success: function(data) {
			console.log(data)
			$("#title").html(data.scheduleDo.title);
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