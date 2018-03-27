
/******************************定义变量***********************************/

var slideLocation = 0;//定义swiper最后一个已加载图片位置
var str2Slide;//定义swiper所有list
var arr=[];//定义图片数组
var showNum =2;
var illustrationArr;//定义插画
var templateIds;//定义模板
if(localStorage.getItem("templateList")){
    templateIds = JSON.parse(localStorage.getItem("templateList"));
    listimg(templateIds);
}
localStorage.clear();//清除本地缓存



/*window.onload = function(){
    var t_img; // 定时器
    var isLoad = true; // 控制变量
    // 判断图片加载状况，加载完成后回调
    isImgLoad(function() {
        // 加载完成
        //document.body.style.background="red"
        shareTemplateIds(templateIds);
    });
    // 判断图片加载的函数
    function isImgLoad(callback) {
        // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
        // 查找所有封面图，迭代处理
        $('.template_list img').each(function() {
            // 找到为0就将isLoad设为false，并退出each
            if(this.height === 0) {
                isLoad = false;
                return false;
            }
        });
        // 为true，没有发现为0的。加载完毕
        if(isLoad) {
            clearTimeout(t_img); // 清除定时器
            // 回调函数
            callback();
            // 为false，因为找到了没有加载完成的图，将调用定时器递归
        } else {
            isLoad = true;
            t_img = setTimeout(function() {
                isImgLoad(callback); // 递归扫描
            }, 500); // 我这里设置的是500毫秒就扫描一次，可以自己调整
        }
    }

};*/

/***************************************APP事件****************************************/

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
    bridge.registerHandler('testJSFunction', function(data, responseCallback) {
        alert('JS方法被调用:'+data);
        responseCallback('js执行过了');
    })
});

function finishViewByApp(){
    window.WebViewJavascriptBridge.callHandler('finishView');
}

function showShareViewByApp(){
    var shareModuleId = $(".dinzhi").attr("id");
    window.WebViewJavascriptBridge.callHandler('showShareView', shareModuleId);
}


function shareTemplateIdsByApp(templateIds){//获取模板
    listimg(templateIds);//加载模板
}

function shareFunIllustrationIdsByApp(illustrationIds){//获取插画
    illustrationArr = illustrationIds;
}

function endPage(data) { //页面结束方法

}

/************************************页面事件****************************************/

function listimg(templatesGet){ //定义swiper
//    document.body.style.background = "red"
    //$("returnValue").value = templateIds;
    templateIds = templatesGet;
    var str = templatesGet;
    str = str.split(",");
    var taotalNum = str.length;
    for(var i=0;i<taotalNum;i++){
        var a = str[i].replace(/"|\[|\]/g,"");
        arr.push(a)
    }
    var str2 ="";
    for(var i=0;i<taotalNum;i++){
        str2 += '<div class="swiper-slide" templateId='+arr[i]+ '><div class="template_list"><img' +  '/></div></div>'
    }

    $(".swiper-wrapper").html(str2);
    str2Slide = $(".swiper-wrapper .swiper-slide");
    for(var a = 0; a < 5; a++){
        loadPic(str2Slide,slideLocation,arr);
        slideLocation += 1;
    }

    var mySwiper = new Swiper ('.swiper-container', {
        //autoplay: 500,
        direction: 'horizontal',
        effect : 'fade',
        loop: true,
        prevButton:'.swiper-button-prev',
        nextButton: '.swiper-button-next',
        pagination: '.swiper-pagination',
        paginationType : 'custom',
        paginationCustomRender: function (swiper, current, total) {
            $(".pagenum").html(current+"/"+total);
            var templateId = $(".swiper-slide:eq("+current+")").attr("templateId");
            $(".dinzhi").attr("id",templateId)
        }
    })
}

function loadPic(obj,location,arr) {//加载图片
    $($(obj[location]).find("img")).attr("src",'http://iou-steward.oss-cn-hangzhou.aliyuncs.com/IOU/Fun/Template/'+arr[location]);
}



/**********************假数据*****************************/

//templateIds ="[\"0501728da4a14d9688173c65b0a78410\",\"192b78e8aca94f7c9985a213af2b8276\",\"3de897e480e34c628e37af0968b15b23\",\"a08373b119914c9f8c4ec592ef623c2c\",\"c7c90223bcc74cec9ed0f372f61b53db\",\"d40e362d681a41b28a3d33828f2f61ae\",\"e18c20552f4c4a1298ee8d6d2f481ad6\"]";
//illustrationArr = "[\"6ca7763028ac49328e7bf208d8bf798e\",\"8f98f8a716e542da89fa103e38c7aa24\",\"90178d997d0c4e438dc29fe7505ba9cc\",\"b139f7cbff9648578eb53c24a792f355\",\"cba6245b05eb4a118ffd0cb7859c4ffc\",\"fafb44c3d7ab4242a5386cd435291967\",\"ffeb8db95d7c4d5d8e203d891b8f2a8e\",\"6ca7763028ac49328e7bf208d8bf798e\",\"8f98f8a716e542da89fa103e38c7aa24\",\"90178d997d0c4e438dc29fe7505ba9cc\",\"b139f7cbff9648578eb53c24a792f355\",\"cba6245b05eb4a118ffd0cb7859c4ffc\",\"fafb44c3d7ab4242a5386cd435291967\",\"ffeb8db95d7c4d5d8e203d891b8f2a8e\"]";
//listimg("[\"0501728da4a14d9688173c65b0a78410\",\"192b78e8aca94f7c9985a213af2b8276\",\"3de897e480e34c628e37af0968b15b23\",\"a08373b119914c9f8c4ec592ef623c2c\",\"c7c90223bcc74cec9ed0f372f61b53db\",\"d40e362d681a41b28a3d33828f2f61ae\",\"e18c20552f4c4a1298ee8d6d2f481ad6\"]",illustrationArr);

/***********************假数据****************************/


/*******************************事件********************************/

$(".next").on("click",function(){//轮播插件下一张
	$(".swiper-button-next").trigger("click");
    if (showNum < templateIds.length){
	    var i = showNum+2;
	    console.log(i%5);
	    if (i%5 === 0){
            for(var a = 0; a < 5; a++){
                if(slideLocation < templateIds.length){
                    loadPic(str2Slide,slideLocation,arr);
                    slideLocation += 1;
                }else {
                    break;
                }
            }
        }
        showNum+=1;
    }
});

$(".back").on("click",function(){//返回上一页
    finishViewByApp();
});

$(".share_btn").on("click",function(){//分享
    showShareViewByApp();
});

$(".dinzhi").on("click",function(){//定制跳转下页

    var IOUID = $(this).attr("id");

    illustrationArr = {
        "illustrationArr":illustrationArr
    };

    localStorage.setItem("templateList",JSON.stringify(templateIds));

    localStorage.setItem("illustrationArr",JSON.stringify(illustrationArr)); //将获取的插画数据写入本地缓存
	window.location.href = "FunEdit.html?templateId="+IOUID;
});

$(".cancel").on("click",function(){
	$(".mask").hide();
});

