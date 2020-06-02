/**
 * @ClassName : UserController
 * @Author : zhangjiacheng
 * @Date : 2020/5/25
 * @Description : TODO
 */
public class UserController {

    @AutoWiredTest
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void test(){
        userService.user();
    }
}
