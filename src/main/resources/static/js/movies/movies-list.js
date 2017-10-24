$(function () {
	
	var current=1;
    var limit = 10;
    
	var params={'start':0,'limit':limit,'query':null};
	moviesList();
	
	/* 搜索框条件查询 回车*/
	$("#query").keydown(function(event) {  
		if(event.keyCode == 13){
			event.preventDefault();
			current=1;
			moviesList();
	    }
	});
	 
	/* 搜索框条件查询 点击按钮*/
	$("#but_query").click(function(){
			current=1;
			moviesList();
	});
	
	/* 参数*/
    function paramsing(){
    	var query = $("#query").val();
    	params['start']=current-1;
    	params['limit']=limit;
    	params['query']=query
    }
        
    /* 方法*/
    function moviesList(){
    	paramsing();
    	$("#moviesList").empty();
    	$.ajax({
    		type:"get",
    		url:"/aspc/listMovies",
    		data:params,
    		success : function(data){
    			$("#moviesTempl").tmpl(data).appendTo("#moviesList");
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
	    			moviesList();
	    		}
	    	});
		}else{
			$(".pagination").hide();
		}
    };
    
    /* j-movies-add  */
    $(".j-movies-add").click(function(){
    	window.location.href="/aspc/movies-add";
    });
    
    /* j-movies-edit  */
    $("#moviesList").on("click",".j-movies-edit",function(){
    	window.location.href="/aspc/movies-edit?moviesId="+$(this).attr("moviesId");
    });
    
    /* j-movies-del  */
    $("#moviesList").on("click",".j-movies-del",function(){
    	var moviesId = $(this).attr("moviesId")
    	$.ajax({
    		type:"get",
    		url:"/aspc/deleMovies",
    		data:{
    			moviesId:moviesId
    		},
    		success : function(data){
    			moviesList();
    		},error:function(data){
    			layer.alert("出现错误信息!");
    		}
    	});
    });
    
    /* 影院信息*/
    $("#moviesList").on("click", ".j-movies-film", function() {
		$("#j-table-2").find(".j-checked").removeClass("j-checked");
        confirmUserStyle(".j-che-2",".j-btn-2");
		$("#film-movies").modal("show");
		pageNum = 1;
		filmMoviesList($(this).attr("moviesId"));//对比存在用户
		$("#thisMoviesId").attr("thisMoviesId",$(this).attr("moviesId"));
		filmList();//用户列表
    });
    
   //样式切换
    $("#filmList").on("click", ".j-btn-2", function() {
    	 confirmUserStyle(".j-che-2",".j-btn-2");
	});
    
    
    //保存 or 移除
    $("#film-movies-add").click(function(){
    	var moivesIds1 = new Array();//页面一共选择多少教师
    	var moivesIds2 = new Array();//已存在组织教师
    	for(var i=0;i<$("#filmList").find(".j-checked").length;i++){
    		moivesIds1.push($("#filmList").find(".j-checked").eq(i).attr("film-id"));
    		moivesIds2.push($("#filmList").find(".old_id").eq(i).attr("film-id")); 
		}
    	
    	 delteaIds = new Array();//删除
     	$("#filmList .old_id").each(function(k,v){
     		if($(v).attr("class").indexOf("j-checked")==-1){
     			delteaIds.push($(v).attr("film-id"));
     		}
     	});
     	
    	//临时数组存放 --去除重复
    	var tempArray1 = [];//临时数组1
    	var tempArray2 = [];//临时数组2

    	for(var i=0;i<moivesIds2.length;i++){
    	    tempArray1[moivesIds2[i]]=true;//将数array2 中的元素值作为tempArray1 中的键，值为true；
    	}
    	for(var i=0;i<moivesIds1.length;i++){
    	    if(!tempArray1[moivesIds1[i]]){
    	        tempArray2.push(moivesIds1[i]);//过滤array1 中与array2 相同的元素；
    	    }
    	}
		$.ajax({
			type: "POST",
			url: "/aspc/filmMovies",
			data: {
				filmIds: tempArray2,
				delteaIds:delteaIds,
				moviesId: $("#thisMoviesId").attr("thisMoviesId")
			},
			success: function(teachers){
				$("#film-movies").modal("hide");
				moviesList();
			}
		})
    });

});

//那些电影存在这个电影院中
function filmMoviesList(moviesId){
	$.ajax({
		type: "GET",
		url: "/aspc/findFilmMoviesByMoviesId",
		data: {
			moviesId: moviesId
		},
		success: function(date){
			var filmIds="";
	            for(var i=0;i<date.length;i++){
	            	filmIds +=","+date[i].filmId;
	            }
	            $("#exist-filmId").attr("filmIds",filmIds);
		}
	})
}


var size1 = 10;
var pageNum1 = 1;
var paramsf={'start':0,'limit':size1,'query':null};
/* 参数*/
function paramsingf(){
	var query = $("#query").val();
	paramsf['start']=pageNum1-1;
	paramsf['limit']=size1;
	paramsf['query']=query
}
    
/* 方法*/
function filmList(){
	paramsingf();
	$("#filmList").empty();
	$.ajax({
		type:"get",
		url:"/aspc/listFilm",
		data:paramsf,
		success : function(data){
			
			$("#filmTempl").tmpl(data).appendTo("#filmList");
			
			var filmIdStr = $("#exist-filmId").attr("filmIds");
			var filmIds = filmIdStr.split(",");
			$("#filmList tr").each(function(k,v){
				var selectedTeaId = $(v).attr("film-id");
				var exist = $.inArray(selectedTeaId, filmIds);
				if(exist!=-1){
					$(v).addClass("j-checked old_id");//old_id 区分教师
					$(v).find(".j-btn-2").removeClass("btn-info").addClass("btn-primary").text("已选");
				}
			});
			opt.cusCheckbox(".j-che-2",".j-btn-2"); //声明用户多选'
			
			pagination1(data.count);
		},error:function(data){
			layer.alert("出现错误信息!");
		}
	});
  }

/**
 * 分页事件
 */
function pagination1(count){
	if(count<1) {
		$("#filmPage").empty();
		return false;
	}
	var totalPage1 = parseInt(count/size1)+((count%size1)>0?1:0);
	if(totalPage1>1){ 
		$("#filmPage").show();
		$("#filmPage").bootstrapPaginator({
    		bootstrapMajorVersion: 3.0,
    		currentPage: pageNum1,
    		totalPages: totalPage1,
    		numberOfPages: 10,
    		itemTexts: function (type1, page1, currentpage1) {
                switch (type1) {
    	            case "first": return "首页";
    	            case "prev" : return "上一页";
    	            case "next" : return "下一页";
    	            case "last" : return "尾页";
    	            case "page" : return page1;
                }
            },
            onPageClicked: function(event, originalEvent, type1, current1){
    			if(pageNum1 == current1){
    				return false;
    			}
    			pageNum1 = current1;
    			filmList();
    		}
    	});
	}else{
		$("#filmPage").hide();
	}
};


//选择用户样式重置
function confirmUserStyle(list,listbtn) {
    for(var i = 0; i < $(list).length;i++){
        if($(list).eq(i).attr("class").indexOf("j-checked") != -1){
            $(list).eq(i).find(listbtn).removeClass("btn-info").addClass("btn-primary").text("已选");
        }else{
            $(list).eq(i).find(listbtn).removeClass("btn-primary").addClass("btn-info").text("可选");
        }
    }
}
