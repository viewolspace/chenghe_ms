
/**
 * 求职者查询
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../jobSeekers/',
		"userList": {
			type: 'POST',
			url: "userList.do"
		},
		"userJoinList": {
			type: 'POST',
			url: "userJoinList.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('seekers-api', result);
});