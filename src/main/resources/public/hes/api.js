(function() {
    var wechatBaseUrl = "../wechat"
    var clientInfoUrl = "../clientInfo"

    var api = {}

    //wechat部分
    api.getWechatUserList = function (param) {
        return axios.post(wechatBaseUrl+ '/getWechatUserList', param)
    }

    //clientInfo部分
    api.getClientInfoList = function (param) {
        return axios.post(clientInfoUrl+ '/getClientInfoList', param)
    }


    window.api = api
})();
