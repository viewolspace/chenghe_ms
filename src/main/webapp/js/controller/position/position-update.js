var webName = getWebName();

layui.config({
    base: webName + '/js/modules/',
    version: true
});

var requireModules = [
    'form',
    'form-util',
    'request',
    'position-api',
    'position-category-api',
    'uploadImage-api',
    'merchant-api',
    'dictionary-api',
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
    dictionaryApi,
    toast,
    upload,
    laydate
) {
    var $ = layui.jquery;
    var f = layui.form;
    var param = ajax.getAllUrlParam();

    ajax.request(
        dictionaryApi.getUrl('listDataDicByApp'),{
            parentId: '00000002'
        }, function(result) {
            formUtil.renderSelects('#recommend', result.data, false);
            f.render('select');
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

    if(param.ext != undefined){
        $.each(JSON.parse(param.ext), function (index, element) {
            if (index == 0) {
                $('#contactType').val(element.contactType);
                $('#contact').val(element.contact);
                f.render('select');
            } else {
                var html;
                if(element.contactType == 1){
                    html = '<div class="layui-form-item"><div class="layui-inline"><label class="layui-form-label">联系方式</label><div class="layui-input-inline"><select name="contactType" id="contactType"><option value="1" selected>QQ</option><option value="2">微信</option><option value="3">手机</option></select></div></div><div class="layui-inline"><label class="layui-form-label">号码</label><div class="layui-input-inline"><input type="text" id="contact" name="contact" lay-verify="required" value="'+element.contact+'" class="layui-input"></div></div><button class="layui-btn" type="button" id="add">添加</button><button class="layui-btn layui-btn-danger" type="button" id="del">删除</button></div>';
                } else if (element.contactType == 2){
                    html = '<div class="layui-form-item"><div class="layui-inline"><label class="layui-form-label">联系方式</label><div class="layui-input-inline"><select name="contactType" id="contactType"><option value="1">QQ</option><option value="2" selected>微信</option><option value="3">手机</option></select></div></div><div class="layui-inline"><label class="layui-form-label">号码</label><div class="layui-input-inline"><input type="text" id="contact" name="contact" lay-verify="required" value="'+element.contact+'" class="layui-input"></div></div><button class="layui-btn" type="button" id="add">添加</button><button class="layui-btn layui-btn-danger" type="button" id="del">删除</button></div>';
                } else if (element.contactType == 3){
                    html = '<div class="layui-form-item"><div class="layui-inline"><label class="layui-form-label">联系方式</label><div class="layui-input-inline"><select name="contactType" id="contactType"><option value="1">QQ</option><option value="2">微信</option><option value="3" selected>手机</option></select></div></div><div class="layui-inline"><label class="layui-form-label">号码</label><div class="layui-input-inline"><input type="text" id="contact" name="contact" lay-verify="required" value="'+element.contact+'" class="layui-input"></div></div><button class="layui-btn" type="button" id="add">添加</button><button class="layui-btn layui-btn-danger" type="button" id="del">删除</button></div>';
                }
                $('#contactRow').append(html);
                f.render('select');
            }
        });
    }

    $(document).on('click', '#add', function () {
        var html = '<div class="layui-form-item"><div class="layui-inline"><label class="layui-form-label">联系方式</label><div class="layui-input-inline"><select name="contactType" id="contactType"><option value="1">QQ</option><option value="2">微信</option><option value="3">手机</option></select></div></div><div class="layui-inline"><label class="layui-form-label">号码</label><div class="layui-input-inline"><input type="text" id="contact" name="contact" lay-verify="required" autocomplete="off" class="layui-input"></div></div><button class="layui-btn" type="button" id="add">添加</button><button class="layui-btn layui-btn-danger" type="button" id="del">删除</button></div>';
        $(this).parent().after(html);
        f.render('select');
    });

    $(document).on('click', '#del', function () {
        $(this).parent().remove();
        toast.msg('删除成功');
    });


    f.on('submit(position-update-form)', function (data) {
        var contactArray = [];
        $("#contactRow .layui-form-item").each(function (index, element) {
            var timesRow = {};
            $(element).find("select").each(function (idx, ele) {
                if (ele.name == 'contactType') {
                    timesRow.contactType = ele.value;
                }
            });
            $(element).find("input").each(function (idx, ele) {
                if (ele.name == 'contact') {
                    timesRow.contact = ele.value;
                }
            });
            contactArray.push(timesRow);
        });
        data.field.contactArray = JSON.stringify(contactArray);

        ajax.request(positionApi.getUrl('updatePosition'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});