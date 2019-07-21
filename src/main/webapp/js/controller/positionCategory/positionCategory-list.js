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
    'position-category-api',
    'table-util',
    'btns',
    'authority',
    'toast',
    'table',
    'treeTable'

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
    positionCategoryApi,
    tableUtil,
    btns,
    authority,
    toast,
    table,
    treeTable
) {

    var o = layui.$;
    var form = layui.form;
    var layer = layui.layer;
    var treeTable = layui.treeTable;
    var re = treeTable.render({
        elem: '#tree-table',
        url: positionCategoryApi.getUrl('categoryList').url,
        icon_key: 'title',
        is_checkbox: true,
        // checked: {
        //     key: 'id',
        //     data: [0, 1, 4, 10, 11, 5, 2, 6, 7, 3, 8, 9]
        // },
        end: function (e) {
            form.render();
        },
        cols: [
            {
                key: 'title',
                title: '名称',
                width: '100px',
                template: function (item) {
                    if (item.level == 0) {
                        return '<span style="color:red;">' + item.title + '</span>';
                    } else if (item.level == 1) {
                        return '<span style="color:green;">' + item.title + '</span>';
                    } else if (item.level == 2) {
                        return '<span style="color:#aaa;">' + item.title + '</span>';
                    }
                }
            },
            {
                key: 'id',
                title: 'ID',
                width: '100px',
                align: 'center'
            },
            {
                key: 'pid',
                title: '父ID',
                width: '100px',
                align: 'center'
            },
            {
                key: 'status',
                title: '是否有效',
                width: '100px',
                align: 'center',
                template: function (item) {
                    return '<input type="checkbox" name="status" value="' + item.id + '" lay-skin="switch" lay-filter="statusSwitch" lay-text="有效|无效">';
                }
            },
            {
                title: '操作',
                align: 'center',
                template: function (item) {
                    return '<a lay-filter="add">添加子节点</a> | <a lay-filter="update">修改</a> | <a lay-filter="delete">删除</a>';
                }
            }
        ]
    });

    // 监听展开关闭
    treeTable.on('tree(flex)', function (data) {
        layer.msg("dsfsd");
    });

    // 添加子分类
    treeTable.on('tree(add)', function (data) {
        var url = request.composeUrl(webName + '/views/positionCategory/category-add.html', data.item);
        var index = layer.open({
            type: 2,
            title: "添加子分类",
            area: ['700px', '450px'],
            offset: '5%',
            scrollbar: false,
            content: url,
            success: function (ly, index) {
                layer.iframeAuto(index);
            }
        });
    });

    //修改分类
    treeTable.on('tree(update)', function (data) {
        var url = request.composeUrl(webName + '/views/positionCategory/category-update.html', data.item);
        var index = layer.open({
            type: 2,
            title: "修改分类",
            area: ['700px', '450px'],
            offset: '5%',
            scrollbar: false,
            content: url,
            success: function (ly, index) {
                layer.iframeAuto(index);
            }
        });
    });

    //删除分类
    treeTable.on('tree(delete)', function (data) {
        layer.confirm('确认删除数据?', {
            icon: 3,
            title: '提示',
            closeBtn: 0
        }, function (index) {
            layer.load(0, {
                shade: 0.5
            });
            layer.close(index);

            request.request(positionCategoryApi.getUrl('deleteCategory'), {
                id: data.item.id
            }, function () {
                layer.closeAll('loading');
                toast.success('成功删除！');
            }, true, function () {
                layer.closeAll('loading');
            });
        });
    });

    //监听分类状态切换
    form.on('switch(statusSwitch)', function (obj) {
        var id = obj.value;
        var checked = obj.elem.checked;
        layer.msg("id:" + id + ", checked:" + checked);

    });

    // 获取选中值，返回值是一个数组（定义的primary_key参数集合）
    o('.get-checked').click(function () {
        layer.msg('选中参数' + treeTable.checked(re).join(','))
    });

    //添加一级分类
    o('.refresh').click(function () {
        var index = layer.open({
            type: 2,
            title: "添加一级分类",
            area: '80%',
            offset: '10%',
            scrollbar: false,
            content: webName + '/views/positionCategory/category-add.html',
            success: function (ly, index) {
                layer.iframeAuto(index);
            }
        });
    });

    // 全部展开
    o('.open-all').click(function () {
        treeTable.openAll(re);
    });

    // 全部关闭
    o('.close-all').click(function () {
        treeTable.closeAll(re);
    });

});