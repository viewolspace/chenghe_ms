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
    'position-api',
    'table-util',
    'btns',
    'authority',
    'toast',
    'table',
    'valid-login'

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
    positionApi,
    tableUtil,
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
            var condition = formUtil.composeData($("#condition"));
            return condition;
        },
        renderTable: function () {
            return $table.render({
                elem: '#position-list'
                , height: 'full-100'
                , url: positionApi.getUrl('positionList').url
                , method: 'post'
                , page: true //开启分页
                , limits: [10, 50, 100, 200]
                , cols: [[ //表头
                    {type: 'numbers'},

                    {field: 'id', title: '职位ID', width: 100},
                    {field: 'title', title: '标题', width: 200},
                    {field: 'companyId', title: '商户id', width: 120},
                    {
                        field: 'recommend', title: '推荐', width: 100, templet: function (d) {
                            if (d.recommend == 0) {
                                return '<span>正常</span>';
                            } else if (d.recommend == 1) {
                                return '<span style="background-color:blue;">兼职圈_首页推荐</span>';
                            } else if (d.recommend == 2) {
                                return '<span style="background-color:blue;">兼职圈_精选</span>';
                            } else if (d.recommend == 3) {
                                return '<span style="background-color:blue;">兼职圈_热门</span>';
                            }else if (d.recommend == 4) {
                                return '<span style="background-color:yellow;">土豆_首页推荐</span>';
                            } else if (d.recommend == 5) {
                                return '<span style="background-color:yellow;">土豆_精选</span>';
                            } else if (d.recommend == 6) {
                                return '<span style="background-color:yellow;">土豆_热门</span>';
                            }else if (d.recommend == 7) {
                                return '<span style="background-color:pink;">彩虹兼职_首页推荐</span>';
                            } else if (d.recommend == 8) {
                                return '<span style="background-color:pink;">彩虹兼职_精选</span>';
                            } else if (d.recommend == 9) {
                                return '<span style="background-color:pink;">彩虹兼职_热门</span>';
                            } else {
                                return '<span>其它</span>';
                            }
                        }
                    },
                    // {field: 'categoryId', title: '职位分类id', width: 120},
                    {field: 'categoryName', title: '职位分类名称', width: 120},
                    {field: 'topNum', title: '置顶顺序', width: 120},
                    {field: 'salary', title: '薪水', width: 120},
                    // {field: 'cycle', title: '周期0小时1天2周3月4季度', width: 120},
                    // {field: 'lable', title: '多个标签空格分隔例如：日结长期男女不限', width: 120},
                    // {
                    //     field: 'contactType', title: '联系方式类型', width: 120, templet: function (d) {
                    //         if (d.contactType == 1) {
                    //             return '<span>QQ</span>';
                    //         } else if (d.contactType == 2) {
                    //             return '<span>微信</span>';
                    //         } else {
                    //             return '<span>手机</span>';
                    //         }
                    //     }
                    // },
                    // {field: 'contact', title: '联系方式', width: 120},
                    // {field: 'content', title: '详细内容', width: 120},
                    {field: 'num', title: '招聘人数', width: 120},
                    {field: 'workTime', title: '工作时间', width: 120},
                    {field: 'workAddress', title: '工作地点', width: 120},
                    {
                        field: 'status', title: '状态', width: 100, templet: function (d) {
                            if (d.status == 0) {
                                return '<span>有效</span>';
                            } else {
                                return '<span>无效</span>';
                            }
                        }
                    },
                    // {field: 'browseNum', title: '浏览人数', width: 120},
                    // {field: 'copyNum', title: '复制人数', width: 120},
                    // {field: 'joinNum', title: '参与人数', width: 120},
                    {
                        field: 'sTime', title: '开始时间', width: 160, templet: function (d) {
                            if (d.sTime) {
                                return moment(d.sTime).format("YYYY-MM-DD HH:mm:ss");
                            } else {
                                return "";
                            }

                        }
                    },
                    {
                        field: 'eTime', title: '结束时间', width: 160, templet: function (d) {
                            if (d.eTime) {
                                return moment(d.eTime).format("YYYY-MM-DD HH:mm:ss");
                            } else {
                                return "";
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
            layer.open({
                type: 2,
                title: '添加职位',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['900px', '450px'],
                offset: '5%',
                scrollbar: false,
                content: webName + '/views/position/position-add.html',
                success: function (ly, index) {
                    // layer.iframeAuto(index);
                }
            });
        },

        modify: function (rowdata) {
            var url = request.composeUrl(webName + '/views/position/position-update.html', rowdata);
            var index = layer.open({
                type: 2,
                title: '修改职位',
                shadeClose: true,
                shade: false,
                maxmin: true, //开启最大化最小化按钮
                area: ['900px', '450px'],
                offset: '5%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    // layer.iframeAuto(index);
                }
            });
        },

        upodateContact: function (rowdata) {
            var url = request.composeUrl(webName + '/views/position/contact-update.html', rowdata);
            var index = layer.open({
                type: 2,
                title: '修改联系方式',
                area: ['800px', '450px'],
                offset: '5%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    // layer.iframeAuto(index);
                }
            });
        },

        view: function (rowdata) {
            var url = request.composeUrl(webName + '/views/user/user-view.html', rowdata);
            var index = layer.open({
                type: 2,
                title: "查看职位",
                area: '60%',
                offset: '10%',
                scrollbar: false,
                content: url,
                success: function (ly, index) {
                    // layer.iframeAuto(index);
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

                request.request(positionApi.getUrl('deletePosition'), {
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
                } else if (obj.event === 'row-contact') {//修改联系方式
                    MyController.upodateContact(data);
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