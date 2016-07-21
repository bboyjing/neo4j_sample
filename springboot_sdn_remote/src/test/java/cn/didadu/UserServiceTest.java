package cn.didadu;

import cn.didadu.sdn.entity.User;
import cn.didadu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangjing on 16-7-18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootSdnRemoteApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 因为是通过http连接到Neo4j数据库的，所以要预先启动Neo4j
     */
    @Test
    public void testInitData(){
        userService.initData();
    }

    @Test
    public void testGetUserByName(){
        User user = userService.getUserByName("John Johnson");
        System.out.println(user);
    }
}
