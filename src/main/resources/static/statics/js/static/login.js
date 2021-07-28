$(function () {
    isIEBrowser();
});
layui.use(['form', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    // 登录过期的时候，跳出ifram框架
    if (top.location != self.location) top.location = self.location;

    $('.bind-password').on('click', function () {
        if ($(this).hasClass('icon-5')) {
            $(this).removeClass('icon-5');
            $("input[name='password']").attr('type', 'password');
        } else {
            $(this).addClass('icon-5');
            $("input[name='password']").attr('type', 'text');
        }
    });

    $('.icon-nocheck').on('click', function () {
        if ($(this).hasClass('icon-check')) {
            $(this).removeClass('icon-check');
        } else {
            $(this).addClass('icon-check');
        }
    });

    // 进行登录操作
    form.on('submit(login)', function (data) {
        data = data.field;
        //console.log(data)
        if (data.username == '') {
            layer.msg('用户名不能为空');
            return false;
        }
        if (data.password == '') {
            layer.msg('密码不能为空');
            return false;
        }
        $.ajax({
            //headers: header,
            //processData: false,
            url: GWDTZ_BASE_URL + '/login/sign',
            type: "POST",
            data: data,
            dataType: "json",
            success: function (data) {
                if (data.msg == "ok") {
                    sessionStorage.setItem("gwdtz_username", Encrypt(data.data.username));
                    sessionStorage.setItem("gwdtz_token", Encrypt(data.data.token));
                    sessionStorage.setItem("gwdtz_flag",Encrypt(data.data.flag));
                    sessionStorage.setItem("gwdtz_role", Encrypt(data.data.role));
                    if (data.data.deptcode.length == 2 || data.data.deptcode.slice(0, 4) == "0001") {
                        location.href = GWDTZ_BASE_URL + "/officeindex.html";
                    } else {
                        location.href = GWDTZ_BASE_URL + "/index.html";
                    }
                } else {
                    layer.msg(data.msg, {
                        icon: 5
                    });

                }
            },
            error: function (data) {
                layer.msg(data.msg, {
                    icon: 5
                });
            }
        });

        return false;
    });
});

function isIEBrowser() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串

    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器

    var isEdge = userAgent.indexOf("Edge") > -1 ; //判断是否IE的Edge浏览器
    var isEdg = userAgent.indexOf("Edg") > -1 &&!isEdge; //判断是否IE的Edge浏览器

    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    var isChrome = userAgent.indexOf("Chrome") > -1 ;
    var isFireFox = userAgent.indexOf("FireFox") > -1 &&!isEdge;
    if(isChrome){
        // console.log(userAgent)
    }else {
        if(isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if(fIEVersion == 7) {
                // console.log("IE7");
                DownloadFireFox("IE7");
            } else if(fIEVersion == 8) {
                // console.log("IE8");
                DownloadFireFox("IE8");
            } else if(fIEVersion == 9) {
                // console.log("IE9");
                DownloadFireFox("IE9");
            } else if(fIEVersion == 10) {
                // console.log("IE10");
                DownloadFireFox("IE10");
            } else if(fIEVersion == 11){
                // console.log("IE6");//IE版本<=7
                DownloadFireFox("IE11");
            }else {
                DownloadFireFox("IE版本太低");
            }
        } else if(isEdge) {
            // console.log("Edge");//edge

            DownloadFireFox("IEEdge");
        } else if(isIE11) {
            // console.log("IE11"); //IE11
            DownloadFireFox("IE11");
        } else if(isFireFox ||isEdg){
            //console.log("FireFox");
            DownloadFireFox("FireFox");
        }
        else{
            // console.log("Other");//不是ie浏览器
            DownloadFireFox("其他");
        }
    }
}

function DownloadFireFox(UserBrowser) {
    //console.log(UserBrowser)
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '[98%,98%]',
        shade: 0.9,
        id: 'lay_indexNew',
        skin: 'layui-layer-rim',
        resize: false,
        moveType: 1,
        content: '<div style="padding: 50px;width: 240px; height: 210px; background-color: #F8F8F8;' +
            ' color: #CC0718;font-family:微软雅黑 ;font-weight: 300; font-size: 13pt;text-align: center; border-radius: 5px;">' +
            '您的浏览器为'+UserBrowser+'版本<br>此系统需使用火狐浏览器<br><br><br><a href="statics/browser/fireFox86.exe" style="cursor: pointer;color:#CC0718;font-family:微软雅黑 ;">win7(32位)及以上点击此处下载</a>' +
            '<br><br><br><a href="statics/browser/fireFox64.exe" style="cursor: pointer;color:#CC0718;font-family:微软雅黑 ;">win7(64位)及以上点击此处下载</a>'+
            '<br><br><br><a href="statics/browser/firefox.52.7.3.exe" style="cursor: pointer;color:#CC0718;font-family:微软雅黑 ;">winXP点击此处下载</a></div>',
    });

}