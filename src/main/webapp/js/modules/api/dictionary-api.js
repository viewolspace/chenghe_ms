/**
 * 数据字典
 */
var requireModules = [
    'base-url'
];

window.top.registeModule(window, requireModules);
layui.define('base-url', function (exports) {
    var $ = layui.jquery;
    var baseApi = layui['base-url'];

    var url = {
        namespace: '../sysDictionary/',
        "addSysDictionary": {
            type: 'POST',
            url: "addSysDictionary.do"
        },
        "updateSysDictionary": {
            type: 'POST',
            url: "updateSysDictionary.do"
        },
        "deleteSysDictionary": {
            url: "deleteSysDictionary.do"
        },
        "sysDictionaryList": {
            type: 'POST',
            url: "sysDictionaryList.do"
        },
        "listDataDic": {
            url: "listDataDic.do"
        },
        "listDataDicByApp": {
            url: "listDataDicByApp.do"
        }
    };
    var result = $.extend({}, baseApi, url);

    exports('dictionary-api', result);
});