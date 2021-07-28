var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
//读取栏目id
var contentid = RequestParameter()["contentid"];
var flagname = RequestParameter()["flagname"];
if(flagname != null) {
	username = flagname;
}
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
				if(i % 2 == 0) infoHtml += '<tr class="odd">';
				else infoHtml += '<tr>';
				infoHtml += '<td>' + row.deptname + '</td>';
				if(row.status == 1||row.status == 2){infoHtml += '<td>' + '<span class="icon1">未完成</span>' + '</td>';}
				else if(row.status == 5){infoHtml += '<td>' + '<span class="icon5">' + row.complete + '</span>' + '</td>';}

				infoHtml += '</tr>';
			});
			$("#infoList").html(infoHtml);
		}
	});
}
