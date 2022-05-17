import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations = "classpath:applicationContext_jdbcTemplate.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJdbcTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testJdbcTemplate(){
        System.out.println("jdbcTemplate" + jdbcTemplate);
//        测试CRUD
        String sql = "insert into tbl_dept(dept_id,dept_name) value(?,?)";
        jdbcTemplate.update(sql,3,"程序员");
    }
}
