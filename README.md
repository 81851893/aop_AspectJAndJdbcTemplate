# aop_AspectJAndJdbcTemplate
SpringAop框架AspectJ:包含AspectJ的使用,相关术语,5种通知以及Spring中的JdbcTemplate
### 第十六章 AOP框架AspectJ

#### 16.1 AspectJ概述

- AspectJ是Java社区里最完整最流行的**AOP框架**。
- 在Spring2.0以上版本中，可以使用基于AspectJ注解或基于XML配置的AOP。

#### 16.2 AspectJ使用步骤【重点】

- 导入jar包

  ```xml
  <!--spirng-aspects的jar包-->
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>5.3.1</version>
  </dependency>
  ```

- 编写spring配置文件【applicationContext.xml】

  - 开启组件扫描
  - 开启AspectJ注解支持

  ```xml
  <!--    - 开启组件扫描-->
  <context:component-scan base-package="com.atguigu.aopAspectJ"></context:component-scan>
  <!--    - 开启AspectJ注解支持-->
  <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
  ```

- 在【非核心业务】类中添加注解

  - 类上面添加【切面类】
    - @Component：标识当前类是一个普通组件
    - @Aspect：标识当前类是一个**切面类**
  - 方法上添加【通知】
    - @Before(value = "execution(public int com.atguigu.spring.aop.Calculator.add(int , int ))")

  ```java
  @Component   //标识当前类是一个普通组件
  @Aspect     //标识当前类是一个**切面类**
  public class Mylogging {
  
      /**
       * 执行方法前代码【前置通知：在【目标方法】执行之前执行当前方法】
       */
      @Before(value = "execution(public int com.atguigu.aopAspectJ.CalcImpl.add(int, int))")
      public void methodBefore(JoinPoint joinPoint){
          //获取方法名称
          String methodName = joinPoint.getSignature().getName();
          //获取参数
          Object[] args = joinPoint.getArgs();
          System.out.println("==>执行"+methodName+"方法，参数："+ Arrays.toString(args));
      }
      
      
  }
  ```

- 总结

  - $开头对象，是代理对象【com.sun.proxy.$Proxy13】

#### 16.3 AOP概述

- AOP(Aspect-Oriented Programming，面向切面编程)
  - AOP是OOP(Object-Oriented Programming，面向对象编程)的补充|扩展
- AOP优势
  - 先横向提取，再动态织入
  - 解决了代码分散及代码混乱问题
  - 提高代码可读性及扩展性

#### 16.4 AOP相关术语

- 横切关注点
  - 非核心业务【如：日志功能对于计算器而言属于，非核心业务】
  - 提取之前，称之为：横切关注点
- **切面(Aspect)**
  - 封装【非核心业务】的类，称之为**切面类**
- **通知(Advice)**
  - 非核心业务【如：日志功能对于计算器而言属于，非核心业务】
  - 提取之后，称之为：通知
- 目标(Target)
  - 被代理的对象，称之为**目标对象**
  - 如：CalcImpl【需要被帮助的对象，叫目标对象】
- 代理(Proxy)
  - 可以代理目标对象的，称之为**代理对象**
  - 如：com.sun.proxy**.$Proxy6**
- 连接点(Joinpoint)
  - 横切关注点在程序代码中的**具体位置**【被通知位置】，称之为连接点【通知之前】
  - 如：被**通知之前**，之歌位置称之为**连接点**
- **切入点(Pointcut)**
  - 横切关注点在程序代码中的**具体位置**【被通知位置】，称之为切入点【通知之后】
  - 如：被**通知之后**，之歌位置称之为**切入点**

#### 16.5 AspectJ中切入点表达式

- 语法：execution(权限修饰符  返回值类型   全类名.方法名(参数类型))

  - eg：
    - execution(public int com.atguigu.aopAspectJ.CalcImpl.add(int, int))
    - execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))

- 切入点表达式中的通配符

  【*】

  - 可以代表任意权限修饰符&返回值类型	
  - 可以代表任意包及类名称
  - 可以代表任意方法名称

  【..】

  - 可以代表任意参数类型

- 重用切入点表达式

  - 语法：@Pointcut()

    ```java
    /**
     * 定义可重用的切入点表达式
     */
    @Pointcut(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))")
    public void myPointCut(){}
    ```

  - 重用

    ```java
    /**
     * 执行方法前代码【前置通知：在【目标方法】执行之前执行当前方法】
     */
    @Before(value = "myPointCut()")
    /**
     * 后置通知【执行方法后代码】
     */
    @After("myPointCut()")
    ```

