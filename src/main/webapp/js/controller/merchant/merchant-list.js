var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: true
});

var requireModules = [
    'element',
    'form',
    'layer',
    'request',
    'form-util',
    'merchant-api',
    'btns',
    'authority',
    'toast',
    'table'
];

registeModule(window, requireModules, {
    'good-api': 'api/good-api'
});

//参数有顺序
layui.use(requireModules, function (
    element,
    form,
    layer,
    request,
    formUtil,
    merchantApi,
    btns,
    authority,
    toast,
    table
) {

    var $ = layui.jquery;
    var $table = table;
    var mainTable;
    var MyController = {
        init: function () {
            var navId = request.getFixUrlParams("navId");

            var totalBtns = authority.getNavBtns(navId);
            var btnObjs = btns.getBtns(totalBtns);
            MyController.pageBtns = btns.getPageBtns(btnObjs);
            MyController.switchPageBtns = btns.getSwitchPageBtns(btnObjs);

            MyController.rowBtns = btns.getRowBtns(btnObjs);
            MyController.rowSwitchBtns = btns.getSwitchBtns(MyController.rowBtns);
            MyController.rowIconBtns = btns.getIconBtns(MyController.rowBtns);

            $('#page-btns').html(btns.renderBtns(MyController.pageBtns) + btns.renderSwitchBtns(MyController.switchPageBtns));
            btns.renderLayuiTableBtns(MyController.rowIconBtns, $("#barDemo"));

            mainTable = MyController.renderTable();
            MyController.bindEvent();
        },
        getQueryCondition: function () {
            return formUtil.composeData($("#condition"));
        },
        renderTable: function () {
            return $table.render({
                elem: '#user-list'
                , height: 'full-100'
                , url: merchantApi.getUrl('merchantList').url
                , method: 'post'
                , page: true
                , limits: [10, 50, 100, 200]
                , cols: [[ //表头
                    {type: 'numbers'},
                    {field: 'id', title: '商户ID', width: 100},
                    {field: 'name', title: '商户名称', width: 120},
                    {
                        field: 'logo', title: 'Logo', width: 100, templet: function (d) {
                            if (d.logo) {
                                return "<img src='" + d.logo + "' />";
                            } else {
                                return "";
                            }
                        }
                    },
                    {field: 'qq', title: 'QQ', width: 120},
                    {field: 'wx', title: '微信', width: 120},
                    {field: 'phone', title: '手机', width: 120},
                    {field: 'star', title: '星级', width: 80},
                    {field: 'des', title: '描述', width: 300},
                    {fixed: 'right', width: 130, align: 'center', toolbar: '#barDemo'}
                ]]
            });
        },

        add: function () {
            var index = layer.open({
                type: 2,
                title: "添加商户",
                area: ['700px', '450px'],
                offset: '5%',
                scrollbar: false,
                content: webName + '/views/merchant/merchant-add.html',
                success: function(ly, index) {
                    layer.iframeAuto(index);
                }
            });
        },

        modify: function (rowdata) {
            var url = request.composeUrl(webName + '/views/merchant/merchant-update.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "修改商户",
                area: ['700px', '450px'],
                offset: '5%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    layer.iframeAuto(index);
                }
            });
        },

        view: function (rowdata) {
            var url = request.composeUrl(webName + '/views/user/user-view.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "查看用户",
                area: '60%',
                offset: '10%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    layer.iframeAuto(index);
                }
            });
        },

        delete: function (rowdata) {
            layer.confirm('确认删除数据?', {
                icon: 3,
                title: '提示',
                closeBtn: 0
            }, function (index) {
                layer.load(0, {
                    shade: 0.5
                });
                layer.close(index);

                request.request(merchantApi.getUrl('deleteMerchant'), {
                    id: rowdata.id
                }, function () {
                    layer.closeAll('loading');
                    toast.success('成功删除！');
                    MyController.refresh();
                }, true, function () {
                    layer.closeAll('loading');
                });
            });
        },

        refresh: function () {
            mainTable.reload();
        },

        bindEvent: function () {
            $table.on('tool(test)', function (obj) {
                var data = obj.data;
                if (obj.event === 'row-view') {
                    MyController.view(data);
                } else if (obj.event === 'row-edit') {//编辑
                    MyController.modify(data);
                } else if (obj.event === 'row-delete') {//删除
                    MyController.delete(data);
                }
            });

            //点击查询按钮
            $('#search-btn').on('click', function () {
                mainTable.reload({
                    where: MyController.getQueryCondition()
                });
            });

            //点击刷新
            $('body').on('click', '.refresh', MyController.refresh);
            //点击添加
            $('body').on('click', '.add', MyController.add);

        }
    };

    window.list = {
        refresh: MyController.refresh
    };

    MyController.init();

});