package org.live.app.controller;

import org.live.app.vo.AppAnchorInfo;
import org.live.app.vo.AppLiveRoomVo;
import org.live.app.vo.LiveCategoryVo;
import org.live.common.constants.Constants;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.support.UploadFilePathConfig;
import org.live.common.utils.CreateOrderNoUtils;
import org.live.common.utils.UploadUtils;
import org.live.live.entity.Anchor;
import org.live.live.entity.AnchorLimitation;
import org.live.live.entity.LiveRoom;
import org.live.live.repository.AnchorLimitationRepository;
import org.live.live.service.AnchorLimitationService;
import org.live.live.service.AnchorService;
import org.live.live.service.LiveCategoryService;
import org.live.live.service.LiveRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 移动端访问，关于直播间的接口
 * Created by wang on 2017/4/15.
 */
@Controller
@RequestMapping("/app")
public class AppLiveRoomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLiveRoomController.class);

    @Resource
    private LiveCategoryService categoryService;

    @Resource
    private LiveRoomService liveRoomService;

    @Resource
    private AnchorService anchorService;

    @Resource
    private AnchorLimitationService anchorLimitationService;

    @Resource
    private UploadFilePathConfig pathConfig;

    /**
     * 获取直播分类
     *
     * @return
     */
    @RequestMapping("/category")
    @ResponseBody
    public ResponseModel<Object> findLiveCategorys() {

        ResponseModel<Object> model = new SimpleResponseModel<Object>();
        try {
            List<LiveCategoryVo> voList = categoryService.findLiveCategory4app();
            model.setData(voList);
            model.setMessage("查询成功");
            model.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.setMessage("查询失败");
        }
        return model;
    }


    /**
     * 1.当分类id为空时，就查询全部
     * 查询未禁播直播间的信息,
     *
     * @return
     */
    @RequestMapping(value = "/liveroom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findLiveRooms(String categoryId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            List<AppLiveRoomVo> voList = liveRoomService.findLiveRoomsForApp(categoryId);
            model.setData(voList);
            model.success();
        } catch (Exception e) {
            LOGGER.error("移动端查询异常", e);
            model.error("服务器繁忙！");
        }
        return model;
    }

    /**
     * 查询用户关注的直播间
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/liveroom", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findAttentionLiveRoomsByUserId(String userId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            List<AppLiveRoomVo> liveRoomVos = liveRoomService.findAttentionLiveRoomsForUser(userId);
            model.setData(liveRoomVos);
            model.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error("服务器繁忙！");
        }
        return model;
    }

    /**
     * 搜索直播间
     *
     * @param searchStr 搜索条件
     * @return
     */
    @RequestMapping(value = "/liveroom/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> searchLiveRooms(String searchStr) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            if (searchStr == null) {
                searchStr = "";
            }
            List<AppLiveRoomVo> liveRoomVos = liveRoomService.findLiveRoomsForAppSearch(searchStr);
            model.setData(liveRoomVos);
            model.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error("服务器繁忙！");
        }
        return model;
    }

    /**
     * 更改直播间的封面
     *
     * @param file
     * @param liveRoomId 直播间id
     * @return
     */
    @RequestMapping(value = "/liveroom/cover/{liveRoomId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateLiveRoomCover(MultipartFile file, @PathVariable String liveRoomId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            if (file == null) return model.error();
            LiveRoom liveRoom = liveRoomService.get(liveRoomId);
            if (liveRoom == null) return model.error();
            String coverUrl = liveRoom.getCoverUrl();
            if (!Constants.DEFAULT_HEAD_IMG_URL.equals(coverUrl)) { //不等于默认封面
                File oldFile = new File(pathConfig.getUploadFileRootPath(), coverUrl);
                if (oldFile.exists()) oldFile.delete(); //删除之前的封面
            }
            String fileSuffix = UploadUtils.getFileSuffix(file.getOriginalFilename()); //文件后缀

            //路径： 相对于项目的 /projectDir/upload/系统日期/系统时间+6位随机数.xxx
            String dateStr = CreateOrderNoUtils.getDate();
            String fileName = CreateOrderNoUtils.getCreateOrderNo() + fileSuffix;

            String targetPathSuffix = dateStr + File.separator + fileName;

            File targetFile = UploadUtils.createFile(pathConfig.getUploadFilePath(), targetPathSuffix);
            file.transferTo(targetFile);
            liveRoom.setCoverUrl(pathConfig.getUploadFilePathPrefix() + "/" + dateStr + "/" + fileName);
            liveRoomService.save(liveRoom);
            model.setData(liveRoom.getCoverUrl());
            model.success("设置成功！");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error();
        }
        return model;
    }

    /**
     * 修改直播间的信息
     *
     * @param liveRoomId  直播间id
     * @param roomName    直播间名
     * @param description 个性签名
     * @return
     */
    @RequestMapping(value = "/liveroom/{liveRoomId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateLiveRooms(@PathVariable String liveRoomId, String roomName, String description) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            LiveRoom liveRoom = liveRoomService.get(liveRoomId);
            if (liveRoom == null) return model.error();
            if (roomName != null) {  //修改房间名
                liveRoom.setRoomName(roomName);
                liveRoomService.save(liveRoom);
                return model.success();
            }
            if (description != null) {
                Anchor anchor = liveRoom.getAnchor();
                if (anchor == null) return model.error();
                anchor.setDescription(description);
                anchorService.save(anchor);
                return model.success();
            }
            model.error();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error();
        }
        return model;
    }

    /**
     * 查询直播间的限制
     *
     * @param userId
     * @param liveRoomId
     * @return
     */
    @RequestMapping(value = "/liveroom/limit", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findLiveRoomLimited(String userId, String liveRoomId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            List<Integer> limitations = anchorLimitationService.findLimitations(userId, liveRoomId);
            model.setData(limitations);
            model.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error();
        }
        return model;
    }

    /**
     * 查询某个直播间的所有限制用户
     *
     * @param liveRoomId
     * @return
     */
    @RequestMapping(value = "/liveroom/limits", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findLimitatioinForLiveRoom(String liveRoomId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            List<AnchorLimitation> limitationList = anchorLimitationService.findLimitationsForLiveRoom(liveRoomId);
            model.setData(limitationList);
            model.success();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            model.error("查询失败！");
        }
        return model;
    }


    /**
     * 根据主播id查询直播间限制，是否被禁播
     *
     * @param anchorId
     * @return
     */
    @RequestMapping(value = "/liveroom/ban", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findLiveRoomByAnchor(String anchorId) {
        ResponseModel<Object> model = new SimpleResponseModel<>();
        try {
            Anchor anchor = new Anchor();
            anchor.setId(anchorId);
            LiveRoom liveRoomByAnchor = liveRoomService.findLiveRoomByAnchor(anchor);
            model.setData(liveRoomByAnchor.isBanLiveFlag()); // 返回禁播标记
            model.success();
        } catch (Exception e) {
            model.error();
            model.setMessage(e.getMessage());
        }
        return model;
    }


}