#### 16.6 AspectJ中JoinPoint对象

- 连接点对象【JoinPoint】
- 常用API
  - Signature st = joinPoint.getSignature()：获取方法签名
    - 方法签名：方法名+参数列表
    - st.getName()：获取方法名称
  - Object[] args = joinPoint.getArgs(); 获取方法中参数

#### 16.7 切面优先级

- 语法：@Order(int index)

  - index建议使用正整数
  - 数值越小优先级越高
  - 默认值：default  2147483647

- 示例代码

  ```java
  @Component   //标识当前类是一个普通组件
  @Aspect     //标识当前类是一个**切面类**
  @Order(value = 1)
  public class Mylogging {}
  
  @Component
  @Aspect
  @Order(2)
  public class MyValidate {}
  ```

#### 16.8 基于XML配置AOP框架AspectJ

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置计算器实现类-->
    <bean id="calculator" class="com.atguigu.spring.aop.xml.CalculatorImpl"></bean>

    <!--配置切面类-->
    <bean id="loggingAspect" class="com.atguigu.spring.aop.xml.LoggingAspect"></bean>

    <!--AOP配置-->
    <aop:config>
        <!--配置切入点表达式-->
        <aop:pointcut id="pointCut"
                      expression="execution(* com.atguigu.spring.aop.xml.Calculator.*(..))"/>
        <!--配置切面-->
        <aop:aspect ref="loggingAspect">
            <!--前置通知-->
            <aop:before method="beforeAdvice" pointcut-ref="pointCut"></aop:before>
            <!--返回通知-->
            <aop:after-returning method="returningAdvice" pointcut-ref="pointCut" returning="result"></aop:after-returning>
            <!--异常通知-->
            <aop:after-throwing method="throwingAdvice" pointcut-ref="pointCut" throwing="e"></aop:after-throwing>
            <!--后置通知-->
            <aop:after method="afterAdvice" pointcut-ref="pointCut"></aop:after>
            <!--环绕通知-->
            <aop:around method="aroundAdvice" pointcut-ref="pointCut"></aop:around>
        </aop:aspect>
    </aop:config>
    
