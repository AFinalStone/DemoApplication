
/*****************************页面信息加载***************************/

var showImgIfo = JSON.parse(localStorage.getItem("showImgIfo"));//从缓存获取预览信息

showImgIfo = $.parseJSON(showImgIfo);

var IOUimg = "<img src='"+ showImgIfo.imageUrl +"'>";
$(".template_list").html(IOUimg);
if(showImgIfo.scheduleReturnDate!==""){
	$("#scheduleReturnDate").text(showImgIfo.scheduleReturnDate);
	$(".pagenum").show();
}

/*************************方法*****************************/

function storegeClear() {//清除缓存,任何操作离开本页面都需要调用改方法
    localStorage.clear();
}

function backForApp() {//返回操作
    window.WebViewJavascriptBridge.callHandler('goto');
    storegeClear();
}

function sharePreviewForApp() {//分享操作
    window.WebViewJavascriptBridge.callHandler('shareFunIOU');
}


/******************************点击事件******************************/

$(".pre_btn").on("click",function () {//返回按钮
    backForApp();
});

$(".share_btn").on("click",function () {
    sharePreviewForApp();
});

/****************************APP方法***********************************/

function setupWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
    if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
    window.WVJBCallbacks = [callback];
    var WVJBIframe = document.createElement('iframe');
    WVJBIframe.style.display = 'none';
    WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
    document.documentElement.appendChild(WVJBIframe);
    setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
}

setupWebViewJavascriptBridge(function(bridge) {

});
