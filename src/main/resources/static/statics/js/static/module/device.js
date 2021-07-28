var header = {};
var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var username = sessionStorage.getItem("username");

layui.use(['layer', 'form', 'table'], function () {
	var $ = layui.jquery,
		layer = layui.layer;

});
