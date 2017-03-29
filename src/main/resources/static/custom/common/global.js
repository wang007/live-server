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


Global.init(); // 相关方法初始化

