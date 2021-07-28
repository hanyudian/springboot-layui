GWDTZ_BASE_URL="http://localhost:7700/gwdtz";
//GWDTZ_BASE_URL="http://10.94.18.90:7600/gwdtz";
function ajaxRequest(method, url, callback, urlParams, bodyParams) {
    var urlSuffix = "";
    if (typeof urlParams != 'undefined') {
        for (var k in urlParams) {
            if (urlSuffix.length == 0) {
                urlSuffix = "?";
            } else {
                urlSuffix += "&";
            }
            urlSuffix += k + "=" + encodeURIComponent(urlParams[k]);
        }
    }

    var data = "";
    if (typeof bodyParams != 'undefined') {
        data = bodyParams;
    }
    var header = {};
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if(typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    $.ajax({
        headers: header,
        dataType: 'json',
        processData: false,
        contentType: 'application/json',
        type: method,
        url: GWDTZ_BASE_URL + url + urlSuffix,
        data: data,
        success: function (data) {
            callback(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var msg = JSON.stringify(jqXHR);
            //console.error("连接服务器错误: " + msg +". 请联系管理员后再做尝试" );
        }
    });
}
function ajaxGet(url, callback, urlParams) {
    ajaxRequest("GET", url, callback, urlParams);
}

//html页面接收？后面的参数
function RequestParameter() {
    var url=window.location.search;
    var theRequest=new Object();
    if(url.indexOf("?")!=-1){
        var str=url.substr(1);
        var strs=str.split("&");
        for(var i=0;i<strs.length;i++){
            theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
function checkLogin() {
    var token = Decrypt(sessionStorage.getItem("gwdtz_token"));
    if(typeof token == "undefined" || token == null) {
        location.href= GWDTZ_BASE_URL +  "/login.html";
    }
}

function logout() {
    var header = {};
    var token = $.cookie("zeus_token");
    if (typeof token != "undefined" && token != null) {
        header["Authorization"] = token;
    }
    $.ajax({
        headers: header,
        async:false,
        url: GWDTZ_BASE_URL + '/home/delredis',
        type: "post",
        dataType: "json",
        success: function(data) {
            $.removeCookie("zeus_token");
            location.href="login.html";
        }
    });
}
Date.prototype.format=function (fmt) {
    var o={
        "M+":this.getMonth()+1,
        "d+":this.getDate(),
        "h+":this.getHours(),
        "m+":this.getMinutes(),
        "s+":this.getSeconds(),
        "q+":Math.floor((this.getMonth()+3)/3),
        "S":this.getMilliseconds()
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+k+")").test(fmt)){
            fmt=fmt.replace(RegExp.$1,(RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length)));
        }
    }
    return fmt;
}
$(function(){
    document.onkeydown = function() {
        var e = window.event || arguments[0];
        if(e.keyCode == 123) {
            //alert("源码不让看哟1！");
            return false;
        } else if((e.ctrlKey) && (e.shiftKey) && (e.keyCode == 73)) {
            //alert("源码不让看哟2！");
            return false;
        } else if((e.ctrlKey) && (e.keyCode == 85)) {
            return false;
        };

    }
    document.oncontextmenu = function() {
        //alert("源码不让看哟3！");
        return false;
    };
});
//html页面url参数加密
function Encrypt(str, pwd) {
    if(str == "") return "";
    str = escape(str);
    if(!pwd || pwd == "") {
        var pwd = "1234";
    }
    pwd = escape(pwd);
    if(pwd == null || pwd.length <= 0) {
        //alert("Please enter a password with which to encrypt the message.");
        return null;
    }
    var prand = "";
    for(var I = 0; I < pwd.length; I++) {
        prand += pwd.charCodeAt(I).toString();
    }
    var sPos = Math.floor(prand.length / 5);
    var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));
    var incr = Math.ceil(pwd.length / 2);
    var modu = Math.pow(2, 31) - 1;
    if(mult < 2) {
        //alert("Algorithm cannot find a suitable hash. Please choose a different password. /nPossible considerations are to choose a more complex or longer password.");
        return null;
    }
    var salt = Math.round(Math.random() * 1000000000) % 100000000;
    prand += salt;
    while(prand.length > 10) {
        prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
    }
    prand = (mult * prand + incr) % modu;
    var enc_chr = "";
    var enc_str = "";
    for(var I = 0; I < str.length; I++) {
        enc_chr = parseInt(str.charCodeAt(I) ^ Math.floor((prand / modu) * 255));
        if(enc_chr < 16) {
            enc_str += "0" + enc_chr.toString(16);
        } else
            enc_str += enc_chr.toString(16);
        prand = (mult * prand + incr) % modu;
    }
    salt = salt.toString(16);
    while(salt.length < 8) salt = "0" + salt;
    enc_str += salt;
    return enc_str;
}
//html页面url参数解密
function Decrypt(str, pwd) {
    if(str == "") return "";
    if(!pwd || pwd == "") {
        var pwd = "1234";
    }
    pwd = escape(pwd);
    if(str == null || str.length < 8) {
        //alert("A salt value could not be extracted from the encrypted message because it's length is too short. The message cannot be decrypted.");
        return;
    }
    if(pwd == null || pwd.length <= 0) {
        //alert("Please enter a password with which to decrypt the message.");
        return;
    }
    var prand = "";
    for(var I = 0; I < pwd.length; I++) {
        prand += pwd.charCodeAt(I).toString();
    }
    var sPos = Math.floor(prand.length / 5);
    var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));
    var incr = Math.round(pwd.length / 2);
    var modu = Math.pow(2, 31) - 1;
    var salt = parseInt(str.substring(str.length - 8, str.length), 16);
    str = str.substring(0, str.length - 8);
    prand += salt;
    while(prand.length > 10) {
        prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
    }
    prand = (mult * prand + incr) % modu;
    var enc_chr = "";
    var enc_str = "";
    for(var I = 0; I < str.length; I += 2) {
        enc_chr = parseInt(parseInt(str.substring(I, I + 2), 16) ^ Math.floor((prand / modu) * 255));
        enc_str += String.fromCharCode(enc_chr);
        prand = (mult * prand + incr) % modu;
    }
    return unescape(enc_str);
}

