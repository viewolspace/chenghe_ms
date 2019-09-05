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
    adApi,
    positionCategoryApi,
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
                elem: '#ad-list'
                , height: 'full-100'
                , url: adApi.getUrl('adStatList').url
                , method: 'post'
                , page: true
                , limits: [10, 50, 100, 200]
                , cols: [[
                    {type: 'numbers'},
                    {field: 'adId', title: '广告ID', width: 80},
                    {field: 'title', title: '标题', width: 220},
                    {
                        field: 'statDate', title: '统计日期', width: 120, templet: function (d) {
                            if (d.statDate) {
                                return moment(d.statDate).format("YYYY-MM-DD");
                            } else {
                                return "";
                            }

                        }
                    },
                    {field: 'companyId', title: '商户ID', width: 100},
                    {field: 'browseUserNum', title: '浏览人数', width: 100},
                    {field: 'browseNum', title: '浏览次数', width: 100},
                    // {field: 'copyNum', title: '复制人数', width: 100},
                    // {field: 'joinNum', title: '参与人数', width: 100},
                    {
                        field: 'cTime', title: '创建时间', width: 160, templet: function (d) {
                            if (d.cTime) {
                                return moment(d.cTime).format("YYYY-MM-DD HH:mm:ss");
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