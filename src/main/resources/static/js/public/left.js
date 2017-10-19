$(function () {
	
	var account = $("#account").val();
	initLeft();
	
	function initLeft(){
		if(account != "admin"){
			$.ajax({
	       		type: "get",
	       		url: "/aspc/permissions",
	       		dataType: "json",
	       		async: false,
	       		success: function(data){
	       			console.log(data+"--");
	       			$(".sidebar-menu").html("");
	       			var str = '<li class="header">主导航</li>' + 
	       				'<li><a href="/panel"><i class="fa fa-home"></i><span>首页</span></a></li>';
	       			for (var i=0; i<data.length; i++) {
	       				if(str.indexOf(data[i].title) == -1){
	       					if(data[i].url != null && data[i].url != ''){
	       						str += '<li><a href="' + data[i].url + '"><i class="fa fa-cog"></i><span>' + data[i].title + '</span></a></li>';
	       					}else{
	       						str += '<li><a href="javascript:;"><i class="fa fa-cog"></i><span>' + data[i].title + '</span></a><ul class="treeview-menu">';
	       						for (var j=0; j<data.length; j++) {
	       							if(data[j].pid == data[i].id){
	       								str += '<li><a href="' + data[j].url + '"><i class="fa fa-circle-o"></i>' + data[j].title + '</a></li>';
	       							}
	       						}
	       						str += '</ul></li>';
	       					}
	       				}
	      				 
	       			}
	       			$(".sidebar-menu").html(str);
	       		}
			});
		}
	}

});