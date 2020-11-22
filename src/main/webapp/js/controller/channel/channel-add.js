var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: true
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'channel-api',
    'toast',
    'upload',
    'laydate',
    'dictionary-api'
];

registeModule(window, requireModules, {
    'role&authority-api': 'api/role&authority-api'
});

layui.use(requireModules, function (
    form,
    formUtil,
    ajax,
    channelApi,
    toast,
    upload,
    laydate,
    dictionaryApi
) {
    var $ = layui.jquery;
    var f = layui.form;
    var data = ajax.getAllUrlParam();

    ajax.request(
        dictionaryApi.getUrl('listDataDic'), {
            parentId: '00000001'
        }, function (result) {
            formUtil.renderSelects('#appId', result.data, false);
            f.render('select');
        },
        false
    );

    f.on('submit(channel-add-form)', function (data) {
        ajax.request(channelApi.getUrl('addChannelView'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});