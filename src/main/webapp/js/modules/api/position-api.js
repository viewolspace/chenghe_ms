
/**
 * 职位管理
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../position/',
		"addPosition": {
			type: 'POST',
			url: "addPosition.do"
		},
        "updatePosition": {
            type: 'POST',
            url: "updatePosition.do"
        },
		"positionList": {
			type: 'POST',
			url: "positionList.do"
		},
		"deletePosition": {
			url: "deletePosition.do"
		},
		"uploadContentImage": {
			type: 'POST',
			url: "uploadContentImage.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('position-api', result);
});