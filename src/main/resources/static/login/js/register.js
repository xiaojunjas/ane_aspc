$(function(){
	
	var nameReg =/^[A-Za-z0-9]{1,10}$/
	var pasReg =/^[A-Za-z0-9]{1,16}$/
	
	 $("#loginName").blur(function () {
		 if(nameReg.test($(this).val())==false){
	    	layer.alert("用户名只能为10位以内字母或数字!");
		 }
	  });
		
	 $("#password").blur(function () {
		 if(nameReg.test($(this).val())==false){
		    	layer.alert("密码只能为16位以内字母或数字!");
			 }
	  });
	 
	 /**
	  * 注册
	  */
	$("#register").click(function(){
		var loginName = $("#loginName").val();
		var password = $("#password").val();
		if(loginName =="" || loginName ==null||loginName=="输入ID或用户名登录"){
			layer.alert("请输入登录账号！", {title: "注册！"});
			return false;
		}
		if(password =="" ||password ==null ||password =="输入密码"){
			layer.alert("请输入登录密码！", {title: "注册！"});
			return false;
		}
		$.ajax({
			type:"post",
			url:"/aspc/createUser",
			data:{
				name:loginName,
				password:password
			},
			success : function(data){ 
	   			 layer.confirm('注册成功！确认登录！', {
	   				  btn: ['确定','取消'] //按钮
	   				}, function(){ 
	   					window.location.href="/aspc/login";
	   				}, function(){
	   				 
	   				});
			},error:function(data){
				layer.alert("注册！", {title: "注册出错！重新注册"});
			}
		});
	})
	
})