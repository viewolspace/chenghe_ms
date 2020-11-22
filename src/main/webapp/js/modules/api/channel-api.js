
/**
 * 渠道管理
 */
var requireModules =[
	'base-url'
];

window.top.registeModule(window,requireModules);
layui.define('base-url', function(exports) {
	var $ = layui.jquery;
	var baseApi = layui['base-url'];

	var url = {
		namespace: '../channel/',
		"channelViewList": {
			type: 'POST',
			url: "channelViewList.do"
		},
		"addChannelView": {
			type: 'POST',
			url: "addChannelView.do"
		},
        "updateChannelView": {
            type: 'POST',
            url: "updateChannelView.do"
        },
		"delChannelView": {
			url: "delChannelView.do"
		},
		"getChannelView": {
			type: 'POST',
			url: "getChannelView.do"
		}
	};

	var result = $.extend({}, baseApi, url);

	exports('channel-api', result);
});