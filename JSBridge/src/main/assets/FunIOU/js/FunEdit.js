
/*
* 进入页面先由APP调用方法判断由模板进入还是由详情进入，方法为judgeWayByApp(path,data)
* 加载时获取本地缓存
* 若editIfo有缓存，则自动填入edit信息
* 若提醒为true，则判断事件是否为空
*
*/
var editIfo; //定义编辑信息
var entrance; //定义入口
var defaultScheduleReturnDate;//定义默认提醒时间
var illustrationArr;//定义插画

/*************************页面加载**************************/


$(function () {
    editIfo = JSON.parse(localStorage.getItem("editIfo"));
    console.log(editIfo);
    if (editIfo){
        fillEditIfo(editIfo);
        $("#scheduleReturnDate").val(editIfo.scheduleReturnDate);//提醒归还日期
    }

    $(".switch").removeClass("on");
    $(".revert").hide();
});



/*************************页面方法******************************/

function fillEditIfo(data) {
    $("#borrowerName").val(data.borrowerName); //我是谁
    $("#loanerName").val(data.loanerName);//问谁借
    $("#thinesName").val(data.thinesName);//借什么
    $("#todo").val(data.todo);//做什么
}



function next(){ //跳转下一页
    var borrowerName = $("#borrowerName").val(); //我是谁
    var loanerName = $("#loanerName").val();//问谁借
    var thinesName = $("#thinesName").val();//借什么
    if(entrance === "detail"){
        if (defaultScheduleReturnDate !== ""){
            var scheduleReturnDate = defaultScheduleReturnDate;//提醒归还日期
            localStorage.setItem("illustrationArr",JSON.stringify(illustrationArr)); //将获取的插画数据写入本地缓存
        }else {
            var scheduleReturnDate ="";
        }
    }else {
        var scheduleReturnDate = $("#scheduleReturnDate").val();//提醒归还日期
    }
    var todo = $("#todo").val();//做什么
    if(borrowerName=="" || loanerName=="" || thinesName=="" || todo==""){
        alert("借条选项不能为空");
        return false;
    }

    if($(".switch").attr("display") != "none"){
        if($(".switch").hasClass("on")){
            if(scheduleReturnDate == ""){
                alert("提醒日期不能为空");
                return false;
            }
        }else {
            scheduleReturnDate = "";
        }
    }

    var templateId = window.location.href;
    var index=templateId.lastIndexOf("=");
    var index2=templateId.lastIndexOf("/");
    var nexturl = templateId.substring(0,index2+1);
    templateId=templateId.substring(index+1,templateId.length);

    editIfo = {
        "borrowerName":borrowerName,
        "loanerName":loanerName,
        "thinesName":thinesName,
        "todo":todo,
        "scheduleReturnDate":scheduleReturnDate,
        "templateId":templateId
    };
    localStorage.setItem("editIfo",JSON.stringify(editIfo));//注入缓存
    if(entrance == "detail"){
        var nextLink = templateId+"&borrowerName="+borrowerName+"&loanerName="+loanerName+"&thinesName="+thinesName+"&todo="+todo;
    }else {
        var nextLink = templateId+"&borrowerName="+borrowerName+"&loanerName="+loanerName+"&thinesName="+thinesName+"&todo="+todo+"&scheduleReturnDate="+scheduleReturnDate;
    }
    window.location.href = "TemplateImg.html?"+nextLink;
}

function backDetailByApp() {//返回APP详情
    window.WebViewJavascriptBridge.callHandler('backDetailByApp');
}


/*******************点击事件*********************/

$(".switch").on("click",function(){
    var t = $(this);
    if(t.hasClass("on")){
        t.removeClass("on");
        $(".revert").hide();
    }else{
        t.addClass("on");
        $(".revert").show();
    }
});

$(".back").on("click",function () {
   if(entrance == "detail"){
       backDetailByApp();
   } else {
       history.go(-1);
   }
});


/**********************APP方法****************************/

function shareFunIllustrationIdsByApp(illustrationIds){//获取插画
    illustrationArr = illustrationIds;
}

function judgeWayByApp(path,data) { //判断当前页面从编辑进入还是详情进入,传递参数为 "来源","json"
     console.log(path)
     console.log(data)
    if (path === "detail"){ //由详情进入
        $(".switch_fram").hide();
        $(".revert").hide();
        data = $.parseJSON(data);
        fillEditIfo(data);
        entrance = "detail";
        defaultScheduleReturnDate = data.scheduleReturnDate;
    }
}
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