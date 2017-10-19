$(function () {
    //（旧版）返回上一页,新版不要用此class
    $(".j-btn-cancel").click(function () {
        window.history.go(-1);
    });

    //（返回，取消）保存提示
    $(".j-btn-save-prompt").click(function () {
        layer.confirm('信息尚未保存，是否继续',{id : 1},function(index){
                layer.close(index);
                window.history.go(-1);
            }
        );
    });

    //（详情页）【预览页面】返回上一页
    $(".j-button-cancel").click(function () {
        window.history.go(-1);
    });

    //计算导航选中
    nav();

    $("#logout").click(function(){
        layer.confirm("确认退出？", {title: "退出系统"}, function(){window.location.href = "/admin/logout";});
    });

//    $("#panelitem").attr("href","http://qingdao.gclasscn.com/panel");

});

//计算导航选中
function nav() {
    var navHref;//面包屑的路径
    var status = true;
    if($(".breadcrumb").find("li").length > 2){
        navHref = $(".breadcrumb").find("li").eq(2).find("a").attr("href");
        status = true;
    }else{
        navHref = $(".breadcrumb").find("li").eq(1).find("a").attr("href");
        status = false;
    }
    navHref = navHref || "";
    // navHref = navHref.substring(navHref.lastIndexOf("/")+1);
    for (var i = 0;i < $("#sidebar-menu").children("li").length;i++){
        var thisNav = $("#sidebar-menu").children("li").eq(i);
        var href = "";
        if(status){
            for (var j = 0;j < thisNav.find("li").length;j++){
                var thisNavs = thisNav.find("li").eq(j);
                href = thisNavs.children("a").attr("href");
                href = href || "";
                if(href == navHref){
                    thisNavs.addClass("active").siblings().removeClass("active");
                    thisNavs.parents("li").addClass("active").siblings().removeClass("active");
                    return;
                }
            }
        }else{
            href = thisNav.children("a").attr("href");
            href = href || "";
            if(href == navHref){
                thisNav.addClass("active").siblings().removeClass("active").parents("li")
                    .addClass("active").siblings().removeClass("active");
                return;
            }
        }
    }
    //如果面包屑第三级比对不上所有的导航第三级，则退回一级比对
    navHref = $(".breadcrumb").find("li").eq(1).find("a").attr("href");
    // navHref = navHref.substring(navHref.lastIndexOf("/")+1);
    for (var i = 0;i < $("#sidebar-menu").children("li").length;i++){
        var thisNav = $("#sidebar-menu").children("li").eq(i);
        var href = "";
        href = thisNav.children("a").attr("href");
        href = href || "";
        if(href == navHref){
            thisNav.addClass("active").siblings().removeClass("active").parents("li")
                .addClass("active").siblings().removeClass("active");
            return;
        }
    }
}

/**
 * 格式化时间输出
 * dateStr: 时间字符串
 * format: 时间格式
 */
function format(dateStr, format) {
    if(isNaN(dateStr)){
    	dateStr = dateStr.replace(/-/g,"/");
    }
	var date = new Date(dateStr);
    var obj = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "H+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var field in obj){
        if (new RegExp("(" + field + ")").test(format)){
            format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ?
                (obj[field]) : (("00" + obj[field]).substr(("" + obj[field]).length)));
        }
    }
    return format;
}

function dateFormat(dateStr) {
	return format(dateStr, "yyyy-MM-dd");
}

//提示信息
function alertLayerMessage(message){
    layer.alert(message);
}


//提示信息返回url
function alertLayerMessageUrl(message,url){
    layer.alert(message,function(){
        window.location.href =url;
    });
}

function talert(d){
    layer.alert(d);
}

function falert(s,f) {
    layer.alert(s,function(index){
        layer.close(index);
        f();
    });
}

//判断是否是admin,是否隐藏学校下拉
var account=$("#account").val();
account = account || "";
if(account!="admin"){
    $(".j_school").parent().hide();
}

//内容过长时进行截取
function ContentSub(mess,val){
    var s = mess;
    if(mess==null||mess == ""){
        s= "";
    }else if(mess.length>val){
        s=mess.substring(0,val)+"...";
    }
    return s;
}