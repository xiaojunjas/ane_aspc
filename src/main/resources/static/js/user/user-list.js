$(function () {
	
	var current=1;
    var limit = 10;
    //初始化加载权限模态框
    initPermissionsTree();
    
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
    		url:"/aspc/listUser",
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
    $("#userList").on("click", ".to-edit", function() {
    	 window.location.href="/aspc/user-edit?userId="+$(this).attr("userId");
    });
    
    $("#userList").on("click",".to-permission",function(){
    	var userId_ = $(this).attr("userId");
    	$("#user-id").val(userId_);
//    	var schoolid = 1;
    	 $('#Modal-perms-set').find('.modal-title').text('用户权限设置');
         $('#Modal-perms-set').modal('show');
         defaultPermissionTree({'type':2,'refId':userId_});
    });

});
var LOADING_URL = '/aspc/permissions/users',GETING_URL='', SAVEING_URL='/aspc/rolePermissions',DELETEING_URL='',FINDING_URL='/aspc/rolePermissions/refid';
var params={};

function defaultPermissionTree(fpms){
	var $permsTree = $("#treeview2").jstree();
	$permsTree.uncheck_all();
	finding(fpms,function(data){
		var hasCheckNodeIds = [];
		if(data.attrs.permIds){
			$.each(data.attrs.permIds,function(i,k){
				if(!$permsTree.is_parent(k)) hasCheckNodeIds.push(k);
			});
			$permsTree.check_node(hasCheckNodeIds);
		}
	});
}

function saveParamsing(){
	var nodeIds = [];
	var nodes = $('#treeview2').jstree().get_checked(true);
	$.each(nodes,function(i,item){
		nodeIds.push(item.id);
		if(nodeIds.indexOf(item.parent)==-1&&item.parent!='#') nodeIds.push(item.parent);
	});
	var userId = $("#user-id").val();
//	var schoolid = 1;
	var saveParams={'permIds':nodeIds,'type':2,'refId':userId};
	return saveParams;
}

$("#do-save").on("click",function(){
	saveing(function(data){ 
		$('#Modal-perms-set').modal('hide');
		alertLayerMessage("保存成功！");
    });
});

function initPermissionsTree(){
	var organizationTree = $('#treeview2').jstree({
		'core' : {
			'multiple' : true,
			'data' : {
				'url' : '/aspc/permissions/pid',
				'type':'get',
				'dataType':'json',
				'async' : false,
				'data' : function (data) {
					return { 'pid' : data.id=='#'?null:data.id};
				},
				'success':function(response){ }
			},
		},
        'types': {
            "default": { "icon": "fa fa-folder-open" },
            "demo": { "icon": "fa fa-flash" }
        },
        "plugins": ["search","checkbox" , "wholerow", "types"]
    }).bind("select_node.jstree",function(e,data){ //点击事件
    	//$("#treeview2").jstree('open_all', data.node);
    }).bind("open_node.jstree",function(e,data){
    	//$("#treeview2").jstree('open_all', data.node);
    }).bind("loaded.jstree", function (e, data){
    	$("#treeview2").jstree("load_all");
    });
}