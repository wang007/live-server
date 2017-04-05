/**
 * 全局函数
 */
var Global = (function () {
    /**
     * 初始化
     */
    var init = function () {
        console.log("init_global");
    };
    /**
     * 通知
     * @param title 通知标题
     * @param content 通知内容
     * @param type 通知类型{success|info|error|warning|dark}
     */
    var notify = function (title, content, type) {
        var notification = {
            title: title || "",
            text: content || "",
            type: 'info',
            styling: 'bootstrap3',
            delay: 2000  // 停留时间
        }; // 通知实体
        switch (type) {
            case 'warning':
                delete notification['type'];
                break;
            case 'dark':
                notification['addclass'] = type;
                break;
            default:
                notification['type'] = type;
                break;
        } // 通知类型对应的调整
        new PNotify(notification); // 创建通知
    };

    /**
     * 警告
     * @param title
     * @param content
     * @param callback
     * @param type 警告类型{warning|danger}
     */
    var warning = function (title, content, type, callback) {
        var ids = {
            "btn": "#btn_warning_modal_confirm",
            "h4": "#warning_modal_title_h4",
            "title": "#warning_modal_title",
            "content": "#warning_modal_content",
            "modal": "#global_warning_modal"
        }; // 元素id

        if ($(ids.h4).is('.red') && type == "warning") {
            $(ids.h4).removeClass('red');
            $(ids.btn).removeClass('btn-danger');
            $(ids.h4).addClass('orange');
            $(ids.btn).addClass("btn-warning");
        } // 调整样式
        if ($(ids.h4).is('.orange') && type == "danger") {
            $(ids.h4).removeClass('orange');
            $(ids.btn).removeClass('btn-warning');
            $(ids.h4).addClass('red');
            $(ids.btn).addClass("btn-danger");
        } // 调整样式

        if (type == "danger") {
            $(ids.btn).html('<i class="fa fa-trash"></i> 确 定');
        } else {
            $(ids.btn).text("确 定");
        } // 调整按钮内容


        $(ids.title).text(title); // 设置标题
        $(ids.content).text(content); // 设置内容
        $(ids.btn).unbind('click').bind('click', function () {
            callback();
        }); // 绑定确定按钮，同时防止多次绑定
        $(ids.modal).modal(); // 显示模态框
    };

    return {
        init: init,
        notify: notify,
        warning: warning
    };
})();

