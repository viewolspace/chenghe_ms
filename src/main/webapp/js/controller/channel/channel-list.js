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
    'channel-api',
    'btns',
    'authority',
    'toast',
    'table',
    'dictionary-api'

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
    channelApi,
    btns,
    authority,
    toast,
    table,
    dictionaryApi
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

            request.request(
                dictionaryApi.getUrl('listDataDic'), {
                    parentId: '00000001'
                }, function (result) {
                    formUtil.renderSelects('#appId', result.data, true);
                    form.render('select');
                },
                false
            );

            mainTable = MyController.renderTable();
            MyController.bindEvent();
        },
        getQueryCondition: function () {
            var condition = formUtil.composeData($("#condition"));
            return condition;
        },
        renderTable: function () {
            return $table.render({
                elem: '#channel-list'
                , height: 'full-100'
                , url: channelApi.getUrl('channelViewList').url
                , method: 'post'
                , page: true
                , limits: [10, 50, 100, 200]
                , cols: [[
                    {type: 'numbers'},
                    {field: 'id', title: '主键', width: 100},
                    {field: 'appId', title: 'App Id', width: 150},
                    {field: 'appName', title: 'App名称', width: 150},
                    {field: 'channel', title: '渠道', width: 300},
                    {field: 'version', title: '版本号', width: 200},
                    {
                        field: 'type', title: '类型', width: 300, templet: function (d) {
                            if (d.type == 1) {
                                return '<span>职位信息</span>';
                            } else {
                                return '<span>其它</span>';
                            }
                        }
                    },
                    {fixed: 'right', width: 200, align: 'center', toolbar: '#barDemo'}
                ]]
            });
        },

        add: function () {
            var index = layer.open({
                type: 2,
                title: "添加渠道",
                area: ['1000px', '700px'],
                offset: '5%',
                scrollbar: false,
                content: webName + '/views/channel/channel-add.html',
                success: function (ly, index) {
                    // layer.iframeAuto(index);
                }
            });
        },

        modify: function (rowdata) {
            var url = request.composeUrl(webName + '/views/channel/channel-update.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "修改渠道",
                area: ['1000px', '700px'],
                offset: '5%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    // layer.iframeAuto(index);
                }
            });
        },

        view: function (rowdata) {
            var url = request.composeUrl(webName + '/views/channel/channel-view.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "查看渠道",
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

                request.request(channelApi.getUrl('delChannelView'), {
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