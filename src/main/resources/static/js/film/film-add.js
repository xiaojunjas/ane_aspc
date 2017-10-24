$(function () {
	
	$(".j-submit").click(function(){
		var film = {};
			film.name = $("#input-name").val();		
			film.director =$("#input-director").val();
			film.toStar =$("#input-toStar").val();
			film.releaseTime =CurentTime();
		$.ajax({
			type:"GET",
			url:"/aspc/filmMessage",
			data:{
				filmInfo:JSON.stringify(film)
			},
			success : function(data){
				layer.confirm('添加影院信息成功，是否返回列表！',function(){
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


function CurentTime(){ 
    var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   
    if(hh < 10)
        clock += "0";
       
    clock += hh + ":";
    if (mm < 10) clock += '0'; 
    clock += mm; 
    return(clock); 
} 