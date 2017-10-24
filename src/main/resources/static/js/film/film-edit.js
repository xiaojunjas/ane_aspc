$(function () {
	
	$(".j-submit").click(function(){
		var film = {};
			film.id = $("#filmId").val();	
			film.name = $("#input-name").val();		
			film.director =$("#input-director").val();
			film.toStar =$("#input-toStar").val();
			film.releaseTime =$("#input-releaseTime").val();
		$.ajax({
			type:"GET",
			url:"/aspc/filmMessage",
			data:{
				filmInfo:JSON.stringify(film)
			},
			success : function(data){
				layer.confirm('修改电影信息成功，是否返回列表！',function(){
	               window.location.href="/aspc/film"
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
