package org.live;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.entity.DictionaryVo;
import org.live.dictionary.repository.DictRepository;
import org.live.dictionary.repository.DictTypeRepository;
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
    private DictTypeRepository dictTypeRepository;
    @Autowired
    private DictRepository dictRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void save() {
        for (int i = 0; i < 20; i++) {
            DictType dictType = new DictType();
            dictType.setTypeName("type" + i);
            dictType.setDictTypeMark("mark" + i);
            dictType.setDescription("description" + i);
            dictTypeRepository.save(dictType);
        }

        System.out.println("count:" + dictTypeRepository.count());
    }

    @Test
    public void findPage() {
        String search = "INFO";
        String[] names = {"typeName", "dictMark", "dictValue", "remarks"};
        Sort sort = new Sort(Sort.Direction.ASC, "dt.typeName");
        PageRequest pageRequest = new PageRequest(0, 5, sort); // 映射请求分页请求参数
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (String name : names) {
            map.put(name, search);
        }
        Page<DictionaryVo> page = dictRepository.find(pageRequest, map);
        List<DictionaryVo> data = page.getContent();
        Long length = page.getTotalElements();
        System.out.println(data.size() + ":" + length);
        for (DictionaryVo d : data) {
            System.out.println(d.getTypeName());
        }
    }
}
