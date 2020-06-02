import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @ClassName : MyTest
 * @Author : zhangjiacheng
 * @Date : 2020/5/25
 * @Description : TODO
 */


public class MyTest {

    @Test
    public void test() throws Exception {
        UserController userController = new UserController();
        Class clazz = userController.getClass();
        UserService userService = new UserService();
        //获取类中的属性值 hello
        Field serviceField = clazz.getDeclaredField("userService");
        //在访问的时候如果是私有的访问类型，也可以访问
        serviceField.setAccessible(true);
        //获取属性名称
        String name = serviceField.getName();
        name = name.substring(0,1).toUpperCase()+ name.substring(1,name.length());
        String methodName = "set" + name;
        //获取方法的对象
        Method method = clazz.getMethod(methodName, UserService.class);
        //执行set方法
        method.invoke(userController,userService);
        userController.getUserService().user();
    }

    @Test
    public void test1() {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            //是否添加了注解
            AutoWiredTest annotation = field.getAnnotation(AutoWiredTest.class);
            if (annotation != null) {
                Class<?> type = field.getType();
                try {
                    Object o = type.newInstance();
                    field.set(userController, o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        userController.test();
    }
}
