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
    'merchant-api',
    'toast',
    'upload',
    'laydate'

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
    merchantApi,
    toast,
    upload,
    laydate
) {
    var $ = layui.jquery;
    var f = layui.form;
    var param = ajax.getAllUrlParam();

    var um = UM.getEditor('container', {
        initialFrameHeight: 200
    });
    ajax.request(
        positionApi.getUrl('getPosition'),{
            id: param.id
        }, function(result) {
            param = result.data;
            if(!$.isEmptyObject(param)) {
                param.sTime=moment(new Date(parseInt(param.sTime))).format("YYYY-MM-DD HH:mm:ss");
                param.eTime=moment(new Date(parseInt(param.eTime))).format("YYYY-MM-DD HH:mm:ss");

                formUtil.renderData($('#position-update-form'), param);

                $('#imageAvatarId').attr('src', param.pic);
                $('#imageUrl').val(param.pic);

                UM.getEditor('container').setContent(param.content);
            }
        },
        false
    );

    ajax.request(
        positionCategoryApi.getUrl('listDataDic'),{
            parentId: '00000001'
        }, function(result) {
            formUtil.renderSelects('#categoryId', result.data, false);
            f.render('select');
        },
        false
    );

    ajax.request(
        merchantApi.getUrl('listDataDic'), null, function (result) {
            formUtil.renderSelects('#companyId', result.data, false);
            f.render('select');
        },
        false
    );

    laydate.render({
        elem: '#sTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss'
    });

    laydate.render({
        elem: '#eTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss'
    });

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
                $('#imageUrl').val(res.imageUrl);
                toast.msg("上传成功");
            }
        }
        , error: function () {
            layer.closeAll('loading');
            return layer.msg('数据请求异常');
        }
    });

    f.on('submit(position-update-form)', function (data) {
        ajax.request(positionApi.getUrl('updatePosition'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});