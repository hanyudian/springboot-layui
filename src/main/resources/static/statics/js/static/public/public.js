layui.use(['miniTab'], function () {
    var $ = layui.jquery,
        miniTab = layui.miniTab;

    miniTab.listen();

    /**
     * 查看公告信息
     **/
    $('body').on('click', '.layuimini-notice', function () {
        var title = $(this).children('.layuimini-notice-title').text(),
            noticeTime = $(this).children('.layuimini-notice-extra').text(),
            content = $(this).children('.layuimini-notice-content').html();
        var html = '<div style="padding:15px 20px; text-align:justify; line-height: 22px;border-bottom:1px solid #e2e2e2;background-color: #2f4056;color: #ffffff">\n' +
            '<div style="text-align: center;margin-bottom: 20px;font-weight: bold;border-bottom:1px solid #718fb5;padding-bottom: 5px"><h4 class="text-danger">' + title + '</h4></div>\n' +
            '<div style="font-size: 12px">' + content + '</div>\n' +
            '</div>\n';
        parent.layer.open({
            type: 1,
            title: '系统公告'+'<span style="float: right;right: 1px;font-size: 12px;color: #b1b3b9;margin-top: 1px">'+noticeTime+'</span>',
            area: '300px;',
            shade: 0.8,
            id: 'layuimini-notice',
            btn: ['查看', '取消'],
            btnAlign: 'c',
            moveType: 1,
            content:html,
            success: function (layero) {
                var btn = layero.find('.layui-layer-btn');
                btn.find('.layui-layer-btn0').attr({
                    href: 'https://gitee.com/zhongshaofa/layuimini',
                    target: '_blank'
                });
            }
        });
    });
});