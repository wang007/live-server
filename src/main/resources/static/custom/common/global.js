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
            title: title,
            text: content,
            type: 'info',
            styling: 'bootstrap3',
            delay: 3000  // 停留时间
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
 * dataTable强化类
 * @description 对原生table进行功能强化
 * 使用方式:
 *          var table = new DataTablePlus({'targetId':'#xxx','columns':[{'name':'xxx','title':'xxxx'},{...},...],'dataUrl':'xxxUrl'});
 *          var dataTable = table.build(); // 返回原生dataTable API
 * option:
 *          targetId : 目标dom元素id,需带上'#'符号
 *          columns  : 列表项基础信息，注意列表默认首列启用checkbox，checkbox自动定义id作为val,所以无需重复为列表定义id属性或checkbox元素
 *          dataUrl  : 数据请求地址
 * @param option {targetId:'#xxx','columns':[{'name':'与实体属性名一致','title','表格列标题'},...],'dataUrl':'数据请求地址'}
 * @constructor
 */
var DataTablePlus = function (option) {
    this.option = option;
    this.build = function () {
        var targetId = option['targetId']; // 目标dom元素id
        var columns = option['columns']; // 列表基础信息
        var dataUrl = option['dataUrl']; // 数据请求地址
        var defaultColumns = [
            {
                "data": "id",
                "name": "id",
                "title": '<input type="checkbox" name="iCheckGroup" value="all"/>'
            }
        ]; // 默认列表信息
        for (var i = 0; i < columns.length; i++) {
            var column = columns[i];
            var name = column['name'];
            var title = column['title'];
            defaultColumns.push({'data': name, 'name': name, 'title': title});
        } // 添加用户自定义列参数
        var table = $(targetId).dataTable({
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
            "columnDefs": [
                {
                    "targets": 0,
                    "width": "3%",
                    "orderable": false,
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox" name="iCheckGroup" value="' + data + '"/>'; // 创建选择框
                    }
                }
            ], // 设置默认值
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
                    '<button type="button" id="btn_datatable_refresh" name="datatable_btn_group" class="btn btn-info datatable-btn" data-placement="top" title="刷新"><i class="fa fa-refresh"></i></button>');
                $("#datatable_input_search").attr({
                    "class": "form-group pull-right top_search"
                });
                $("#datatable_input_search").append('<div class="input-group">' +
                    '<input type="text" id="search_input" class="form-control search-query" placeholder="请输入搜索内容"/>' +
                    '<span class="input-group-btn">' +
                    '<button type="button" id="btn_datatable_search" name="datatable_btn_group" class="btn btn-purple btn-sm" data-placement="top" title="搜索"><span class="ace-icon fa fa-search icon-on-right bigger-110"></span></button>' +
                    '</span>' +
                    '</div>');

                /**
                 * 绑定按钮组
                 */
                $("button[name='datatable_btn_group']").on("click", function () {
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
                            console.log("create");
                            break; // 新增
                        case "btn_datatable_update":
                            console.log("update");
                            break; // 修改
                        case "btn_datatable_del":
                            console.log("del");
                            break; // 删除
                    }
                });
            }
        }).api();

        /**
         * 刷新表格
         */
        function reloadTable() {
            table.ajax.reload(function (json) {
                // 请求到的数据做相应的操作，如详情查看等
            }, true); // 刷新表格，分页信息重置
        }

        return table;
    }; // 构建表格
};
Global.init(); // 相关方法初始化

