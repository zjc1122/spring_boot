import com.zjc.service.IUserServer;
import com.zjc.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OauthResourceTests.class)
@Slf4j
@ComponentScan(basePackages = "com.zjc")
public class OauthResourceTests {

    @Resource
    private IUserServer userServer;


    @Test
    public void send() {
        List<User> all = userServer.findAll();
        System.out.println(1);
    }

}
