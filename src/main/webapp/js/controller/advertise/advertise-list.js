var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: 2018011001
});

var requireModules = [
    'element',
    'form',
    'layer',
    'request',
    'form-util',
    'ad-api',
    'position-category-api',
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
    adApi,
    positionCategoryApi,
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

            request.request(
                positionCategoryApi.getUrl('listDataDic'),{
                    parentId: '00000002'
                }, function(result) {
                    formUtil.renderSelects('#categoryId', result.data, true);
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
                elem: '#ad-list'
                , height: 'full-100'
                , url: adApi.getUrl('adList').url
                , method: 'post'
                , page: true
                , limits: [10, 50, 100, 200]
                , cols: [[
                    {type: 'numbers'},
                    {field: 'id', title: '广告ID', width: 100},
                    {field: 'title', title: '标题', width: 220},
                    {field: 'categoryName', title: '所属分类', width: 120},
                    {
                        field: 'imageUrl', title: '图片地址', width: 100, templet: function (d) {
                            if (d.imageUrl) {
                                return "<img src='" + d.imageUrl + "' />";
                            } else {
                                return "";
                            }
                        }
                    },
                    {field: 'url', title: '跳转地址', width: 150},
                    {field: 'num', title: '顺序', width: 120},
                    {
                        field: 'status', title: '状态', width: 100, templet: function (d) {
                            if (d.status == 0) {
                                return '<span>有效</span>';
                            } else {
                                return '<span>无效</span>';
                            }
                        }
                    },
                    {
                        field: 'cTime', title: '创建时间', width: 160, templet: function (d) {
                            if (d.cTime) {
                                return moment(d.cTime).format("YYYY-MM-DD HH:mm:ss");
                            } else {
                                return "";
                            }

                        }
                    },
                    {
                        field: 'mTime', title: '修改时间', width: 160, templet: function (d) {
                            if (d.mTime) {
                                return moment(d.mTime).format("YYYY-MM-DD HH:mm:ss");
                            } else {
                                return "";
                            }

                        }
                    },
                    {fixed: 'right', width: 140, align: 'center', toolbar: '#barDemo'}
                ]]
            });
        },

        add: function () {
            var index = layer.open({
                type: 2,
                title: "添加广告",
                area: ['700px', '420px'],
                offset: '5%',
                scrollbar: false,
                content: webName + '/views/advertise/advertise-add.html',
                success: function (ly, index) {
                    layer.iframeAuto(index);
                }
            });
        },

        modify: function (rowdata) {
            var url = request.composeUrl(webName + '/views/advertise/advertise-update.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "修改广告",
                area: ['700px', '420px'],
                offset: '5%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    layer.iframeAuto(index);
                }
            });
        },

        view: function (rowdata) {
            var url = request.composeUrl(webName + '/views/advertise/advertise-view.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "查看广告",
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

                request.request(adApi.getUrl('deleteAd'), {
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