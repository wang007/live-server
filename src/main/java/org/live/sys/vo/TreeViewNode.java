package org.live.sys.vo;

import java.util.Map;

/**
 * treeview插件中的节点
 * Created by Mr.wang on 2016/12/4.
 */
public class TreeViewNode {

    /**
     * 节点名称
     */
    private String text ;

    /**
     * 节点的类型："item":文件  "folder":目录
     */
    private String type ;

    /**
     * 附加参数.
     * 包括id。
     * children,指定
     */
    public Map<String, Object> additionalParameters ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(Map<String, Object> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
}



