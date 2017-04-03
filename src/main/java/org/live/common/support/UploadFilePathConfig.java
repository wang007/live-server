package org.live.common.support;

import org.springframework.stereotype.Component;

/**
 * 这个放到spring容器，在ServletContextHolder进行设值
 *
 * Created by Mr.wang on 2017/4/3.
 */
@Component
public class UploadFilePathConfig {

    public static final String UPLOAD_FILE_ROOT_PATH_KEY = "uploadFileRootPathKey" ;

    public static final String UPLOAD_FILE_PATH_PREFIX_KEY = "uploadFilePathPrefixKey" ;

    public static final String UPLOAD_FILE_PATH_KEY = "uploadFilePathKey" ;

    /**
     *  上传文件根目录
     */
    private String uploadFileRootPath ;

    /**
     * 上传文件路径的前缀,这个路径也要拼接到数据库中的文件路径中
     */
    private String uploadFilePathPrefix ;

    /**
     *  上传文件的路径。 uploadFilePath = uploadFileRootPath + localUplaodFilePrefix
     */
    private String uploadFilePath ;

    public String getUploadFileRootPath() {
        return uploadFileRootPath;
    }

    public void setUploadFileRootPath(String uploadFileRootPath) {
        this.uploadFileRootPath = uploadFileRootPath;
    }

    public String getUploadFilePathPrefix() {
        return uploadFilePathPrefix;
    }

    public void setUploadFilePathPrefix(String uploadFilePathPrefix) {
        this.uploadFilePathPrefix = uploadFilePathPrefix;
    }

    public String getUploadFilePath() {
        return uploadFilePath;
    }

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }
}
