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
    return {
        init: init,
        notify: notify
    };
})();

Global.init(); // 相关方法初始化
//Global.notify("通知框", "通知框共有五种样式，<br/>'type':{success | info | error | warning | dark} <br/> 使用方法:Global.notify(title, content, type);", "dark");

