
/**
 * 用户管理api
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../statistics/',
		"userStatList": {
			type: 'POST',
			url: "userStatList.do"
		},
		"partTimeStatList": {
			type: 'POST',
			url: "partTimeStatList.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('statistics-api', result);
});