var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: 2018011001
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'position-api',
    'position-category-api',
    'uploadImage-api',
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
    positionApi,
    positionCategoryApi,
    uploadImageApi,
    toast,
    upload
) {
    var $ = layui.jquery;
    var f = layui.form;
    var data = ajax.getAllUrlParam();

    ajax.request(
        positionCategoryApi.getUrl('listDataDic'),{
            parentId: '00000001'
        }, function(result) {
            formUtil.renderSelects('#categoryId', result.data, false);
            f.render('select');
        },
        false
    );

    f.on('submit(position-add-form)', function (data) {
        ajax.request(positionApi.getUrl('addPosition'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});