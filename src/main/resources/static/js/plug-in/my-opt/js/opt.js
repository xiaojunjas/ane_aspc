//选择插件
var opt = (function () {

    //自定义复选
    //参数从至右（列表的class,列表的选择区域class,全选的calss，全选的选择区域的class）
    function cusCheckbox(list,listbtn,cla,calbtn) {

        $(listbtn).click(function () {
            var cl=$(this).parents(list).attr("class");
            if(cl.indexOf("j-checked") == -1){
                $(this).parents(list).addClass("j-checked");
            }else{
                $(this).parents(list).removeClass("j-checked");
            }
            var thisType=true;
            for(var i=0;i<$(list).length;i++){
                if($(list).eq(i).attr("class").indexOf("j-checked") == -1){
                    thisType=false;
                    $(cla).removeClass("j-checked");
                }
            }
            if(thisType==true){
                $(cla).addClass("j-checked");
            }
        })

        $(calbtn).click(function () {
            var cl=$(cla).attr("class");
            if(cl.indexOf("j-checked") == -1){
                $(cla).addClass("j-checked");
                $(list).addClass("j-checked");
            }else{
                $(cla).removeClass("j-checked");
                $(list).removeClass("j-checked");
            }
        })
    }

    //自定义单选
    //参数从至右（列表的class,列表的选择区域class）
    function cusRadio(list,listbtn) {
        $(listbtn).click(function () {
            $(list).removeClass("j-checked");
            $(this).parents(list).addClass("j-checked");
        })
    }

    
    //复选
    //参数从至右（列表的class,全选的calss）
    function checkbox(list,cla) {
        $(list).click(function () {
            var thisType=true;
            for(var i=0;i<$(list).length;i++){
                if(!$(list).eq(i).find("input[type='checkbox']").prop("checked")){
                    thisType=false;
                    $(cla).find("input[type='checkbox']").prop("checked",false);
                }
            }
            if(thisType==true){
                $(cla).find("input[type='checkbox']").prop("checked",true);
            }
        })
        $(cla).click(function () {
            $(list).find("input[type='checkbox']").prop("checked",$(this).find("input[type='checkbox']").prop("checked"));
        })
    }
    
    return{
        cusCheckbox : cusCheckbox,
        cusRadio : cusRadio,
        checkbox : checkbox
    }
})()