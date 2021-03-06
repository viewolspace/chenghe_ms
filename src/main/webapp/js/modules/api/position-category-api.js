
/**
 * 职位分类
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../positionCategory/',
		"addCategory": {
			type: 'POST',
			url: "addCategory.do"
		},
        "updateCategory": {
            type: 'POST',
            url: "updateCategory.do"
        },
		"deleteCategory": {
			url: "deleteCategory.do"
		},
		"categoryList": {
			type: 'POST',
			url: "categoryList.do"
		},
		"listDataDic": {
			url: "listDataDic.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('position-category-api', result);
});