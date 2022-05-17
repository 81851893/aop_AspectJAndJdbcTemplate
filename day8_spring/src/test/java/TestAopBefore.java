import com.atguigu.aopbefore.CalcImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAopBefore {
    @Test
    public void testAopBefore(){
//        1.创建容器对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_aopbefore.xml");
        CalcImpl calcImpl = context.getBean("calcImpl", CalcImpl.class);
        calcImpl.add(1,2);
    }
}
