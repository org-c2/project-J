package dcnet.tutorial.c01.business.service;

import org.springframework.stereotype.Service;

import dcnet.tutorial.c01.business.object.Status;
import dcnet.tutorial.c01.business.object.UserRegister;

@Service
public class UserRegisterService {

    /**
     * 新用户注册
     * @param email 邮箱
     * @param password 密码
     * @return 注册结果
     */
    public UserRegister regist(String email, String password) {

        UserRegister result = new UserRegister();
        if (email == null || email.isEmpty()) {
            result.setStatus(Status.ILLEGALARGS);
            return result;
        }
        if (password == null || password.isEmpty()) {
            result.setStatus(Status.ILLEGALARGS);
            return result;
        }
        if (email.length() < 11 || email.length() > 30) {
            result.setStatus(Status.ILLEGALARGS);
            return result;
        }
        if (password.length() < 8 || password.length() > 30) {
            result.setStatus(Status.ILLEGALARGS);
            return result;
        }
        // 这里应该是要从数据库查询的，作为示例代码直接写死
        if ("test@gmail.com".equals(email)) {
            result.setStatus(Status.DUPLICATE);
            return result;
        }
        result.setStatus(Status.SUCCESSED);
        return result;
    }

}
