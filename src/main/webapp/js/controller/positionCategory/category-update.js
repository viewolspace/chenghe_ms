var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: true
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'position-category-api',
    'uploadImage-api',
    'dictionary-api',
    'toast',
    'upload'

];

registeModule(window, requireModules, {
    'role&authority-api': 'api/role&authority-api'
});

layui.use(requireModules, function (
    form,
    formUtil,
    ajax,
    positionCategoryApi,
    uploadImageApi,
    dictionaryApi,
    toast,
    upload
) {
    var $ = layui.jquery;
    var f = layui.form;
    var param = ajax.getAllUrlParam();

    ajax.request(
        dictionaryApi.getUrl('listDataDic'), {
            parentId: '00000001'
        }, function (result) {
            formUtil.renderSelects('#appId', result.data, true);
            f.render('select');
        },
        false
    );

    if(!$.isEmptyObject(param)) {
        formUtil.renderData($('#category-update-form'), param);
    }

    f.on('submit(category-update-form)', function (data) {
        ajax.request(positionCategoryApi.getUrl('updateCategory'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});