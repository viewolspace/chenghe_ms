
/**
 * 上传图片
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../upload/',
		"uploadImg": {
			type: 'POST',
			url: "uploadImg.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('uploadImage-api', result);
});