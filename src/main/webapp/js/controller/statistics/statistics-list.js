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
    'statistics-api',
    'table-util',
    'btns',
    'authority',
    'toast',
    'table',
    'laydate'

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
    table,
    laydate
) {

    var $ = layui.jquery;
    var $table = table;
    var mainTable;
    var MyController = {
        init: function () {
            var navId = request.getFixUrlParams("navId");

            laydate.render({
                elem: '#statDate',
                type: 'date',
                format: 'yyyy-MM-dd',
                value: moment(new Date()).format("YYYY-MM-DD")
            });

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
                    {
                        field: 'statDate', title: '统计日期', width: 120, templet: function (d) {
                            if (d.statDate) {
                                return moment(d.statDate).format("YYYY-MM-DD");
                            } else {
                                return "";
                            }

                        }
                    },
                    {field: 'userRegister', title: '今日注册用户数', width: 220},
                    {field: 'browseNum', title: '总浏览次数', width: 120},
                    {field: 'browseUserNum', title: '总浏览人数', width: 150},
                    {field: 'copyNum', title: '总复制人数', width: 150},
                    {field: 'joinNum', title: '总报名人数', width: 150}
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