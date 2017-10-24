$(function () {
	
	var current=1;
    var limit = 10;
    
	var params={'start':0,'limit':limit,'query':null};
	filmList();
	
	/* 搜索框条件查询 回车*/
	$("#query").keydown(function(event) {  
		if(event.keyCode == 13){
			event.preventDefault();
			current=1;
			filmList();
	    }
	});
	 
	/* 搜索框条件查询 点击按钮*/
	$("#but_query").click(function(){
			current=1;
			filmList();
	});
	
	/* 参数*/
    function paramsing(){
    	var query = $("#query").val();
    	params['start']=current-1;
    	params['limit']=limit;
    	params['query']=query
    }
        
    /* 方法*/
    function filmList(){
    	paramsing();
    	$("#filmList").empty();
    	$.ajax({
    		type:"get",
    		url:"/aspc/listFilm",
    		data:params,
    		success : function(data){
    			$("#filmTempl").tmpl(data).appendTo("#filmList");
    			pageing(data.count);
    		},error:function(data){
    			layer.alert("出现错误信息!");
    		}
    	});
      }

    /**
     * 分页事件
     */
    function pageing(count){
    	if(count<1) {
    		$(".pagination").empty();
    		return false;
    	}
    	var totalPage = parseInt(count/limit)+((count%limit)>0?1:0);
    	if(totalPage>1){
    		$(".pagination").show();
	    	$(".pagination").bootstrapPaginator({
	    		bootstrapMajorVersion: 3.0,
	    		currentPage: current,
	    		totalPages: totalPage,
	    		numberOfPages: limit,
	    		itemTexts: function (type, page, currentpage) {
	                switch (type) {
	    	            case "first": return "首页";
	    	            case "prev" : return "上一页";
	    	            case "next" : return "下一页";
	    	            case "last" : return "尾页";
	    	            case "page" : return page;
	                }
	            },onPageClicked: function(event, originalEvent, type, page){
	    			if(current == page){
	    				return false;
	    			}
	    			current = page;
	    			filmList();
	    		}
	    	});
		}else{
			$(".pagination").hide();
		}
    };
    
    /* j-movies-add  */
    $(".j-film-add").click(function(){
    	window.location.href="/aspc/film-add";
    });
    
    /* j-movies-edit  */
    $("#filmList").on("click",".j-film-edit",function(){
    	window.location.href="/aspc/film-edit?filmId="+$(this).attr("filmId");
    });
    
    /* j-movies-del  */
    $("#filmList").on("click",".j-film-del",function(){
    	var filmId = $(this).attr("filmId")
    	$.ajax({
    		type:"get",
    		url:"/aspc/deleFilm",
    		data:{
    			filmId:filmId
    		},
    		success : function(data){
    			filmList();
    		},error:function(data){
    			layer.alert("出现错误信息!");
    		}
    	});
      
    });

});
