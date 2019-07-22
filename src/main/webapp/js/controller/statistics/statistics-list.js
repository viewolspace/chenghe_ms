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
    'statistics-api',
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
    statisticsApi,
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
                elem: '#statistics-list'
                , height: 'full-100'
                , url: statisticsApi.getUrl('userStatList').url
                , method: 'post'
                , page: true //开启分页
                , limits: [10, 50, 100, 200]
                , cols: [[ //表头
                    {type: 'numbers'},
                    {field: 'statDate', title: '统计日期', width: 100},
                    {field: 'userActive', title: '今日激活用户数', width: 120},
                    {field: 'userRegister', title: '今日注册用户数', width: 220},
                    {field: 'userIdfa', title: '今日新增idfa', width: 150},
                    {field: 'browseNum', title: '总浏览人数', width: 150},
                    {field: 'copyNum', title: '总复制人数', width: 150},
                    {field: 'joinNum', title: '总参与人数', width: 150}
                ]]
            });
        },

        refresh: function () {
            mainTable.reload();
        },

        bindEvent: function () {
            //点击查询按钮
            $('#search-btn').on('click', function () {
                mainTable.reload({
                    where: MyController.getQueryCondition()
                });
            });
        }
    };

    window.list = {
        refresh: MyController.refresh
    };

    MyController.init();

});