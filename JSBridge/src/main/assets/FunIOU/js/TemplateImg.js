
/*
* 从缓存获取插画数据
* 从url获取编辑信息
* 初始加载部分图片
* 滚动加载剩余图片
*/


/***********************数据**************************/

var editIfo = JSON.parse(localStorage.getItem("editIfo"));//从缓存获取edit信息
var Ilist;
var loadNum = 0;//已加载图片数量
var OnLoadNum = 10;//初始加载图片数量
var delayLoadNum = 5;//每次图片加载数量
var illustrationArr = JSON.parse(localStorage.getItem("illustrationArr"));
var IlistId = illustrationArr.illustrationArr;
console.log(IlistId);
var IlistArr = [];//图片ID数组

/************************解析url***********************/
var strurl = window.location.href;//获取当前url
var index=strurl.lastIndexOf("?");
strurl=strurl.substring(index+1,strurl.length);
var words = strurl.split('&');
var arr =[];//url解析数组
for(var i=0;i<words.length;i++){
    var splitLocation = words[i].indexOf("=");
    var z = decodeURIComponent(words[i].substring(splitLocation+1,words[i].length));
    arr.push(z)
}

/**********************加载插画***********************/

if(IlistId!="" || IlistId!=null){
    IlistId = IlistId.split(",");//切割图片数据
    var taotalNum = IlistId.length;
    for(var i=0;i<taotalNum;i++){
        var a = IlistId[i].replace(/"/g,"");
        a= a.replace(/\[/g,"");
        a= a.replace(/]/g,"");
        console.log(a);
        IlistArr.push(a)
    }
    var s = "";
    for(var i=0;i<IlistId.length;i++){
        s+='<li illustrationId='+IlistArr[i]+'><img/></li>'
    }
    $(".Img_list ul").html(s);
    Ilist = $(".Img_list ul li");
    for (var a=0; a<OnLoadNum; a++){
        loadPic(Ilist,loadNum,IlistArr);
        loadNum += 1;
    }
}else {
    alert("数据错误");
}

/***************************滚动加载图片****************************/

var frameHeight = $($(".Img_list_ul li")[0]).height();//单张插画li高度
var loadRange =frameHeight * delayLoadNum; //滚动加载高度
$(".Img_list").scroll(function () {//插画列表滚动事件
    var scrollTop = $(".Img_list_ul").offset().top.toString();
    var scrollRange = (scrollTop -88.5) * (-1);
    if(scrollRange%loadRange < 50 && scrollRange > frameHeight*2){
        if(loadNum < IlistId.length){
            for(var a = 0; a < 5; a++){
                loadPic(Ilist,loadNum,IlistArr);
                loadNum += 1;
            }
        }
    }
});

;

/************************页面点击事件**************************/

$(".Img_list li").on("click",function(){//图片点击
    $(this).addClass("img_sel").siblings().removeClass("img_sel");
});
$(".Img_list li:eq(0)").trigger("click");

function loadPic(obj,location,arr) {//加载图片方法
    $($(obj[location]).find("img")).attr("src",'http://iou-steward.oss-cn-hangzhou.aliyuncs.com/IOU/Fun/Illustration/'+arr[location]);
}

$("#generate").on("click",function () {//生成借条
    generate();
});


/*************************APP调用传参方法***************************/

//var getImgIfo = "{\"scheduleReturnDate\":\"20190323\",\"iouId\":\"56e3f47b7e6243ab8518c4d956c46ae2\",\"imageUrl\":\"http://iou-steward.oss-cn-hangzhou.aliyuncs.com/IOU/Fun/User/c91bc66726824bb7a854ea41ec4fd2b3/56e3f47b7e6243ab8518c4d956c46ae2/Original/ab1db3fcf7ce4e098c98a51a24294c67.png\"}";

function createFunIOUByAPP(data){
    window.WebViewJavascriptBridge.callHandler('createFunIOUByAPP', data);
    //getShowDataByApp(getImgIfo);
}

function generate(){//传值给APP函数
//console.log(templateId,borrowerName,loanerName,thinesName,todo,scheduleReturnDate)
    var illustrationId = $('.img_sel').attr("illustrationid");//插画ID
    editIfo.illustrationId = illustrationId;//补全edit信息，可以直接用来传参
    createFunIOUByAPP(editIfo);
}

function getShowDataByApp(data) {//完成传参后由app调用此方法，获取借条信息后跳转预览
    creatIfo = data;
    createFunIOU(creatIfo);
}

//createFunIOU({"scheduleReturnDate":"zzzzzzzz","iouId":"xxxxxxxxx","imageData":"yyyyyyyyyy"})

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


function endPage(data) { //页面结束方法

}


/***************************页面方法****************************/

function createFunIOU(res){//APP生成借条成功后传参跳转    未同步
    localStorage.clear();
    localStorage.setItem("showImgIfo",JSON.stringify(res));
    window.location.href = "FunPreview.html"+"?imageData=" + res.imageData + "&scheduleReturnDate=" + res.scheduleReturnDate;
}