package dcnet.tutorial.c01;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dcnet.tutorial.c01.business.object.Status;
import dcnet.tutorial.c01.business.object.UserRegister;
import dcnet.tutorial.c01.business.service.UserRegisterService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoServiceTest {

    Logger log = LoggerFactory.getLogger(UserInfoServiceTest.class);

    @Autowired
    private UserRegisterService userRegisterService;

    @Before
    public void beforeTest() {
        log.info("测试开始执行，一般在这里做一些前期准备，比如往数据库投入测试数据等等。");
    }

    @After
    public void afterTest() {
        log.info("测试结束之后执行，做一些清理，擦屁股的工作。");
    }

    @Test
    public void test_case_001() {
        // 执行测试对象，取得实际返回值
        UserRegister actuals = userRegisterService.regist(null, "12345678");
        // 和期待值比对
        Assert.assertEquals(Status.ILLEGALARGS, actuals.getStatus());
    }


    @Test
    public void test_case_002() {
        // 执行测试对象，取得实际返回值
        UserRegister actuals = userRegisterService.regist("0@mail.com", "12345678");
        // 和期待值比对
        Assert.assertEquals(Status.ILLEGALARGS, actuals.getStatus());
    }
}
