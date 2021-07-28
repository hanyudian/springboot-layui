var header = {};
var token = sessionStorage.getItem("gwdtz_token");
if(typeof token != "undefined" && token != null) {
    header["Authorization"] = token;
}
var username = sessionStorage.getItem("gwdtz_username");
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
var contentid = RequestParameter()["contentid"];
var zTree, rMenu;
$(document).ready(function() {
    // //加载树
    loadTree();
    $("#nodeId").val('');
    $("#name").val('');
    rMenu = $("#rMenu");
});
//绑定数据
function Bind(contentid){
    ajaxGet("/content/selectDeptById", function(ajaxRet) {
        // console.log(ajaxRet.data)
        zTree = $.fn.zTree.getZTreeObj("treeDemo");
        for(var i=0 ; i <ajaxRet.data.length; i++) {
            var node = zTree.getNodeByParam("id", ajaxRet.data[i],null);
            if(node != null){
                zTree.checkNode(node,true,false);
            }
        }
    }, {"contentid": contentid});
}

//取消选中当前节点的子节点
// function cancleChecked(node) {
//     var childs = node.children;
//     if(childs){
//         if(node.zAsync){
//             zTree = $.fn.zTree.getZTreeObj("treeDemo");
//             for (var i = 0; i < childs.length ; i++){
//                 zTree.checkNode(childs[i], false ,false);
//                 cancleChecked(childs[i]);
//             }
//         }
//     }
// }
//树加载
function loadTree() {
    $.ajax({
        headers: header,
        type: "get",
        dataType: "json",
        traditional: true,
        // data: { username: username },
        url: GWDTZ_BASE_URL + '/dept/deptList',
        async: false, //表示同步执行
        success: function(data) {
            var list = data.data;
            // console.log(list);
            if(list != null) {
                $.fn.zTree.init($("#treeDemo"), setting, list);
                zTree = $.fn.zTree.getZTreeObj("treeDemo");
                var nodes = zTree.getNodes();
                for(var i = 0; i < nodes.length; i++) { //设置节点展开
                    zTree.expandNode(nodes[i], true, false, true);
                }
                // zTree.expandAll(true);
                Bind(contentid);
            }

        }
    });
}

var setting = {
    callback: {
        onClick: onClick,//点击每个节点回调
        // onAsyncSuccess: onAsyncSuccess,//树结构加载完后回调
        onRightClick: OnRightClick,//鼠标右键点击回调
        beforeExpand: beforeExpand

    },
    view: {
        // addHoverDom: addHoverDom,
        // removeHoverDom: removeHoverDom,
        selectedMulti: true,
        dblClickExpand: true
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: 0
        },
        key: {
            name: "name",
            title:"id",
            children: "children"
        }
    },
    edit: {
        enable: false
    }


};


//数据过滤
function ajaxDataFilter(treeId, parentNode, responseData) {
    if (responseData) {
        for (var i = 0; i < responseData.length; i++) {
            responseData[i].name += "";
        }
    }
    return responseData;
}

//点击每个节点回
function onClick(e, treeId, treeNode) {
    //alert("111");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.expandNode(treeNode);
}

//树结构加载完后回调
function onAsyncSuccess(event, treeId, treeNode, msg) {
    // funcTreeObj.expandAll(true);//展开所有节点
    //默认打开顶级（第一级）节点
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodes();
        if (nodes.length > 0) {
            for (var i = 0; i < nodes.length; i++) {
                treeObj.expandNode(nodes[i], true, false, true);//默认展开第一级节点
            }
        }
    // var treeObj = $.fn.zTree.getZTreeObj(treeNode);
    // var node = treeObj.getNodeByParam("id",treeId,null);
    // treeObj.checkNode(node,true,true);
    // treeObj.expandNode(node,true,true,true);
}

function OnRightClick(event, treeId, treeNode) {////////////////
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX, event.clientY);
    }
    $('#nodeId').val(treeNode.id);
    $('#pname').val(treeNode.name)
}

function beforeExpand(treeId, treeNode){
    Bind(contentid);
}
function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type == "root") {
        $("#m_del").hide();
    } else {
        $("#m_del").show();
    }

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
    if (rMenu)
        rMenu.css({
            "visibility": "hidden"
        });
    $("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        rMenu.css({"visibility": "hidden"});
    }
}


function submit() {
    var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = zTreeObj.getCheckedNodes(true);
    var deptids=[];
    for(var i = 0; i<nodes.length; i++ ){
        deptids.push(nodes[i].id);
    }
    if(deptids.length>0){
            $.ajax({
                headers: header,
                data:{
                    contentid: contentid,
                    deptids: deptids.toString(),
                    username:username
                },
                dataType: 'json',
                type: 'post',
                url: GWDTZ_BASE_URL + '/dept/contentDeptEdit',
                success: function(result) {
                    if (result.code=='200') {
                        parent.layer.msg(result.msg.toString(), {icon: 1});
                        parent.layer.close(index);
                    } else {
                        parent.layer.msg(result.msg.toString(), {icon: 0});
                    }
                }
        });
    }else {
        layer.msg("请选择",{icon: 0});
    }
}