</beans>
```





### 第十七章 Aspect中五种通知【重要】

#### 17.1 前置通知

- 语法：@Before()

- 执行时机：在目标方法**执行之前**执行标识方法

- 注意事项：目标方法中存在异常【执行】

- 示例代码

  ```java
  /**
   * 执行方法前代码【前置通知：在【目标方法】执行之前执行当前方法】
   */
  @Before(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))")
  public void methodBefore(JoinPoint joinPoint){
      //获取方法名称
      String methodName = joinPoint.getSignature().getName();
      //获取参数
      Object[] args = joinPoint.getArgs();
      System.out.println("==>前置通知！执行"+methodName+"方法，参数："+ Arrays.toString(args));
  }
  ```

#### 17.2 后置通知

- 语法：@After()

- 执行时机：在目标方法**执行之后**执行标识方法【执行所有通知之后】

- 注意事项：目标方法中存在异常【执行】

- 示例代码

  ```java
  /**
   * 后置通知【执行方法后代码】
   */
  @After(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))")
  public void methodAfter(JoinPoint joinPoint) {
      String methodName = joinPoint.getSignature().getName();
      Object[] args = joinPoint.getArgs();
      System.out.println("==>后置通知！执行"+methodName+"方法，参数："+ Arrays.toString(args));
  }
  ```

#### 17.3 返回通知

- 语法：@AfterReturning(value="切入点表达式"，returning="rs")

- 执行时机：在目标方法**返回结果**后执行标识方法

- 注意事项：

  - 目标方法中存在异常【不执行】
  - 返回通知要求returning的**属性值与入参的参数名**一致

- 示例代码

  ```java
  /**
   * 返回通知
   */
  @AfterReturning(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))",returning = "rs")
  public void afterReturning(JoinPoint joinPoint,Object rs){
      String methodName = joinPoint.getSignature().getName();
      System.out.println("==>返回通知！执行"+methodName+"方法，结果："+rs);
  }
  ```

#### 17.4 异常通知

- 语法：@AfterThrowing(value="切入点表达式"，throwing="ex")

- 执行时机：在目标方法**出现异常**后执行标识方法

- 注意事项

  - 目标方法存在异常执行，不存在异常不执行
  - 异常通知要求：throwing中属性名与入参参数名一致

- 示例代码

  ```java
  /**
   * 异常通知
   */
  @AfterThrowing(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))" ,throwing = "ex")
  public void afterThrowing(JoinPoint joinPoint,Exception ex){
      String methodName = joinPoint.getSignature().getName();
      System.out.println("==>异常通知！执行"+methodName+"方法，出现异常："+ex);
  }
  ```

- 小结

  - 目标方法存在异常：前置通知->异常通知->后置通知
  - 目标方法不存在异常：前置通知->返回通知->后置通知

#### 17.5 环绕通知

> 环绕通知：是前面四个通知结合

- 语法：@Around

- 注意事项

  - 使用环绕通知，参数中必须应用ProceedingJoinpoint对象
    - ProceedingJoinpoint是JoinPoint子类
    - ProceedingJoinpoint.proceed()可以调用目标对象的相应方法【加减乘除方法】
  - 使用环绕通知，必须为方法设置返回值类型，否则报错

- 示例代码

  ```java
  /**
   * 环绕通知
   */
  @Around(value = "execution(* com.atguigu.aopAspectJ.CalcImpl.*(..))")
  public Object around(ProceedingJoinPoint pjp){
      Object rs = null;
      //获取方法名称
      String methodName = pjp.getSignature().getName();
      //获取参数
      Object[] args = pjp.getArgs();
      try {
          //前置通知
          System.out.println("==>前置通知！执行"+methodName+"方法，参数："+ Arrays.toString(args));
          //触发目标对象的相应方法【加减乘除方法】
          rs = pjp.proceed();
          //返回通知
          System.out.println("==>返回通知！执行"+methodName+"方法，结果："+rs);
      } catch (Throwable throwable) {
          throwable.printStackTrace();
          //异常通知
          System.out.println("==>异常通知！执行"+methodName+"方法，出现异常："+throwable);
      } finally {
          //后置通知
          System.out.println("==>后置通知！执行"+methodName+"方法，参数："+ Arrays.toString(args));
      }
      return rs;
  }
  ```



### 第十八章 Spring中JdbcTemplate

#### 18.1 JdbcTemplate概述

- JdbcTemplate是一个持久层框架
  - Mybatis是一个半自动化持久化层ORM框架

#### 18.2 JdbcTemplate使用步骤

- 导入jar包

  ```xml
  <!--spring-context-->
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.1</version>
  </dependency>
  <!--spring-jdbc-->
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.3.1</version>
  </dependency>
  <!--spring-orm-->
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-orm</artifactId>
      <version>5.3.1</version>
  </dependency>
  <!--导入druid的jar包-->
  <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.10</version>
  </dependency>
  <!--导入mysql的jar包-->
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.37</version>
  </dependency>
  <!--junit-->
  <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
  </dependency>
  ```

- 编写配置文件

  - 加载外部属性文件
  - 配置DataSource数据源
  - 装配JdbcTemplate核心类库

  ```xml
  <!--    加载外部属性文件-->
  <context:property-placeholder location="classpath:db.properties"></context:property-placeholder>
  
  <!--    配置DataSource数据源-->
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
      <property name="driverClassName" value="${db.driverClassName}"></property>
      <property name="url" value="${db.url}"></property>
      <property name="username" value="${db.username}"></property>
      <property name="password" value="${db.password}"></property>
  </bean>
  
  <!--    装配JdbcTemplate核心类库-->
  <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
      <property name="dataSource" ref="dataSource"></property>
  </bean>
  ```

- 使用核心类库【JdbcTemplate】

  ```java
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.test.context.ContextConfiguration;
  import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
  
  /**
   * @author Chunsheng Zhang 尚硅谷
   * @create 2022/5/16 16:30
   */
  @ContextConfiguration(locations = "classpath:applicationContext_jdbcTemplate.xml")
  @RunWith(SpringJUnit4ClassRunner.class)
  public class TestJdbcTemplate {
  
      @Autowired
      private JdbcTemplate jdbcTemplate;
  
      @Test
      public void testJdbcTemplate(){
          System.out.println("jdbcTemplate = " + jdbcTemplate);
          //测试CRUD
          //添加员工信息【JdbcTemplate自动提交事务】
          String sql = "insert into tbl_dept(dept_id,dept_name) values(?,?)";
          jdbcTemplate.update(sql,4,"程序员鼓励师！");
  
      }
  }
  ```
