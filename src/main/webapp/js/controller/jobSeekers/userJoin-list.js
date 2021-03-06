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
    'seekers-api',
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
    seekersApi,
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
            mainTable = MyController.renderTable();
            MyController.bindEvent();
        },
        getQueryCondition: function () {
            var condition = formUtil.composeData($("#condition"));
            return condition;
        },
        renderTable: function () {
            return $table.render({
                elem: '#seekers-list'
                , height: 'full-100'
                , url: seekersApi.getUrl('userJoinList').url
                , method: 'post'
                , page: true //开启分页
                , limits: [10, 50, 100, 200]
                , cols: [[ //表头
                    {type: 'numbers'},
                    {field: 'userId', title: '用户ID', width: 100},
                    {field: 'realName', title: '真实姓名', width: 120},
                    {field: 'phone', title: '手机号', width: 120},
                    {field: 'nickName', title: '用户昵称', width: 120},
                    {field: 'partTimeId', title: '职位ID', width: 100},
                    {field: 'title', title: '职位名称', width: 200},
                    {
                        field: 'type', title: '类型', width: 120, templet: function (d) {
                            if (d.type == 1) {
                                return '<span>报名</span>';
                            } else if (d.type == 2) {
                                return '<span>复制</span>';
                            } else if (d.type == 3) {
                                return '<span>浏览</span>';
                            } else {
                                return '<span>未知</span>';
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
                    }
                ]]
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
        }
    };

    window.list = {
        refresh: MyController.refresh
    };

    MyController.init();

});