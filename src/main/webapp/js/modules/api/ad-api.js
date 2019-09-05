
/**
 * 广告管理
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../advertise/',
		"adList": {
			type: 'POST',
			url: "adList.do"
		},
		"addAd": {
			type: 'POST',
			url: "addAd.do"
		},
        "updateAd": {
            type: 'POST',
            url: "updateAd.do"
        },
		"deleteAd": {
			url: "deleteAd.do"
		},
		"adStatList": {
			type: 'POST',
			url: "adStatList.do"
		}
	};

	//下面这种避免不同api相同key取值相同的问题
	var result = $.extend({}, baseApi, url);

	exports('ad-api', result);
});