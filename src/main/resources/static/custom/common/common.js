

		//一定要在被加载页面中使用在该方法$.ace_ajax_finish([callback]) 或 引入ace_ajax_finish.js
	/*	$.extend({
				ace_ajax_finish:function(scripts,callback) {
					var localScripts =  [null, null] ;
						if(typeof callback === 'function') {
								$('#auto_page_content').ace_ajax('loadScripts', localScripts,callback) 	
							} else {
								$('#auto_page_content').ace_ajax('loadScripts', localScripts,function(){}) 
							}
				}
			}) */
		$.extend({
			ace_ajax_finish:function(scripts,callback) {
				var length = arguments.length ;
				if(length == 0) {
					$('#auto_page_content').ace_ajax('loadScripts', [null,null],function(){}) 
				} else if(length == 1) {
					if(typeof arguments[0] ==='function') {
						$('#auto_page_content').ace_ajax('loadScripts', [null,null],arguments[0]) ;
					} else {
						$('#auto_page_content').ace_ajax('loadScripts', arguments[0],function(){}) ;
					}		
				} else {	
					$('#auto_page_content').ace_ajax('loadScripts', scripts,callback) ; 
				}	
			}
		})

		/**
		 * ace菜单加载页面
		 */
		$('#auto_page_content').ace_ajax({
		   content_url : function(hash) {
				var temp = hash.split("?") ;
				if(temp[1] == null || typeof temp[1] == 'undefined') {
					hash=hash+"?times="+ Date.parse(new Date());
				}else{
					hash=hash+"&times="+ Date.parse(new Date());
				}
				
				return hash ;
			},
			 //default_url: '',	//页面打开时，首次加载的页面
			update_title:function(hash, url, some_text) {
			   
				return false ;
				
			 },		//不更新标题
			max_load_wait:1	//页面加载最多等待2秒中。
		});
		
		/**
		 * 显示服务器响应的内容
		 */
	function showMsg4model(json,isShowSuccess) {
		if(json == null) {
			showErrorMsg('操作失败！') ;
		} else {
			if(json.status == 0) {
				showErrorMsg(json.message)
				return false ;
			} else {
				if(isShowSuccess) {	//是否显示成功提示
					showSuccessMsg(json.message)
				}
			}
		}	
	}



