$(function () {

    //验证
    //姓名
    $("#input-name").blur(function () {
        var reg=/^[^\s]{1,20}$/;
        if(reg.test($(this).val())){
            formPrompt.hide("#input-name");
        }else{
            formPrompt.show("#input-name");
        }
    })

    //邮箱
    $("#input-email").blur(function () {
        var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if(reg.test($(this).val()) || $(this).val() == ""){
            formPrompt.hide("#input-email");
        }else{
            formPrompt.show("#input-email");
        }
    })

    //居住地
    $("#input-address").blur(function () {
        if($(this).val().length >= 0 && $(this).val().length <= 30){
            formPrompt.hide("#input-address");
        }else{
            formPrompt.show("#input-address");
        }
    })

    //手机
    $("#input-phone").blur(function () {
        var reg=/^1[34578]\d{9}$/;
        if(reg.test($(this).val()) || $(this).val() == ""){
            formPrompt.hide("#input-phone");
        }else{
            formPrompt.show("#input-phone");
        }
    })

    //修改头像
    headSculpture("#input-photo",".j-photo");
    
    
    /*获取用户信息*/
    getUserByUserId();
    function getUserByUserId(){
    	var userId = $("#userId").val();
    	$.ajax({
    		type:"GET",
    		url:"/aspc/getUserByUserId",
    		data:{
    			userId:userId
    		},
    		success : function(data){
    			 $("#input-name").val(data.name);
    			 sexRs(data.sex);//给单选框赋值
				 $("#input-password").val(data.password);
				 $("#input-address").val(data.address);
				 $("#input-phone").val(data.phone);
				 $("#input-email").val(data.email);
    		},error:function(data){
    			alertLayerMessage("出现错误信息!");
    		}
    	});
      }
    
    /*保存*/
    $(".j-submit").click(function(){
    	var user = {};
    		user.id = $("#userId").val();					
    		user.name = $("#input-name").val();		
    		user.sex =$("#wrap input[name='sex']:checked").val();
    		user.password = $("#input-password").val();			
    		user.address = $("#input-address").val();		
    		user.phone =$("#input-phone").val();	
    		user.email = $("#input-email").val();	
    	$.ajax({
    		type:"GET",
    		url:"/aspc/editUserByUserId",
    		data:{
    			userInfo:JSON.stringify(user)
    		},
    		success : function(data){
    			layer.confirm('修改用户信息成功，是否返回列表！',function(){
                   window.location.href="/aspc/user"
                },function(){
                    //这里是取消的回掉函数
                }
            );
    		},error:function(data){
    			alertLayerMessage("出现错误信息!");
    		}
    	});
      
    });
    

})



//修改头像
function headSculpture(inputcla,imgcla) {
    $(inputcla).change(function(){
        var file=this.files[0];
        var reader=new FileReader();
        reader.readAsDataURL(file);
        //当选择的文件加载成功时
        reader.onload=function(){
            var url=reader.result;
            setImageURL(url);
        }
    })

    var image=$(imgcla);
    function setImageURL(url){
        image.attr("src",url);
    }
}

/*单选框赋值*/
function sexRs(txt){
	 var radios = document.getElementsByName("sex");
	 for(i=0;i<radios.length;i++){          //循环单选框
		    if(radios[i].value == txt)   //判断是否等于单选框的值
		    radios[i].checked = true;          //选中单选框
		  }
}

