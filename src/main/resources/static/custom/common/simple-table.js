/**
 * 简单表格的crud封装类
 */

/**
 * 提交地址
 */
var actionUrl;
/**
 * 列表数据
 */
var jsonData;
/**
 * 当前页面地址
 */
var originUrl;

/** 简单表格操作实体 * */
function SimpleTable() {

	/**
	 * 初始化操作 ,url：提交地址 ,data：当前列表数据, currentUrl:当前页面地址, paramCount:需要显示的字段数量
	 */
	this.init = function(url, data, currentUrl, paramCount) {
		/** 全局变量赋值 * */
		jsonData = data;
		actionUrl = url;
		originUrl = currentUrl;

		/**
		 * 列表字段参数
		 */
		var aoColums = [];
		aoColums.push({
			"bSortable" : false
		});
		var i;
		for (i = 0; i < paramCount; i++) {
			aoColums.push(null);
		}
		aoColums.push({
			"bSortable" : false
		});

		// 表格初始化
		var oTable1 = $('#sample-table').dataTable({
			bAutoWidth : false,
			"aoColumns" : aoColums,
			"aaSorting" : [],
		});

		// 绑定表格第一个复选框
		$(document).on(
				'click',
				'th input:checkbox',
				function() {

					var that = this;
					$(this).closest('table').find(
							'tr > td:first-child input:checkbox').each(
							function() {
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});
				});

		/** 绑定工具提示 * */
		$('[data-rel="tooltip"]').tooltip({
			placement : tooltip_placement
		});

		// spinner
		$('#spinner3').ace_spinner({
			value : 1,
			min : 1,
			max : 10,
			step : 1,
			on_sides : true,
			icon_up : 'ace-icon fa fa-plus smaller-75',
			icon_down : 'ace-icon fa fa-minus smaller-75',
			btn_up_class : 'btn-success',
			btn_down_class : 'btn-danger'
		});

	},
	/**
	 * 新建操作 createBtnId:新建按钮id btnId:模态框保存按钮id
	 * data:需要提交的参数名称和字段类型，格式为{name:type, "name":"input"}
	 */
	this.addSaveMethod = function(createBtnId, btnId, data) {

		/**
		 * 绑定新建按钮
		 */
		$("button[id='" + createBtnId + "']").click(function() {
			$("button[id='" + btnId + "']").attr("name", "save");
		});

		/** 绑定保存按钮 * */
		$("button[id='" + btnId + "']").click(function() {
			var jsonParams = {};
			for ( var name in data) {
				var value = $(data[name] + "[name='" + name + "']").val();
				jsonParams[name] = value;
			}

			var btnType = $(this).attr("name");

			if (btnType == "save") {
				ajaxAction(actionUrl, "POST", jsonParams, originUrl); // 新建提交
			} else {
				ajaxAction(actionUrl, "PUT", jsonParams, originUrl); // 修改提交
			}

		});
	},
	/**
	 * 删除操作 btnId:列表删除按钮id cbName:复选框名称 allDelBtnId:删除按钮id
	 */
	this.addDelMethod = function(delBtnId, cbName, allDelBtnId) {
		/** 绑定列表中的删除按钮 * */
		$("a[name='" + delBtnId + "']").click(function() {
			var id = $(this).attr("id");
			ajaxAction(actionUrl + "/" + id, "DELETE", {}, originUrl); // 提交
		});

		/**
		 * 绑定删除按钮
		 */
		$("button[id='" + allDelBtnId + "']").click(function() {
			var ids = new Array();
			var index = 0; // 数组下标

			/**
			 * 遍历所有复选框
			 */
			$("input[name='" + cbName + "']").each(function() {
				if ($(this).is(':checked')) {
					var id = $(this).attr("id");
					ids[index] = id;
					index++;
				}

			});
			if (ids.length != 0) {
				ajaxAction(actionUrl + "/all", "DELETE", {
					ids : ids
				}, originUrl); // 提交
			}
		});

	},
	/**
	 * 更新操作 btnId:修改按钮id data:需要提交的参数名称和字段类型，格式为{name:type, "name":"input"}
	 * size:需要提交的参数个数 btnId:模态框保存按钮id
	 */
	this.addUpdateMethod = function(UpdateBtnId, btnId, data) {
		/**
		 * 绑定修改按钮
		 */
		$("a[name='" + UpdateBtnId + "']").click(function() {

			$("button[id='" + btnId + "']").attr("name", "update");

			var id = $(this).attr("id");
			var record = jsonData[id];
			for ( var name in data) {
				var type = data[name];
				if (type == "textarea") {
					$(type + "[name='" + name + "']").text(record[name]);
				} else if (type == "select") {
					var i = name.indexOf('.');
					var objName = name.substring(0, i); // 取得对象名称
					$(type + "[name='" + name + "']").val(record[objName].id);
				} else {
					$(type + "[name='" + name + "']").val(record[name]);
				}

			}
		});
	}
}

/**
 * ajax提交
 */
function ajaxAction(url, method, jsonData, currentUrl) {
	var actionMethod = null;
	if (method != "POST" && method != "GET") {
		jsonData["_method"] = method;
		actionMethod = "POST";
	}else{
		actionMethod = method;
	}
	$.ajax({
		type : actionMethod,
		url : url,
		data : jsonData,
		dataType : "json",
		success : function(data) {
			showMsg4model(data, data.status);
			// showSuccessMsg("提示", data.message, null, null);
			toMain(currentUrl);
		}
	});
}

/** 工具提示 * */
function tooltip_placement(context, source) {
	var $source = $(source);
	var $parent = $source.closest('table')
	var off1 = $parent.offset();
	var w1 = $parent.width();

	var off2 = $source.offset();
	// var w2 = $source.width();

	if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
		return 'right';
	return 'left';
}