/**
 * dataTable强化插件
 * @description 对原生table进行功能强化,默认提供删除、详情查看、添加、修改、刷新、搜索等功能。
 * 注意事项：默认启用Restful风格，本插件请求类型与springmvc请求类型比对
 *           删除请求 : RequestMethod.DELETE
 *           数据请求 : RequestMethod.POST
 *           新增请求 : RequestMethod.POST
 *           修改请求 : RequestMethod.PUT
 * 使用方式:
 *          var table = new DataTablePlus({'targetId':'#xxx','columns':[{'name':'xxx','title':'xxxx'},{...},...],'dataUrl':'xxxUrl'});
 *          var dataTable = table.build(); // 返回原生dataTable API
 * option:
 *          targetId : 目标dom元素id
 *          columns : 列表项基础信息，注意列表默认首列启用checkbox，checkbox自动定义id作为val,所以无需重复为列表定义id属性或checkbox元素
 *              columns[i].name : 用于获取数据key值
 *              columns[i].title : 列标题
 *              columns[i].detail : {true, false} 是否用于详情查看
 *              columns[i].show : {true, false} 是否用于表格数据显示
 *              columns[i].edit : {true, false} 是否可编辑
 *              columns[i].inputType : {text|password|url|email|textarea|select|checkbox|radio|switch|spinner|datapicker} 若可编辑，设置对应的控件类型
 *              columns[i].type : {int|float|string} 字段类型
 *              columns[i].data : 若控件为select、checkbox、radio 需要指定数据源数组
 *                  data[i].text : 对应的选项名称
 *                  data[i].value : 对应的选项值
 *                  data[i].checked : {true, false} 是否默认选中，控件类型为radio仅可指定一个，控件类型为checkbox可指定多个
 *                  data[i].selected : {true, false} 是否默认选中，控件类型为select专用，仅可指定一个
 *              columns[i].editName : 控件为select专用，用于提交添加参数和修改参数时指定别名，往往select相关的显示属性名和提交属性名是不一致的，如显示属性名为xxxTypeName,提交属性名为xxxType.id
 *              columns[i].detailRender : function(data){xxx return data} 满足详情显示时对特殊字段数据的处理，如对true|false进行字符映射，注意一定要要返回data,需启用columns[i].detail = true
 *              columns[i].prefix : 若启用的实体中有主从表的关系，从表属性需要提供前缀让后台能够正确区分主从表属性，与hql/sql指定的实体/表名 的别名一致
 *          url : 数据请求地址
 *              url.edit : 添加和修改提交地址，提交类型以post和push区分,若指定modal的工作模式为非自动，则无需填写此项
 *              url.delete : 删除提交地址，提交类型为delete
 *              url.data : 数据拉取地址, 提交类型为post
 *          responseArguments : 服务器响应参数，注意直接返回单一json数据，不予许嵌套
 *              responseArguments.successMsgName : 响应成功标志的key值
 *              responseArguments.successCode : 响应成功的状态码
 *              responseArguments.errorMsgName : 响应失败返回的错误信息对应的key值
 *          modal : 用于指定 edit modal 和 info modal ,即添加数据和修改数据用的modal和显示详细信息用的modal
 *              auto : {true|false} 是否启用自动modal模式，默认为true,若不启用需要为插件指定自定义editModalId、infoModalId 和 一个回调方法用于绑定按钮的点击事件
 *              editModalId : 指定需要与插件按钮绑定的 edit modal id
 *              infoModalId : 指定需要与插件按钮绑定的 info modal id
 *              clickCallBack : function(call){} 按钮点击回调方法
 *                  call : 回调相关参数
 *                      call.btnType : {create|update|info} 当前按钮类型、
 *                      call.data : 若按钮类型为update和info,提供用于修改的当前行的全部数据
 *          rules : 默认启用jquery.validate，rules用于指定检验规则，若开启非auto modal工作模式，无需指定
 *          ajax : function(data){} 插件向后台提交数据前，可通过此方法对数据进行修改和添加，注意一定要返回data
 *          columnsDefs : 暴露dataTables原生columnsDefs属性以满足单元格的复杂需求，注意因列表第一项以被checkbox占据，所以列表项的索引应该从1开始
 *          masterPrefix : 若启用的实体中有主从表的关系，需要启用前缀与从表进行区分，与hql/sql指定的实体/表名 的别名一致
 * @param option {targetId:'#xxx','columns':[{'name':'与实体属性名一致','title','表格列标题'},...],'responseArguments':{'successMsgName':'xxx','successCode':1001,'errorMsgName':'xxxx'}}
 * @constructor
 */
