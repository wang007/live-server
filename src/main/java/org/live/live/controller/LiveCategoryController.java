package org.live.live.controller;

import org.live.common.constants.SystemConfigConstants;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.support.ServletContextHolder;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.common.utils.CreateOrderNoUtils;
import org.live.common.utils.UploadUtils;
import org.live.live.entity.LiveCategory;
import org.live.live.service.LiveCategoryService;
import org.live.live.vo.LiveCategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 *  直播房间分类的controller
 * Created by Mr.wang on 2017/3/29.
 */
@Controller
@RequestMapping("/live")
public class LiveCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveCategoryController.class) ;

    @Resource
    private LiveCategoryService categoryService ;


    /**
     * 跳转到房间分类的页面
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String toRoomCategory(HttpServletRequest request) {

        try {
            List<LiveCategory> liveCategorys = categoryService.findAllCategory();
            request.setAttribute("liveCategorys", liveCategorys) ;
        } catch (Exception e) {
            LOGGER.error("查询直播房间分类时发生异常", e) ;
        }
        return "live/category" ;
    }

    @RequestMapping(value="/category/serialNo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findMaxSerialNo() {
        ResponseModel<Object>  model = new SimpleResponseModel<Object>() ;
        try {
            Integer serialNo = categoryService.findMaxSerialNo() ;
            if(serialNo == null) serialNo = 0 ;
            model.setData(serialNo+1) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("查询直播分类的最大serialNo出现异常", e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 查询一条分类信息
     * @param id 分类的id
     * @return
     */
    @RequestMapping(value="/category/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> getLiveCategory(@PathVariable String id) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            LiveCategory category = categoryService.findOne(id) ;
            model.setData(category) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("查询单条分类信息失败！", e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     *  添加直播分类
     * @param file
     * @param liveCategory
     * @param enabledNum 是否启用的标记
     * @return
     */
    @RequestMapping(value="/category",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> addLiveCategory(MultipartFile file, LiveCategory liveCategory, @RequestParam(value = "enabled", required = false) Integer enabledNum) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            if(file == null) {
                model.setMessage("上传失败") ;
                model.error() ;
                return model ;
            }

            if(enabledNum == null || enabledNum == 0) liveCategory.setEnabled(false) ;    //启用状态
                //本地文件储存的路径前缀
                String prefixUpload = ServletContextHolder.getAttribute(SystemConfigConstants.REAL_UPLOAD_FILE_DIR_KEY) ;
                LOGGER.debug("文件上传路径的前缀：---> "+ prefixUpload) ;

                String fileSuffix = UploadUtils.getFileSuffix(file.getOriginalFilename()) ; //文件后缀
                //路径： 相对于项目的 /projectDir/upload/系统日期/系统时间+6位随机数.xxx

                String targetPathSuffix = CreateOrderNoUtils.getDate()+File.separator+CreateOrderNoUtils.getCreateOrderNo()+fileSuffix ;

                File targetFile = UploadUtils.createFile(prefixUpload, targetPathSuffix) ;
                file.transferTo(targetFile);

                String dbFilePrefix = ServletContextHolder.getAttribute(SystemConfigConstants.DB_UPLOAD_FILE_PREFIX_KEY) ;
                targetPathSuffix = targetPathSuffix.replace(File.separator, "/") ;  //替换分隔符

                liveCategory.setCoverUrl(dbFilePrefix + "/" + targetPathSuffix) ;

                categoryService.save(liveCategory) ;

                model.setMessage("上传成功") ;
                model.success() ;
                return model ;

        } catch (Exception e) {
                LOGGER.error(e.getMessage(), e) ;
                model.setMessage("上传失败") ;
                model.error() ;
                return model ;
        }

    }


    /**
     * 更新直播分类的封面
     * @param file
     * @param id
     * @return
     */
    @RequestMapping(value = "/category/cover/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateCategoryCover(MultipartFile file, @PathVariable String id) {

        ResponseModel model = new SimpleResponseModel<Object>() ;
        if(file == null) {
            model.setMessage("上传失败") ;
            model.error() ;
            return model ;
        }

        String projectDirPath = ServletContextHolder.getAttribute(SystemConfigConstants.REAL_PROJECT_DIR_KEY) ;
        try {
            LiveCategory liveCategory = categoryService.findOne(id) ;
            String coverUrl = liveCategory.getCoverUrl() ;
            File oldFile = new File(projectDirPath, coverUrl) ;
            if(oldFile.exists()) oldFile.delete() ; //删除之前的图片

            //本地文件储存的路径前缀
            String prefixUpload = ServletContextHolder.getAttribute(SystemConfigConstants.REAL_UPLOAD_FILE_DIR_KEY) ;
            String fileSuffix = UploadUtils.getFileSuffix(file.getOriginalFilename()) ; //文件后缀
            //路径： 相对于项目的 /projectDir/upload/系统日期/系统时间+6位随机数.xxx

            String targetPathSuffix = CreateOrderNoUtils.getDate()+File.separator+CreateOrderNoUtils.getCreateOrderNo()+fileSuffix ;

            File targetFile = UploadUtils.createFile(prefixUpload, targetPathSuffix) ;
            file.transferTo(targetFile);

            String dbFilePrefix = ServletContextHolder.getAttribute(SystemConfigConstants.DB_UPLOAD_FILE_PREFIX_KEY) ;
            targetPathSuffix = targetPathSuffix.replace(File.separator, "/") ;  //替换分隔符

            liveCategory.setCoverUrl(dbFilePrefix + "/" + targetPathSuffix) ;

            categoryService.save(liveCategory) ;

            model.setMessage("上传成功") ;
            model.success() ;
            return model ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.setMessage("上传失败") ;
            model.error() ;
            return model ;
        }
    }


    /**
     * 更新直播分类信息，不包括直播封面
     * @param liveCategoryVo
     * @return
     */
    @RequestMapping(value="/category",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> uploadLiveCategory(LiveCategoryVo liveCategoryVo, @RequestParam(value = "enabled", required = false) Integer enabledNum) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            if(enabledNum == null || enabledNum == 0) liveCategoryVo.setEnabled(false) ;
            LiveCategory liveCategory = categoryService.findOne(liveCategoryVo.getId()) ;
            CopyPropertiesUtils.copyPropertiesIgnoreNull(liveCategory, liveCategoryVo) ;
            categoryService.save(liveCategory) ;
            model.setMessage("保存成功！") ;
            model.success() ;
        } catch (Exception e) {
            model.setMessage("保存失败！") ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 删除直播分类
     * @param id
     * @return
     */
    @RequestMapping(value="/category/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> deleteLiveCategory(@PathVariable String id) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
           boolean successFlag = categoryService.deleteLiveCategoryById(id) ;
           if(successFlag) {
               model.setMessage("删除成功！") ;
               model.success() ;
           } else {
               model.setMessage("删除失败，该直播分类下存在直播间！") ;
               model.error() ;
           }
        } catch(Exception e) {
            model.error() ;
            model.setMessage("删除失败！") ;
        }
        return model ;

    }




}
