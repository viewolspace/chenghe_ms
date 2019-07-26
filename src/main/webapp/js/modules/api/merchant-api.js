
/**
 * 商户管理
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../merchant/',
		"addMerchant": {
			type: 'POST',
			url: "addMerchant.do"
		},
        "updateMerchant": {
            type: 'POST',
            url: "updateMerchant.do"
        },
		"deleteMerchant": {
			url: "deleteMerchant.do"
		},
		"merchantList": {
			type: 'POST',
			url: "merchantList.do"
		},
		"uploadImg": {
			type: 'POST',
			url: "uploadImg.do"
		},
		"listDataDic": {
			url: "listDataDic.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('merchant-api', result);
});