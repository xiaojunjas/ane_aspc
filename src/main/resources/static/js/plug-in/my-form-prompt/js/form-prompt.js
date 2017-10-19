//表单提示插件
var formPrompt = (function () {
    var thistime;
    //初始化
    function init(cla) {
        $(cla).blur(function () {
            if($(this).val() == ""){
                $(this).parent("div[form-prompt]").attr("form-prompt","true");
            }else{
                $(this).parent("div[form-prompt]").attr("form-prompt","false");
            }
        })
        // thistime = window.setTimeout(function () {
        //     $(cla).parent("div[form-prompt]").attr("form-prompt","false");
        // },6000)
    }

    //修改提示文字
    function text(cla,text) {
        if(text){
            $(cla).parent("div[form-prompt]").attr("form-prompt-text",text);
        }
    }

    //显示
    function show(cla) {
        if(!cla){
            $("div[form-prompt]").attr("form-prompt","true");
        }
        $(cla).parent("div[form-prompt]").attr("form-prompt","true");
        // thistime = window.setTimeout(function () {
        //     $(cla).parent("div[form-prompt]").attr("form-prompt","false");
        // },6000)
    }

    //隐藏
    function hide(cla) {
        if(!cla){
            $("div[form-prompt]").attr("form-prompt","false");
        }
        $(cla).parent("div[form-prompt]").attr("form-prompt","false");
    }

    return{
        init : init,
        text : text,
        show : show,
        hide : hide
    }
})()
