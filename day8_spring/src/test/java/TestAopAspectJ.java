import com.atguigu.aopAspectJ.Calc;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAopAspectJ {
    @Test
    public void testAopAspectJ(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext_aspectj.xml");
        Calc calc = context.getBean("calcImpl", Calc.class);
        System.out.println(calc.getClass().getName());
        int add = calc.add(1, 2);
        System.out.println("======================");
        int sub = calc.sub(2, 1);
    }
}
