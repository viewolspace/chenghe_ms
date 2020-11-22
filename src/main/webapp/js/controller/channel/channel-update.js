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
    var param = ajax.getAllUrlParam();

    ajax.request(
        dictionaryApi.getUrl('listDataDic'), {
            parentId: '00000001'
        }, function (result) {
            formUtil.renderSelects('#appId', result.data, false);
            f.render('select');
        },
        false
    );

    if (!$.isEmptyObject(param)) {
        formUtil.renderData($('#channel-update-form'), param);
    }



    f.on('submit(channel-update-form)', function (data) {
        ajax.request(channelApi.getUrl('updateChannelView'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('修改成功');
        });
        return false;
    });

});