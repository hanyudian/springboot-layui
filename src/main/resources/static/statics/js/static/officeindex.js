var header = {};
var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
if(typeof token != "undefined" && token != null) {
	header["Authorization"] = token;
}
var flag = Decrypt(sessionStorage.getItem("gwdtz_flag"));

var username = Decrypt(sessionStorage.getItem("gwdtz_username"));
document.getElementById("sign_name").innerHTML = username;
$(function(){
	checkLogin();
});
layui.use(['jquery', 'layer', 'miniAdmin', 'form', 'element'], function () {
	var $ = layui.jquery,
		layer = layui.layer,
		miniAdmin = layui.miniAdmin,
		form = layui.form;



	var options = {
		header: header,
		username: username,
		iniUrl: "api/init.json",    // 初始化接口
		clearUrl: "api/clear.json", // 缓存清理接口
		urlHashLocation: true,      // 是否打开hash定位
		bgColorDefault: false,      // 主题默认配置
		multiModule: true,          // 是否开启多模块
		menuChildOpen: false,       // 是否默认展开菜单
		loadingTime: 0,             // 初始化加载时间
		pageAnim: true,             // iframe窗口动画
		maxTabNum: 20              // 最大的tab打开数量
	};
	miniAdmin.render(options);

	// 监听指定开关
	form.on('switch(switchTest)', function (data) {
		layer.msg('开关checked：' + (this.checked ? 'true' : 'false'), {
			offset: '6px'
		});
		layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
	});


	$('.login-out').on("click", function () {
		sessionStorage.removeItem("gwdtz_username");
		sessionStorage.removeItem("gwdtz_token");
		layer.msg('退出登录成功', function () {
			window.location = 'page/login-3.html';
		});
	});
});
