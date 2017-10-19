$(function () {
	
	var current=1;
    var limit = 10;
    
	var params={'start':0,'limit':limit,'query':null};
	userList();
	
	/* 搜索框条件查询 回车*/
	$("#query").keydown(function(event) {  
		if(event.keyCode == 13){
			event.preventDefault();
			current=1;
			userList();
	    }
	});
	 
	/* 搜索框条件查询 点击按钮*/
	$("#but_query").click(function(){
			current=1;
			userList();
	});
	
	/* 参数*/
    function paramsing(){
    	var query = $("#query").val();
    	params['start']=current-1;
    	params['limit']=limit;
    	params['query']=query
    }
        
    /* 方法*/
    function userList(){
    	paramsing();
    	$("#userList").empty();
    	$.ajax({
    		type:"get",
    		url:"/aspc/roles",
    		data:params,
    		success : function(data){
    			$("#userTempl").tmpl(data).appendTo("#userList");
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
	    			userList();
	    		}
	    	});
		}else{
			$(".pagination").hide();
		}
    };
    
    
    /* 删除*/
    $("#userList").on("click", ".to-deleted", function() {
    	var userId = $(this).attr("userId");
    	layer.confirm('你正在删除用户信息，是否继续',function(){
        	$.ajax({
        		type:"get",
        		url:"/aspc/delUserByUserId",
        		data:{
        			userId:userId
        		},
        		success : function(data){
        			userList();
        		},error:function(data){
        			layer.alert("出现错误信息!");
        		}
        	});
        },function(){
            //这里是取消的回掉函数
        }
      );
   });
    
    /* 删除*/
   /* $("#userList").on("click", ".to-edit", function() {
    	 window.location.href="/aspc/user-edit?userId="+$(this).attr("userId");
    });*/

});