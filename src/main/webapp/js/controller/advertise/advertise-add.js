var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: 2018011001
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'ad-api',
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
    adApi,
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
            parentId: '00000006'
        }, function(result) {
            formUtil.renderSelects('#categoryId', result.data, false);
            f.render('select');
        },
        false
    );

    //上传图片
    upload.render({
        elem: '#imageBtn'
        , url: uploadImageApi.getUrl('uploadImg').url
        , ext: 'jpg|png|gif|bmp'
        , type: 'image'
        , size: 1024 //最大允许上传的文件大小kb
        , before: function (obj) {
            //预读本地文件
            layer.load(0, {
                shade: 0.5
            });
        }
        , done: function (res) {
            layer.closeAll('loading');
            if (res.status == false) {
                return layer.msg('上传失败');
            } else {
                $('#imageAvatarId').attr('src', res.imageUrl);
                $('#logo').val(res.imageUrl);
                toast.msg("上传成功");
            }
        }
        , error: function () {
            layer.closeAll('loading');
            return layer.msg('数据请求异常');
        }
    });

    f.on('submit(ad-add-form)', function (data) {
        ajax.request(adApi.getUrl('addAd'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});