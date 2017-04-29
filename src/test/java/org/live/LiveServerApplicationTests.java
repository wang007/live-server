package org.live;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.entity.Report;
import org.live.live.repository.ReportRepository;
import org.live.live.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveServerApplicationTests {
    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void save() {
        for (int i = 0; i < 1; i++) {
            MobileUser mobileUser = new MobileUser();
            mobileUser.setId("80e9d66c-b3e0-4800-b0d6-a33b04dd10bf");
            LiveRoom liveRoom = new LiveRoom();
            liveRoom.setId("e8604259-c1c0-4c66-883a-524e2a3132f0");
            Report report = new Report();
            report.setLiveRoom(liveRoom);
            report.setUser(mobileUser);
            report.setCreateTime(new Date());
            report.setReportNum("reportNunm" + i);
            report.setHandleType(false);
            reportRepository.save(report);
        }
    }

    @Test
    public void find() {
        List<Report> reports = reportRepository.findAll();
        System.out.println(reports.size());
    }

    @Test
    public void findByPage() {
        Sort sort = new Sort(Sort.Direction.ASC, "re.createTime");
        PageRequest pageRequest = new PageRequest(0, 5, sort);
        Map<String, Object> filter = new HashMap<>();
        filter.put("roomNum", "201335020233");
        filter.put("account", "201335020233");
        filter.put("nickname", "201335020233");
        filter.put("roomName", "201335020233");
        Page<ReportVo> page = reportRepository.findReports(pageRequest, filter, false);
        List<ReportVo> reportVos = page.getContent();
        System.out.println(reportVos.get(0).getLiveRoomName());
        System.out.println(reportVos.size()); // 当前分页数据记录条目数
    }
}