//全局js函数
//$(function(){	
	/**
	 * 封装jquery的load，用于加载局部页面
	 * url:加载的路径，必选
	 * data:传送的数据. 类型：js对象.
	 * isHaveDelay: 是否需要时延，当页面中打开模态框时，进行toMain，滚动条会消失，因此时延，即：isHaveDelay设置成true
	 *
	 * 调用eg：toMain(url), toMain(url,{key,value}), toMain(url,boolean), toMain(url,{key,value}, boolean)
	 *
	 */
	window.toMain = function(url,data, isHaveDelay)  {
		var json = "" ;
		var delayFlag = false ;		//时延标记
		if(arguments.length ==2) {
			if((typeof arguments[1]) == 'object') {	//js对象
                var json = "?"+$.param(data) ;
            } else {
				delayFlag = arguments[1] ;
			}
		} else if(arguments.length ==3) {
            if((typeof arguments[1]) == 'object') {	//js对象
                var json = "?"+$.param(data) ;
            }
            delayFlag = arguments[2] ;
		}
		if(delayFlag) {
			setTimeout(function(){
                $("#auto_page_content").ace_ajax('loadUrl', url+json) ;
			} ,500) ;
		} else {
            $("#auto_page_content").ace_ajax('loadUrl', url+json) ;
		}
	}
	
    /**
     * 显示对话窗口
     */
    bootbox.setDefaults({
        locale: "zh_CN"
    });
	window.customShowDialog = function(msg, fnt) {
        bootbox.dialog({
            message: "<span class='bigger-110'>" + msg + "</span>",
            buttons: {
                "success": {
                    "label": "<i class='icon-ok'></i>确定",
                    "className": "btn-sm btn-success",
                    "callback": function() {
                        if (typeof fnt == 'function') {
                            fnt();
                        }
                    }
                }
            }
        });
    }
	

    //填充表单数据
	/**
	 * form 表单的jquery对象
	 * data 数据模型，data中key要对应输入框的prefix+name
	 * perfix input中的name前缀
	 * 
	 * 用法：fillform($('.form'),data)
	 * 
	 */
    window.fillform = function(form, data, prefix) {
        $.each(data, function(key, value) {
            if (prefix) {
                key = prefix + "\." + key;  
            }
            var $ctrl = $('[name=' + key + ']', form);
            switch ($ctrl.attr("type")) {
                case "text" :
                case "hidden":
                    $ctrl.val(value);
                    break;
                case "radio" :
                case "checkbox":
                    $ctrl.each(function() {
                        if ($(this).attr("value") == value) {
                            $(this).prop("checked", true);
                        } else {
                            $(this).prop("checked", false) ;
						}
                    });
                    break;             	
                default:
                	if($ctrl.is('select')) {
                		if($ctrl.prop("multiple")) {	//下拉多选
                            $ctrl.children('option').each(function(){
								if(value instanceof Array) {
									for(var i=0,length = value.length;i<length;i++) {
										if($(this).val() == value[i]) $(this).prop("selected",true) ;
									}
								} else {
									if($(this).val() == value) $(this).prop("selected",true) ;
								}
                            })
						}else {
                            $ctrl.children('option').each(function(){
                                if($(this).val() == value) {
                                    $(this).prop("selected",true)
                                }
                            })
						}
                	} else {
                		$ctrl.val(value);
                	}             
            }
        });
    }
	

    /**
     * 增强jquery的serializeArray
     * 
     * 需要被过滤的form表单的输入框的name
     * 
     * 用法：$('.form').serializeObject() 不过滤，全部序列化
     * 	   $('.form').serializeObject(['password','age']) 过滤password,age的输入框的值
     * 
     * @return js对象
     */
    $.fn.serializeObject = function(filters) {
        var obj = {};
        var filterInput = filters || [] ;	//过滤数组
        var formArr = this.serializeArray();
        $.each(formArr, function() {	//遍历表单序列化之后的数组
        	if(filterInput.indexOf(this.name) == -1) {
        		if (obj[this.name]) {	//判断obj中是否存在这个name为key的value，
        			if (!obj[this.name].push) {	//判断是否是数组	
        				obj[this.name] = [obj[this.name]];	//存在这个value时，就变成数组，并保存原来的值
        			}
        			obj[this.name].push(this.value || '');	//往数组中添加值
        		} else {
        			obj[this.name] = this.value || '';	//不存在这个value时，就直接往obj中添加key和value
        		}
        	}
        });
        return obj;
    };
    
    /**
     * 写好之后jquery已经有该实现了，建议用jquery的。jQuery.param(json)
     * 
     * 把js对象或数组转换成key,value的数据
     * 例如：name=aaa&age=111....
     */
    window.json2kv = function(json, prefix) {
    	var str = "";
    	var isArray = json instanceof Array; // 判断是否数组，作用在遍历出数据，字符串链接时是否需要"[]"和prefix是否需要\.
    	if (isArray) {
    		prefix = prefix == undefined ? "" : prefix; // 前缀
    	} else {
    		prefix = prefix == undefined ? "" : prefix + "\."; // 前缀
    	}
    	$.each(json, function(indexOrName, value) { // 遍历可能是数组或对象
    		if (value instanceof Array) { // 数组
    			$.each(value, function(index, val) { // 数组里面还是数组，继续遍历
    				if ((val instanceof Array) || (val instanceof Object)) { // 当数组内还是数组或对象时，递归调用
    					str = str + json2kv(val, prefix + indexOrName + '[' + index+ ']');
    				} else { // 普通值时，
    					if (isArray) {
    						str = str + prefix + "[" + indexOrName + "][" + index + "]=" + val + "&";
    					} else {
    						str = str + prefix + indexOrName + "[" + index + "]=" + val + "&";
    					}
    				}
    			}) // each
    		} else if (value instanceof Object) { // js对象
    			if (isArray) {
    				str = str + json2kv(value, prefix + "[" + indexOrName + "]");
    			} else {
    				str = str + json2kv(value, prefix + indexOrName);
    			}
    		} else {	//普通值
    			if (isArray) { // 数组时的情况
    				str = str + prefix + "[" + indexOrName + "]=" + value + "&";
    			} else {
    				str = str + prefix + indexOrName + "=" + value + "&";
    			}
    		}
    	})
    	return str;
    }
    
    
	// 遮罩层start
    window.showLoading = function(text) {
	    	$('.main-content').jqLoading({
	    		height: 80, 
	    		width: 240, 
	    		text: text || "正在加载中，请稍后....",
	    		backgroundImage: '/static/images/common/loading.gif',
	    		type:0	
	    	}) ;
	    }
	    
    window.hideLoading = function() {
	    	//$.fn.jqLoading("destroy");
	    	$('.main-content').jqLoading('destroy') ;
	    }
 		//遮罩层end
    
    /** 
     * 自定义提示消息方法  
     * 	title：可选. 标题。 
     *  content: 可选. 详情内容
     *  showtime: 可选. 消息显示时长. 默认 2秒.
     *  position: 可选. 显示位置.默认右上角.  可选值.true.中间显示
     *
     */
    //提示操作成功的方法
    window.showSuccessMsg = function(title,content,showtime,position) {
    	$.gritter.add({
			title: title || '操作成功！',
			text: content || '',
			time: showtime || 2300,
			image: '/static/images/common/gritter/success.png',
			class_name: 'gritter-success gritter-light' + (position ? ' gritter-center' : '')
		});
    	return false ;
    }
    //提示常规操作的方法
    window.showInfoMsg = function(title,content,showtime,position) {
    	$.gritter.add({
			title: title || '操作提示！',
			text: content || '',
			time: showtime || 2300,
			image: '/static/images/common/gritter/info.png',
			class_name: 'gritter-info gritter-light' + (position ? ' gritter-center' : '')
		});
    	return false ;
    }
    //提示操作警告的方法
    window.showWarnMsg = function(title,content,showtime,position) {
    	$.gritter.add({
			title: title || '操作警告！',
			text: content || '',
			time: showtime || 2300,
			image: '/static/images/common/gritter/warn.png',
			class_name: 'gritter-warning gritter-light' + (position ? ' gritter-center' : '')
		});
    	return false ;
    }
    //提示操作错误的方法
    window.showErrorMsg = function(title,content,showtime,position) {
    	$.gritter.add({
			title: title || '操作错误！',
			text: content || '',
			time: showtime || 2300,
			image: '/static/images/common/gritter/error.png',
			class_name: 'gritter-error gritter-light' + (position ? ' gritter-center' : '')
		});
    	return false ;
    }
    // 提示消息 end

    
    /**
     * versionFlag: boolean类型，是否要返回浏览器的版本
     */
     window.judgeBrower = function(versionFlag)
     {
        //注意关键字大小写
        var ua_str = navigator.userAgent.toLowerCase(), ie_Tridents, trident, match_str, ie_version, browser_Type;

        //判断IE 浏览器,或edge浏览器
        if("ActiveXObject" in self){
            // ie_version :  指示IE 的版本.
            ie_version = (match_str = ua_str.match(/msie ([\d.]+)/)) ? match_str[1] :
                  (match_str = ua_str.match(/rv:([\d.]+)/)) ? match_str[1] : 0;

            // ie内核
            ie_Tridents = {"trident/7.0": 11, "trident/6.0": 10, "trident/5.0": 9, "trident/4.0": 8};
            //匹配 ie8, ie11, edge
            trident = (match_str = ua_str.match(/(trident\/[\d.]+|edge\/[\d.]+)/)) ?match_str[1] : undefined;
            browser_Type = (ie_Tridents[trident] || ie_version) > 0 ? "ie" : "";
        }else{
            //判断 windows edge 浏览器
            // match_str[1]: 返回浏览器及版本号,如: "edge/13.10586"
            // match_str[1]: 返回版本号,如: "edge" 
            browser_Type = (match_str = ua_str.match(/edge\/([\d.]+)/)) ? "edge" :
                        //判断firefox 浏览器
                          (match_str = ua_str.match(/firefox\/([\d.]+)/)) ? "firefox" : 
                        //判断chrome 浏览器
                          (match_str = ua_str.match(/chrome\/([\d.]+)/)) ? "chrome" : 
                        //判断opera 浏览器
                          (match_str = ua_str.match(/opera.([\d.]+)/)) ? "opera" : 
                        //判断safari 浏览器
                          (match_str = ua_str.match(/version\/([\d.]+).*safari/)) ? "safari" : undefined;
        }   
        //返回浏览器类型和版本号
        var verNum, verStr;

        verNum = trident && ie_Tridents[trident] ? ie_Tridents[trident] : match_str[1];

        verStr = versionFlag ? browser_Type+"/"+verNum : browser_Type;
        return verStr;
     }


//})	//$(jquery())	
	
