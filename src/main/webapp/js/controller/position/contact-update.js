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
    $('#id').val(param.id);
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
        var html = '<div class="layui-form-item contactRow"><div class="layui-inline"><label class="layui-form-label">联系方式</label><div class="layui-input-inline"><select name="contactType" id="contactType"><option value="1">QQ</option><option value="2">微信</option><option value="3">手机</option></select></div></div><div class="layui-inline"><label class="layui-form-label">号码</label><div class="layui-input-inline"><input type="text" id="contact" name="contact" lay-verify="required" autocomplete="off" class="layui-input"></div></div><button class="layui-btn" type="button" id="add">添加</button><button class="layui-btn layui-btn-danger" type="button" id="del">删除</button></div>';
        $(this).parent().after(html);
        f.render('select');
    });

    $(document).on('click', '#del', function () {
        $(this).parent().remove();
        toast.msg('删除成功');
    });

    f.on('submit(contact-update-form)', function (data) {
        var timesArray = [];
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
            timesArray.push(timesRow);
        });
        data.field.contactArray = JSON.stringify(timesArray);

        ajax.request(positionApi.getUrl('updateContact'), data.field, function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.list.refresh();
            toast.success('保存成功');
        });
        return false;
    });

});