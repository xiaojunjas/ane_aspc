$(function () {
	
	$(".j-submit").click(function(){
		var movies = {};
			movies.id = $("#moviesId").val(); 
			movies.name = $("#input-name").val();		
			movies.address =$("#input-address").val();
		$.ajax({
    		type:"GET",
    		url:"/aspc/moviesMessage",
    		data:{
    			moviesInfo:JSON.stringify(movies)
    		},
    		success : function(data){
    			layer.confirm('修改影院信息成功，是否返回列表！',function(){
                   window.location.href="/aspc/movies"
                },function(){
                    //这里是取消的回掉函数
                }
            );
    		},error:function(data){
    			alertLayerMessage("出现错误信息!");
    		}
    	});
	})
	
});
