var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: true
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'dictionary-api',
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
    dictionaryApi,
    uploadImageApi,
    toast,
    upload
) {
    var $ = layui.jquery;
    var f = layui.form;
    var data = ajax.getAllUrlParam();
    //给父节点赋值
    $("#pid").val(data.id);

    ajax.request(
        dictionaryApi.getUrl('listDataDic'), {
            parentId: '00000001'
        }, function (result) {
            formUtil.renderSelects('#appId', result.data, true);
            f.render('select');
        },
        false
    );

    f.on('submit(category-add-form)', function (data) {
        ajax.request(dictionaryApi.getUrl('addSysDictionary'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});