function Encrypt(str) {
    if(str == "") return "";
    str = escape(str);
    var pwd = '123!QAZ';
    pwd = escape(pwd);
    if(pwd == null || pwd.length <= 0) {
        //alert("Please enter a password with which to encrypt the message.");
        return null;
    }
    var prand = "";
    for(var I = 0; I < pwd.length; I++) {
        prand += pwd.charCodeAt(I).toString();
    }
    var sPos = Math.floor(prand.length / 5);
    var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));
    var incr = Math.ceil(pwd.length / 2);
    var modu = Math.pow(2, 31) - 1;
    if(mult < 2) {
        //alert("Algorithm cannot find a suitable hash. Please choose a different password. /nPossible considerations are to choose a more complex or longer password.");
        return null;
    }
    var salt = Math.round(Math.random() * 1000000000) % 100000000;
    prand += salt;
    while(prand.length > 10) {
        prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
    }
    prand = (mult * prand + incr) % modu;
    var enc_chr = "";
    var enc_str = "";
    for(var I = 0; I < str.length; I++) {
        enc_chr = parseInt(str.charCodeAt(I) ^ Math.floor((prand / modu) * 255));
        if(enc_chr < 16) {
            enc_str += "0" + enc_chr.toString(16);
        } else
            enc_str += enc_chr.toString(16);
        prand = (mult * prand + incr) % modu;
    }
    salt = salt.toString(16);
    while(salt.length < 8) salt = "0" + salt;
    enc_str += salt;
    return enc_str;
}
//html页面url参数解密
function Decrypt(str, pwd) {
    if(str == "") return "";
    var pwd = '123!QAZ';
    pwd = escape(pwd);
    if(str == null || str.length < 8) {
        //alert("A salt value could not be extracted from the encrypted message because it's length is too short. The message cannot be decrypted.");
        return;
    }
    if(pwd == null || pwd.length <= 0) {
        //alert("Please enter a password with which to decrypt the message.");
        return;
    }
    var prand = "";
    for(var I = 0; I < pwd.length; I++) {
        prand += pwd.charCodeAt(I).toString();
    }
    var sPos = Math.floor(prand.length / 5);
    var mult = parseInt(prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));
    var incr = Math.round(pwd.length / 2);
    var modu = Math.pow(2, 31) - 1;
    var salt = parseInt(str.substring(str.length - 8, str.length), 16);
    str = str.substring(0, str.length - 8);
    prand += salt;
    while(prand.length > 10) {
        prand = (parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length))).toString();
    }
    prand = (mult * prand + incr) % modu;
    var enc_chr = "";
    var enc_str = "";
    for(var I = 0; I < str.length; I += 2) {
        enc_chr = parseInt(parseInt(str.substring(I, I + 2), 16) ^ Math.floor((prand / modu) * 255));
        enc_str += String.fromCharCode(enc_chr);
        prand = (mult * prand + incr) % modu;
    }
    return unescape(enc_str);
}

function uploadFile(index, name, url){
    this.index = index;
    this.name = name;
    this.url = url;
}

