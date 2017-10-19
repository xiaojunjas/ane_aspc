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
	 * 跳转注册页面
	 */
	$("#register").click(function(){
		window.location.href="/aspc/register";
	});
	
	/**
	 * 登陆
	 */
	$("#login").click(function(){
		var loginName = $("#loginName").val();
		var password = $("#password").val();
		if(loginName =="" || loginName ==null||loginName=="输入ID或用户名登录"){
			layer.alert("登录账号不能为空！")
			return false;
		}
		if(password =="" ||password ==null ||password =="输入密码"){
			layer.alert("密码不能为空！")
			return false;
		}
		
		$.ajax({
			type:"get",
			url:"/aspc/loginUser",
			data:{
				name:loginName,
				password:password
			},
			success : function(data){ 
				console.log(data)
				if(data==1){
					window.location.href="/aspc/user";
				}else{
					layer.alert("账号或密码不正确！")
				}
			},error:function(data){
				layer.alert("登录出错！")
			}
		});
	})
	
})