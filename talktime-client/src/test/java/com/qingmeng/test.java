package com.qingmeng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月08日 14:27:00
 */
@SpringBootTest(classes = TalkTimeClientApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class test {
    @Resource
    public TestSer testSer;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
//            testAsync.doAsync();
            testSer.visibleAsync();
        }
    }
}
