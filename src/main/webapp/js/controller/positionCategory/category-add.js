var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: 2018011001
});

var requireModules = [
    'form',
    'form-util',
    'request',
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
    positionCategoryApi,
    uploadImageApi,
    toast,
    upload
) {
    var $ = layui.jquery;
    var f = layui.form;
    var data = ajax.getAllUrlParam();
    //给父节点赋值
    $("#pid").val(data.id);

    f.on('submit(category-add-form)', function (data) {
        ajax.request(positionCategoryApi.getUrl('addCategory'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});