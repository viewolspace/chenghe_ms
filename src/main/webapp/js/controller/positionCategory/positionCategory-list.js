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
    'user-api',
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
    userApi,
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
    // 直接下载后url: './data/table-tree.json',这个配置可能看不到数据，改为data:[],获取自己的实际链接返回json数组
    var re = treeTable.render({
        elem: '#tree-table',
        data: [{"id": 1, "pid": 0, "title": "1-1"}, {"id": 2, "pid": 0, "title": "1-2"}, {
            "id": 3,
            "pid": 0,
            "title": "1-3"
        }, {"id": 4, "pid": 1, "title": "1-1-1"}, {"id": 5, "pid": 1, "title": "1-1-2"}, {
            "id": 6,
            "pid": 2,
            "title": "1-2-1"
        }, {"id": 7, "pid": 2, "title": "1-2-3"}, {"id": 8, "pid": 3, "title": "1-3-1"}, {
            "id": 9,
            "pid": 3,
            "title": "1-3-2"
        }, {"id": 10, "pid": 4, "title": "1-1-1-1"}, {"id": 11, "pid": 4, "title": "1-1-1-2"}],
        icon_key: 'title',
        is_checkbox: true,
        checked: {
            key: 'id',
            data: [0, 1, 4, 10, 11, 5, 2, 6, 7, 3, 8, 9],
        },
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
                    return '<input type="checkbox" name="status" value="'+item.id+'" lay-skin="switch" lay-filter="statusSwitch" lay-text="有效|无效">';
                }
            },
            {
                title: '操作',
                align: 'center',
                template: function (item) {
                    return '<a lay-filter="add">添加</a> | <a lay-filter="update">修改</a>';
                }
            }
        ]
    });

    // 监听展开关闭
    treeTable.on('tree(flex)', function (data) {
        layer.msg("dsfsd");
    });

    // 监听自定义
    treeTable.on('tree(add)', function (data) {
        re.data.push({"id": 50, "pid": 0, "title": "1-4"}, {"id": 51, "pid": 50, "title": "1-4-1"});
        layer.msg(JSON.stringify(data));
        treeTable.render(re);
    });

    treeTable.on('tree(update)', function (data) {
        layer.msg(JSON.stringify(data));
    });

    form.on('switch(statusSwitch)', function(obj){
        var id = obj.value;
        var checked = obj.elem.checked;
        layer.msg("id:"+id+", checked:"+checked);

    });

    // 获取选中值，返回值是一个数组（定义的primary_key参数集合）
    o('.get-checked').click(function () {
        layer.msg('选中参数' + treeTable.checked(re).join(','))
    });

    // 刷新重载树表（一般在异步处理数据后刷新显示）
    o('.refresh').click(function () {
        re.data.push({"id": 50, "pid": 0, "title": "1-4"}, {"id": 51, "pid": 50, "title": "1-4-1"});
        treeTable.render(re);
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