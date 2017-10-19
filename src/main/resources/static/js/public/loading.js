/**
 * 分页
 * current 当前页码
 * limit 每页数据量
 * count 总数据量
 * pages 总页数 parseInt(count/limit)+((count%limit)>0?1:0);
 */
/*function pageing(pages){
	if(pages<2) {
		$("#pagination").empty();
		return false;
	}
	$("#pagination").bootstrapPaginator({
		bootstrapMajorVersion: '3.0',
		currentPage: current,
		totalPages: pages,
		numberOfPages: limit,//此处分页栏 显示数量
		itemTexts: function (type, page, currentpage) {
            switch (type) {
	            case "first": return "首页";
	            case "prev" : return "上一页";
	            case "next" : return "下一页";
	            case "last" : return "尾页";
	            case "page" : return page;
            }
        },onPageClicked: function(event, originalEvent, type, page){
			if(current == page) return false;
			current = page;
			loading();
		}
	});
}

*//**
 * 列表加载
 * 必须条件  paramsing参数封装函数,LOADING_URL请求地址,params 参数对象,保存加载选择器不变
 * @returns
 *//*
function loading(){
	paramsing();
	$('#listContent').empty();
	$.ajax({
		type:"GET",
		url: LOADING_URL,
		dataType: "json",
		data:params,
		success: function(data){
			$("#listContentTemplate").tmpl(data).appendTo("#listContent");
			pageing(data.pages);
		},error:function(){
			layer.alert("请求异常！", {title: "请求异常"});
		}
	});
}*/


/**
 * 根据主键获取详细信息
 * @param id_ 主键编号
 * @param callback(data) 带参回调函数 data:查询返回结果集
 * @returns
 */
function geting(id_,callback){
	if(!id_){
		layer.alert("请求异常！", {title: "请求错误，缺少参数"});
		return false;
	}
	$.ajax({
		type:"GET",
		url: GETING_URL+'/'+id_,
		dataType: "json",
		success: function(data){
			if(typeof callback == 'function') callback(data);
		},error:function(){
			layer.alert("请求异常！", {title: "请求异常"});
		}
	});
}

function finding(fparams,callback){
	$.ajax({
		type:"GET",
		url: FINDING_URL,
		dataType: "json",
		data:fparams,
		success: function(data){
			if(typeof callback == 'function') callback(data);
		},error:function(){
			layer.alert("请求异常！", {title: "请求异常"});
		}
	});
}

function saveing(callback){
	var saveParams = saveParamsing();
//	var result = validate(saveParams);
//	if(!result) return false;
	$.ajax({
		type:"POST",
		url: SAVEING_URL,
		dataType: "json",
		data:saveParams,
		success: function(data){
			if(data.status==10000)
				(typeof callback == 'function')?callback():$.dialog({'title': '提示:','content': '操作成功！'});
			else if(data.message) 
				alertLayerMessage(data.message);
			else alertLayerMessage("操作成功");;
		},error:function(){
			alertLayerMessage("操作异常");
		}
	});
}

function deleteing(id_,callback){
	if(!id_){
		alertLayerMessage("请求错误，缺少参数");
		return false;
	}
	var deleteparams ={'_method':'DELETE','id':id_};
	$.ajax({
		type:"POST",
		url: DELETEING_URL,
		dataType: "json",
		data:deleteparams,
		success: function(data){
			if(data.status==10000){
				if(typeof callback == 'function') callback(data);
			}else alertLayerMessage(data.message);;
		},error:function(){
			alertLayerMessage("操作异常");;
		}
	});
}

/**
 * 列表加载加强
 * @param params_  请求参数
 * @param url_ 请求地址
 * @param selector_ 选择器
 * @param callback_ 回调函数
 * @param paramsing_ 参数封装函数
 * @returns
 */
var LOAD_={'current_':1,'limit_':10,'params_':{},'url_':'','selector_':'','callback_':'','paramsing_':''};
function loading_(paramsfun_){
	LOAD_.paramsing_();
	$(LOAD_.selector_).empty();
	$.ajax({
		type:"GET",
		url: LOAD_.url_,
		dataType: "json",
		data:LOAD_.params_,
		success: function(data){
			if(typeof LOAD_.callback_ == 'function') LOAD_.callback_(data);
			/*$("#listContentTemplate").tmpl(data).appendTo("#listContent");
			pageing(data.pages);*/
		},error:function(){
			alertLayerMessage("请求异常");
		}
	});
}

/**
 * 分页加强
 * current 当前页码
 * limit 每页数据量
 * count 总数据量
 * pages 总页数 parseInt(count/limit)+((count%limit)>0?1:0);
 */

/*function pageing_(pageSelector_,pages_){
	if(pages_<2) {
		$(pageSelector_).empty();
		return false;
	}
	$(pageSelector_).bootstrapPaginator({
		bootstrapMajorVersion: '3.0',
		currentPage: LOAD_.current_,
		totalPages: pages_,
		numberOfPages: LOAD_.limit_,//此处分页栏 显示数量
		itemTexts: function (type, page, currentpage) {
            switch (type) {
	            case "first": return "首页";
	            case "prev" : return "上一页";
	            case "next" : return "下一页";
	            case "last" : return "尾页";
	            case "page" : return page;
            }
        },onPageClicked: function(event, originalEvent, type, page){
			if(LOAD_.current_ == page) return false;
			LOAD_.current_ = page;
			loading_();
		}
	});
}
*/


