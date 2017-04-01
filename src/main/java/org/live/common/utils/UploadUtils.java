package org.live.common.utils;

import java.io.File;
import java.io.IOException;

/**
 *  文件上传的工具类
 * Created by Mr.wang on 2017/3/31.
 */
public class UploadUtils {

    /**
     * 获取文件的后缀名, 包括点号
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {

        int dotIndex = fileName.lastIndexOf(".") ;  //获取点的位置
        return dotIndex == -1 ? "": fileName.substring(dotIndex)  ;
    }

    /**
     * 创建一个文件，
     * @param parentPath 父级目录
     * @param childPath 子级目录或文件名
     * @return
     * @throws IOException
     */
    public static File createFile(String parentPath, String childPath) throws IOException {
        File file = new File(parentPath, childPath) ;
        File parentFile = file.getParentFile() ;
        if(parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs() ;
        }
        if(!file.exists()) {
            file.createNewFile() ;
        }
        return file ;
    }

    /**
     *  创建一个文件
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException {
        File file = new File(path) ;
        File parentFile = file.getParentFile() ;
        if(parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs() ;
        }
        if(!file.exists()) {
            file.createNewFile() ;
        }
        return file ;
    }



    public static void main(String[] args) throws Exception {

        createFile("c:\\projectDir", "\\upload\\20170403\\haoaoa.txt") ;
    }

}
