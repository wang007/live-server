package org.live;

import org.junit.runner.RunWith;

import org.live.dictionary.repository.DictRepository;
import org.live.dictionary.repository.DictTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveServerApplicationTests {
    @Autowired
    private DictTypeRepository dictTypeRepository;
    @Autowired
    private DictRepository dictRepository;

}