var DataTablePlus = function (option) {
    this.option = option;
    this.build = function () {
        var targetId = option['targetId']; // 目标dom元素id
        var columns = option['columns']; // 列表基础信息
        var columnsDefs = option['columnsDefs'] || null; // 列表基础定义
        var dataUrl = option['url']['data']; // 数据请求地址
        var delUrl = option['url']['delete']; // 删除提交地址
        var editUrl = option['url']['edit'] || null; // 添加或修改提交地址
        var responseArguments = option['responseArguments']; // 服务器响应状态信息
        var modal = option['modal']; // modal相关参数，用于指定modal的工作方式
        var rules = option['rules']; // 检验规制，默认启用jquery.validate
        var ajax = option['ajax'] || null; // 用于修改提交后台数据的方法
        var masterPrefix = option['masterPrefix'] || null; // 用于区分主从表
        var data; // 当前表格的全部数据
        var validator; // 表单检验器
        var defaultColumns = [
            {
                "data": "id",
                "name": "id",
                "title": '<input type="checkbox" name="iCheckGroup" value="all"/>'
            }
        ]; // 默认列表信息
        var defaultColumnDefs = [
            {
                "targets": 0,
                "width": "3%",
                "orderable": false,
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox" name="iCheckGroup" value="' + data + '"/>'; // 创建选择框
                }
            }
        ]; // 默认列表定义
        for (var i = 0; i < columns.length; i++) {
            var column = columns[i];
            var isShow = column['show']; // 该字段是否用于表格数据显示
            if (isShow) {
                var name = column['name'];
                var title = column['title'];
                defaultColumns.push({'data': name, 'name': name, 'title': title});
            }
        } // 添加用户自定义列参数
        if (columnsDefs != null) {
            for (var i = 0; i < columnsDefs.length; i++) {
                defaultColumnDefs.push(columnsDefs[i]);
            }
        } // 添加用户自定义列表定义

        var table = $("#" + targetId).dataTable({
            "pagingType": "full_numbers",
            "bAutoWidth": false,
            "language": {
                "info": "当前为第 _PAGE_ 页，共_PAGES_页_TOTAL_条记录；",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页",
                    "last": "末页",
                    "first": "首页",
                    "search": "搜索"
                },
                "lengthMenu": "每页显示 _MENU_ 条记录",
                "search": "搜索：",
                "infoEmpty": "没有数据信息...",
                "emptyTable": "表格中没有数据...",
                "infoFiltered": "(在 _MAX_ 条数据中查询)",
                "zeroRecords": "没有找到对应的数据"
            },// 页面信息替换
            "columns": defaultColumns,
            "columnDefs": defaultColumnDefs, // 设置默认值
            "serverSide": true, // 是否开启服务器模式
            "ajax": {
                "url": dataUrl, // 服务器地址
                "contentType": "application/json", // 提交参数类型
                "type": "POST",
                "data": function (d) {
                    // 修改或添加提交数据
                    var searchVal = $("#search_input").val();
                    if (searchVal == "undefinded") {
                        d.search["value"] = "";
                    }
                    else if (searchVal != "" && searchVal != null) {
                        d.search["value"] = searchVal; // 添加过滤参数
                    } else {
                        d.search["value"] = "";
                    }

                    if (masterPrefix != null) {
                        for (var i = 0; i < columns.length; i++) {
                            var column = columns[i];
                            var name = column['name'];
                            var prefix = column['prefix'] || masterPrefix;
                            d['columns'][i + 1]['name'] = prefix + "." + name; // 添加前缀
                        }

                        d['columns'][0]['name'] = masterPrefix + ".id";
                    }

                    if (ajax != null) {
                        d = ajax(d);
                    }
                    return JSON.stringify(d);
                }
            },
            "drawCallback": function (settings) {
                $("input[name='iCheckGroup']").iCheck({
                    checkboxClass: 'icheckbox_flat-blue'
                }); // 为选择框添加样式
                if ($("input[value='all']").is(':checked')) {
                    $("input[name='iCheckGroup']").iCheck('check');
                }
                $("input[name='iCheckGroup']").on("ifToggled", function () {
                    if ($(this).is(':checked')) {
                        if ($(this).val() == "all") {
                            $("input[name='iCheckGroup']").iCheck('check');
                        }
                    } else {
                        if ($(this).val() == "all") {
                            $("input[name='iCheckGroup']").iCheck('uncheck');
                        }
                    }
                });// 绑定表格内checkbox的选择事件

                data = getTableData().data; // 缓存数据
            },
            "dom": '<"#datatable_wrapper.dataTables_wrapper form-inline dt-bootstrap no-footer datatable-plus"' +
            '<"row"<"col-sm-6" <"#datatable_btn_group">><"col-sm-6" <"#datatable_input_search">>>' +
            '<"row"<"col-sm-12" t>>' +
            '<"row"<"col-sm-3" i><"col-sm-3 datatable-length-row" l><"col-sm-6" p>>' +
            '> ',
            "initComplete": function () {
                $("#datatable_btn_group").append('操作：' +
                    '<button type="button" id="btn_datatable_create" name="datatable_btn_group" class="btn btn-success datatable-btn"  data-placement="top" title="添加"><i class="fa fa-plus"></i></button>' +
                    '<button type="button" id="btn_datatable_update" name="datatable_btn_group" class="btn btn-primary datatable-btn" data-placement="top" title="修改"><i class="fa fa-edit"></i></button>' +
                    '<button type="button" id="btn_datatable_del" name="datatable_btn_group" class="btn btn-danger datatable-btn" data-placement="top" title="删除"><i class="fa fa-trash"></i></button>' +
                    '<button type="button" id="btn_datatable_info" name="datatable_btn_group" class="btn btn-info datatable-btn" data-placement="top" title="详情"> &nbsp;<i class="fa fa-info"></i>&nbsp; </button>' +
                    '<button type="button" id="btn_datatable_refresh" name="datatable_btn_group" class="btn btn-defalut datatable-btn" data-placement="top" title="刷新"><i class="fa fa-refresh"></i></button>');
                $("#datatable_input_search").attr({
                    "class": "form-group pull-right top_search"
                });
                $("#datatable_input_search").append('<div class="input-group">' +
                    '<input type="text" id="search_input" class="form-control search-query" placeholder="请输入搜索内容"/>' +
                    '<span class="input-group-btn">' +
                    '<button type="button" id="btn_datatable_search" name="datatable_btn_group" class="btn btn-purple btn-sm" data-placement="top" title="搜索"><span class="ace-icon fa fa-search icon-on-right bigger-110"></span></button>' +
                    '</span>' +
                    '</div>');
                buildFormElement(columns); // 构建表单
                /**
                 * 绑定按钮组
                 */
                $("button[name='datatable_btn_group']").unbind("click").on("click", function () {
                    var id = $(this).attr("id");
                    switch (id) {
                        case "btn_datatable_refresh":
                            $("#search_input").val("");
                            reloadTable(); // 刷新页面
                            break; // 刷新
                        case "btn_datatable_search":
                            var val = $("#search_input").val();
                            if (val.length > 0 && val != null) {
                                reloadTable(); // 刷新页面
                            }
                            break; // 搜索
                        case "btn_datatable_create":
                            var isAuto = modal['auto'];
                            if (isAuto) {
                                // 自动模式
                                showEditModal("create"); // 显示modal
                                for (var k = 0; k < columns.length; k++) {
                                    var column = columns[k];
                                    var isEdit = column['edit'] || false;
                                    if (isEdit) {
                                        var inputType = column['inputType'];
                                        var name = column['name'];
                                        var rule = rules[name] || null;
                                        if (rule != null) {
                                            var isRequired = rule['required'] || false;
                                            if (isRequired) {
                                                $("span[name='" + name + "'] > span").detach("#required_hint"); // 去重
                                                $("span[name='" + name + "']").prepend('<span class="red" id="required_hint">*</span>');
                                            }
                                        }
                                        switch (inputType) {
                                            case 'textarea':
                                                $("textarea[name='" + name + "']").val("");
                                                var $div = $("div[name='" + name + "']");
                                                if ($div.is(".has-error")) {
                                                    $div.removeClass("has-error");
                                                    $("span[name='" + name + "'] > i").remove(".glyphicon-remove");
                                                    $("label[class='error']").text("");
                                                } // 清除残留样式
                                                break;
                                            case 'select':
                                                $("select[name='" + name + "']").select2("val", "");
                                                break;
                                            case 'switch':
                                                $("input[name='" + name + "']").attr("value", "1");
                                                $("input[name='" + name + "']").prop('checked', true);
                                                break;
                                            default:
                                                $("input[name='" + name + "']").val("");
                                                var $div = $("div[name='" + name + "']");
                                                if ($div.is(".has-error")) {
                                                    $div.removeClass("has-error");
                                                    $("span[name='" + name + "'] > i").remove(".glyphicon-remove");
                                                    $("label[class='error']").text("");
                                                } // 清除残留样式
                                                break;
                                        }
                                    }
                                }
                            } else {
                                // 非自动模式

                                $("#" + modal['editModalId']).modal(); // 显示modal
                                modal['clickCallBack']({'btnType': 'create'}); // 执行回调
                            }
                            break; // 新增
                        case "btn_datatable_update":

                            var ids = [];
                            var index;
                            var i = 0;
                            $("input[name='iCheckGroup']").each(function () {
                                i++; // 定位选择框所在的行索引
                                var val = $(this).val();
                                if (val != "all" && $(this).is(':checked')) {
                                    ids.push(val);
                                    index = i - 2;
                                }
                            });

                            if (ids.length == 1) {
                                var originData = data[index];
                                var isAuto = modal['auto'];
                                if (isAuto) {
                                    // 自动模式
                                    for (var k = 0; k < columns.length; k++) {
                                        var column = columns[k];
                                        var isEdit = column['edit'] || false;
                                        if (isEdit) {
                                            var name = column['name'];
                                            var value = originData[name];
                                            var inputType = column['inputType'];
                                            var rule = rules[name] || null;
                                            if (rule != null) {
                                                var isRequired = rule['required'] || false;
                                                if (isRequired) {
                                                    $("span[name='" + name + "'] > span").detach("#required_hint"); // 去重
                                                    $("span[name='" + name + "']").prepend('<span class="red" id="required_hint">*</span>');
                                                }
                                            }
                                            switch (inputType) {
                                                case 'textarea':
                                                    $("textarea[name='" + name + "']").val(value);
                                                    var $div = $("div[name='" + name + "']");
                                                    if ($div.is(".has-error")) {
                                                        $div.removeClass("has-error");
                                                        $("span[name='" + name + "'] > i").remove(".glyphicon-remove");
                                                        $("label[class='error']").text("");
                                                    } // 清除残留样式
                                                    break;
                                                case 'select':
                                                    var optionInfos = column['data'];
                                                    for (var j = 0; j < optionInfos.length; j++) {
                                                        var optionInfo = optionInfos[j];
                                                        var txt = optionInfo['text'];
                                                        if (txt == value) {
                                                            value = optionInfo['value'];
                                                        }
                                                    }
                                                    $("select[name='" + name + "']").select2("val", value);
                                                    break;
                                                case 'switch':
                                                    if (value) {
                                                        $("input[name='" + name + "']").attr("value", "1");
                                                        $("input[name='" + name + "']").prop('checked', true);
                                                    } else {
                                                        $("input[name='" + name + "']").attr("value", "0");
                                                        $("input[name='" + name + "']").prop('checked', false);
                                                    }
                                                    break;
                                                default:
                                                    $("input[name='" + name + "']").val(value);
                                                    var $div = $("div[name='" + name + "']");
                                                    if ($div.is(".has-error")) {
                                                        $div.removeClass("has-error");
                                                        $("span[name='" + name + "'] > i").remove(".glyphicon-remove");
                                                        $("label[class='error']").text("");
                                                    } // 清除残留样式
                                                    break;
                                            }
                                        }
                                    }
                                    showEditModal("update"); // 显示modal
                                } else {
                                    // 非自动模式
                                    $("#" + modal['editModalId']).modal(); // 显示modal
                                    modal['clickCallBack']({'btnType': 'update', 'data': originData}); // 执行回调
                                }

                            } else {
                                Global.notify("修改操作提醒", "未选取有效数据或选中多条数据！", "warning");
                            }
                            break; // 修改
                        case "btn_datatable_del":
                            var ids = [];
                            $("input[name='iCheckGroup']").each(function () {
                                var val = $(this).val();
                                if (val != "all" && $(this).is(':checked')) {
                                    ids.push(val);
                                }
                            });
                            if (ids.length > 0) {
                                Global.warning("删除警告", "确定要删除选中的记录吗？", "danger", function () {
                                    $.ajax({
                                        type: "POST",
                                        url: delUrl,
                                        dataType: "json",
                                        data: {
                                            '_method': 'delete',
                                            'ids': ids
                                        },
                                        success: function (msg) {
                                            var successMsgCode = responseArguments['successMsgCode'];
                                            var successCode = responseArguments['successCode'];
                                            var errorMsgName = responseArguments['errorMsgName'];
                                            if (msg[successMsgCode] == successCode) {
                                                Global.notify("操作提示：", "删除成功！", "success");
                                                reloadTable(); // 刷新页面
                                            } else {
                                                Global.notify("操作提示：", "删除失败，" + msg[errorMsgName], "error");
                                            }
                                        }
                                    });
                                });
                            }
                            break; // 删除
                        case "btn_datatable_info":
                            var ids = [];
                            var index;
                            var i = 0;
                            $("input[name='iCheckGroup']").each(function () {
                                i++; // 定位选择框所在的行索引
                                var val = $(this).val();
                                if (val != "all" && $(this).is(':checked')) {
                                    ids.push(val);
                                    index = i - 2;
                                }
                            });

                            if (ids.length == 1) {
                                var isAuto = modal['auto'];
                                if (isAuto) {
                                    $("#datatable_info_modal").modal(); // 显示modal
                                    var record = data[index];
                                    var $ul = $("#datatable_info_modal_list");
                                    $ul.empty();
                                    for (var i = 0; i < columns.length; i++) {
                                        var column = columns[i];
                                        var isDetail = column['detail'] || false; // 该字段是否用于详情查看
                                        var detailRender = column['detailRender'] || null;
                                        if (isDetail) {
                                            var name = column['name'];
                                            var title = column['title'];
                                            var val = record[name];
                                            if (detailRender != null) {
                                                val = detailRender(val);
                                            }
                                            var $li = $("<li></li>");
                                            $li.append('<b>' + title + '：</b>' + val);
                                            $li.appendTo($ul);
                                        }
                                    } // 构建数据列表
                                } else {
                                    // 非自动模式
                                    $("#" + modal['detailModalId']).modal(); // 显示modal
                                    modal['clickCallBack']({'btnType': 'info', 'data': originData}); // 执行回调
                                }
                            } else {
                                Global.notify("详情操作提醒", "未选取有效数据或选中了多条数据！", "warning");
                            }
                            break; // 详情
                        case "datatable_edit_modal_btn":
                            var $btn = $("#datatable_edit_modal_btn");
                            if (!$("#datatable_edit_form").valid()) {
                                Global.notify(" 错误提醒：", "请修改好表单错误项！", "error");
                                return false;
                            }

                            if ($btn.is('.btn-success')) {
                                // 新增
                                var postData = {};
                                for (var j = 0; j < columns.length; j++) {
                                    var column = columns[j];
                                    var isEdit = column['edit'] || false; // 该字段是否用于编辑
                                    if (isEdit) {
                                        var name = column['name'];
                                        var inputType = column['inputType'];
                                        var type = column['type'] || "string";
                                        var val;
                                        switch (inputType) {
                                            case 'textarea':
                                                val = $("textarea[name='" + name + "']").val();
                                                break;
                                            case 'select' :
                                                val = $("select[name='" + name + "']").val();
                                                name = column['editName'];
                                                break;
                                            case 'switch':
                                                val = $("input[name='" + name + "']").val();
                                                if (val == "1") {
                                                    val = true;
                                                } else {
                                                    val = false;
                                                }
                                                break;
                                            default:
                                                val = $("input[name='" + name + "']").val();
                                                break;
                                        }
                                        if (type == "int") {
                                            postData[name] = parseInt(val);
                                        } else if (type == "float") {
                                            postData[name] = parseFloat(val);
                                        } else {
                                            postData[name] = val;
                                        }
                                    }
                                }
                                postModalData(editUrl, postData, "post") // 提交postData
                            } else {
                                // 修改
                                var _index;
                                var i = 0;
                                $("input[name='iCheckGroup']").each(function () {
                                    i++; // 定位选择框所在的行索引
                                    var val = $(this).val();
                                    if (val != "all" && $(this).is(':checked')) {
                                        _index = i - 2;
                                    }
                                });
                                var postData = data[_index];
                                for (var j = 0; j < columns.length; j++) {
                                    var column = columns[j];
                                    var isEdit = column['edit'] || false; // 该字段是否用于编辑
                                    if (isEdit) {
                                        var name = column['name'];
                                        var inputType = column['inputType'];
                                        var type = column['type'] || "string";
                                        var val;
                                        switch (inputType) {
                                            case 'textarea':
                                                val = $("textarea[name='" + name + "']").val();
                                                break;
                                            case 'select' :
                                                val = $("select[name='" + name + "']").val();
                                                name = column['editName'];
                                                break;
                                            case 'switch':
                                                val = $("input[name='" + name + "']").val();
                                                if (val == "1") {
                                                    val = true;
                                                } else {
                                                    val = false;
                                                }
                                                break;
                                            default:
                                                val = $("input[name='" + name + "']").val();
                                                break;
                                        }
                                        if (type == "int") {
                                            postData[name] = parseInt(val);
                                        } else if (type == "float") {
                                            postData[name] = parseFloat(val);
                                        } else {
                                            postData[name] = val;
                                        }
                                    }
                                }
                                // 提交postData
                                postModalData(editUrl, postData, "put") // 提交postData
                            }
                            $("#datatable_edit_modal").modal('hide'); // 关闭模态框
                            break; // 编辑modal提交按钮
                    }
                });
            }
        }).api();

        /**
         * 显示编辑模态框
         * @param btnType
         */
        function showEditModal(btnType) {
            $("#datatable_edit_modal").modal();
            var $btn = $("#datatable_edit_modal_btn");
            if ($btn.is('.btn-success') && btnType == 'update') {
                $btn.removeClass('btn-success')
                $btn.addClass('btn-primary');
                $btn.html('<i class="fa fa-edit"></i> 修改');
                $("#datatable_edit_modal_title").text('修改数据');
            } // 调整样式
            if ($btn.is('.btn-primary') && btnType == 'create') {
                $btn.removeClass('btn-primary')
                $btn.addClass('btn-success');
                $btn.html('<i class="fa fa-plus"></i> 添加');
                $("#datatable_edit_modal_title").text('新增数据');
            }// 调整样式

        }

        /**
         * 提交编辑模态框的数据
         * @param url 提交地址
         * @param data 提交数据
         * @param type 提交类型
         */
        function postModalData(url, data, type) {
            if (type == 'put') {
                data['_method'] = 'put'
            }
            $.ajax({
                type: "POST",
                url: url,
                data: $.param(data),
                success: function (msg) {
                    var successMsgCode = responseArguments['successMsgCode'];
                    var successCode = responseArguments['successCode'];
                    var errorMsgName = responseArguments['errorMsgName'];
                    var option = (type == "post") ? "添加" : "修改";
                    if (msg[successMsgCode] == successCode) {
                        Global.notify("操作提示：", option + "成功！", "success");
                        reloadTable(); // 刷新页面
                    } else {
                        Global.notify("操作提示：", option + "失败，" + msg[errorMsgName], "error");
                    }
                }
            });
        }

        /**
         * 刷新表格
         */
        function reloadTable() {
            table.ajax.reload(function (json) {
                // 请求到的数据做相应的操作，如详情查看等
            }, true); // 刷新表格，分页信息重置
        }

        /**
         * 获取表格数据
         */
        function getTableData() {
            return table.ajax.json();
        }

        /**
         * 构建表单元素
         * @param columns
         */
        function buildFormElement(columns) {
            var buffer = new StringBuffer();
            var specialEls = [];
            var el = {
                'form': '<form class="form-horizontal" role="form" id="datatable_edit_form">',
                '/form': '</form>',
                'label': '<label class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">',
                '/label': '</label>',
                'span': '<div class="help-block col-xs-12 col-sm-reset inline">',
                '/span': '</div>',
                'col5': '<div class="col-xs-12 col-sm-5">',
                '/col5': '</div>',
                'col6': '<div class="col-sm-6">',
                '/col6': '</div>'
            };
            buffer.append(el['form']);
            for (var i = 0; i < columns.length; i++) {
                var column = columns[i];
                var isEdit = column['edit'] || false; // 该字段是否用于编辑
                if (isEdit) {
                    var inputType = column['inputType'];
                    var title = column['title'];
                    var name = column['name'];
                    var hint = "";
                    var rule = rules[name] || null;
                    if (rule != null) {
                        var isRequired = rule['required'] || false;
                        if (isRequired) {
                            hint = '<span class="red" id="required_hint">*</span>';
                        }
                    }
                    buffer.append('<div class="form-group" name="' + name + '">');
                    buffer.append(el['label']);
                    buffer.append(title);
                    buffer.append(el['/label']);
                    switch (inputType) {
                        case 'text' :
                            buffer.append(el['col5']);
                            buffer.append('<input type="text" id="_' + name + '" class="width-100" name="' + name + '">');
                            buffer.append(el['/col5']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);
                            break;
                        case 'password' :
                            buffer.append(el['col5']);
                            buffer.append('<input type="password" id="_' + name + '" class="width-100" name="' + name + '">');
                            buffer.append(el['/col5']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);
                            break;
                        case 'url' :
                            buffer.append(el['col5']);
                            buffer.append('<input type="url" id="_' + name + '" class="width-100" name="' + name + '">');
                            buffer.append(el['/col5']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);
                            break;
                        case 'email' :
                            buffer.append(el['col5']);
                            buffer.append('<input type="email" id="_' + name + '" class="width-100" name="' + name + '">');
                            buffer.append(el['/col5']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);
                            break;
                        case 'textarea' :
                            buffer.append(el['col6']);
                            buffer.append('<textarea class="form-control limited" id="_' + name + '" maxlength="' + column['maxLength'] + '" name="' + name + '"></textarea>');
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);
                            break;
                        case 'select' :
                            var data = column['data'];
                            buffer.append(el['col6']);
                            buffer.append('<select class="select2" style="width: 100%" id="_' + name + '" name="' + name + '"data-placeholder="请选择...">');
                            buffer.append('<option value=""></option>');
                            for (var j = 0; j < data.length; j++) {
                                buffer.append('<option value="' + data[j]['value'] + '">' + data[j]['text'] + '</option>');
                            }
                            buffer.append('</select>');
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                        case 'checkbox' :
                            var data = column['data'];
                            buffer.append(el['col6']);
                            for (var j = 0; j < data.length; j++) {
                                var isChecked = data[j]['checked'] || false;
                                var $checked = '';
                                if (isChecked) {
                                    $checked = 'checked="checked"';
                                }
                                buffer.append('<div class="checkbox" id="_' + name + '"><label>');
                                buffer.append('<input  name="' + name + '" ' + $checked + ' value="' + data[j]['value'] + '" type="checkbox"/>');
                                buffer.append('<span class="lbl"> ' + data[j]['label'] + '</span>');
                                buffer.append('</label></div>');
                            }
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                        case 'radio' :
                            var data = column['data'];
                            buffer.append(el['col6']);
                            for (var j = 0; j < data.length; j++) {
                                var isChecked = data[j]['checked'] || false;
                                var $checked = '';
                                if (isChecked) {
                                    $checked = 'checked="checked"';
                                }
                                buffer.append('<div class="radio" id="_' + name + '"><label>');
                                buffer.append('<input name="' + name + '" ' + $checked + ' value="' + data[j]['value'] + '" type="radio"/>');
                                buffer.append('<span class="lbl"> ' + data[j]['label'] + '</span>');
                                buffer.append('</label></div>');
                            }
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                        case 'switch' :
                            var isChecked = column['checked'] || false;
                            var $checked = '';
                            if (isChecked) {
                                $checked = 'checked="checked"';
                            }
                            buffer.append(el['col6']);
                            buffer.append('<div class="switch"><label>');
                            buffer.append('<input id="_' + name + '" name="' + name + '" ' + $checked + ' class="ace ace-switch ace-switch-4 btn-empty" type="checkbox">');
                            buffer.append('<span class="lbl"></span>');
                            buffer.append('</label></div>');
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                        case 'spinner' :
                            buffer.append(el['col6']);
                            buffer.append('<div class="spinner">');
                            buffer.append('<input id="_' + name + '" type="text" name="' + name + '" class="input-mini" id="spinner"/>');
                            buffer.append('</div>');
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                        case 'datepicker':
                            buffer.append(el['col6']);
                            buffer.append('<div class="input-group">');
                            buffer.append('<input id="_' + name + '" class="form-control date-picker" name="' + name + '" type="text" data-date-format="yyyy-mm-dd"/>');
                            buffer.append('<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>');
                            buffer.append('</div>');
                            buffer.append(el['/col6']);
                            buffer.append(el['span']);
                            buffer.append('<span class="middle" name="' + name + '">' + hint + '</span>');
                            buffer.append(el['/span']);

                            specialEls.push(column);
                            break;
                    }
                    buffer.append('</div>');
                }
            }
            buffer.append(el['/form']);
            $("#datatable_edit_modal_body").empty(); // 清空元素，若有的话
            $("#datatable_edit_modal_body").append(buffer.toString()); // 构建表单


            for (var k = 0; k < specialEls.length; k++) {
                var column = specialEls[k];
                var inputType = column['inputType'];
                var name = column['name'];
                switch (inputType) {
                    case 'checkbox' :
                        $("input[name='" + name + "']").iCheck({
                            checkboxClass: 'icheckbox_flat-blue'
                        }); // 为选择框添加样式
                        break;
                    case 'radio' :
                        $("input[name='" + name + "']").iCheck({
                            radioClass: 'iradio_flat-blue'
                        }); // 为单选框添加样式
                        break;
                    case 'spinner' :
                        $("input[name='" + name + "']").ace_spinner({
                            value: column['value'],
                            min: column['min'],
                            max: column['max'],
                            step: column['step'],
                            btn_up_class: 'btn-info',
                            btn_down_class: 'btn-info'
                        })
                            .closest('.ace-spinner')

                            .on('changed.fu.spinbox', function () {
                                //alert($('#spinner1').val())
                            });
                        break;
                    case 'select':
                        $("select[name='" + name + "']").select2({'allowClear': true});
                        break;

                    case 'datepicker':
                        $("input[name='" + name + "']").datepicker({
                            autoclose: true,
                            todayHighlight: true
                        });
                        break;
                    case 'switch':
                        $("input[name='" + name + "']").click(function () {
                            var val = $(this).val();
                            if (val == "1") {
                                $(this).attr('value', '0');
                                $(this).prop('checked', false);
                            } else {
                                $(this).attr('value', '1');
                                $(this).prop('checked', true);
                            }
                        }); // 置换按钮状态
                        break;
                }
            }

            // 开启检验
            validator = $("#datatable_edit_form").validate({
                rules: rules,
                onkeyup: false,
                onclick: false,
                ignore: [],
                debug: false, // 是否关闭表单提交功能
                errorPlacement: function (error, element) {
                    var name = element.attr("name");
                    var $span = $("span[name='" + name + "']");
                    error.appendTo($span);
                }, // 指定错误信息提示位置
                success: function (label) {
                    var $span = label.parent();
                    var name = $span.attr("name");
                    var $div = $("div[name='" + name + "']");
                    $("span[name='" + name + "'] > i").remove(".glyphicon-remove"); // 清除图标
                    $div.removeClass("has-error"); // 清除样式
                    var rule = rules[name] || null;
                    if (rule != null) {
                        var isRequired = rule['required'] || false;
                        if (isRequired) {
                            $("span[name='" + name + "'] > span").detach("#required_hint"); // 去重
                            $("span[name='" + name + "']").prepend('<span class="red" id="required_hint">*</span>');
                        }
                    }
                },// 指定检验完成后的样式
                highlight: function (element, errorClass, validClass) {
                    var name = element.name;
                    var $span = $("span[name='" + name + "']");
                    var $div = $("div[name='" + name + "']");
                    $("span[name='" + name + "'] > span").detach("#required_hint"); // 清除*字符
                    $("span[name='" + name + "'] > i").remove(".glyphicon-remove"); // 去重
                    $span.prepend('<i class="glyphicon glyphicon-remove red"></i> ');
                    $div.addClass("has-error"); // 添加错误样式
                }, // 检验出错
                unhighlight: function (element, errorClass, validClass) {
                    var name = element.name;
                    var $div = $("div[name='" + name + "']");
                    $("span[name='" + name + "] > i").remove(".glyphicon-remove"); // 清除图标
                    $div.removeClass("has-error"); // 清除样式
                } // 检验正常
            });

        }

        return table;
    }; // 构建表格
};

/**
 * 字符串缓存类，方便陪拼接字符串
 * @constructor
 */
function StringBuffer() {
    this.__strings__ = new Array();
}

StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this;    //方便链式操作
}
StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
}


Global.init(); // 相关方法初始化

