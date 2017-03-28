package org.live;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.live.module.demo.entity.Demo;
import org.live.module.demo.entity.DemoVo;
import org.live.module.demo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveServerApplicationTests {
    @Autowired
    private DemoRepository demoRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void save() {
        for (int i = 0; i < 20; i++) {
            Demo demo = new Demo();
            demo.setName("kam" + i);
            demo.setAge("25" + i);
            demo.setUsername("user" + i);
            demo.setAddress("英国伦敦" + i);
            demo.setOther("其他数据" + i);
            demoRepository.save(demo);
        }
        System.out.println("count:" + demoRepository.count());
    }

    @Test
    public void findPage() {
        String search = "8";
        String[] names = {"name", "age", "username", "address", "other"};
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        PageRequest pageRequest = new PageRequest(0, 5, sort); // 映射请求分页请求参数
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (String name : names) {
            map.put(name, search);
        }
        Page<DemoVo> page = demoRepository.find(pageRequest, map);
        List<DemoVo> data = page.getContent();
        Long length = page.getTotalElements();
        System.out.println(data.size() + ":" + length);
        for (DemoVo dv : data) {
            System.out.println(dv.getName());
        }
    }
}
