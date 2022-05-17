package com.atguigu.aopbefore;

import org.springframework.stereotype.Component;

@Component
public class CalcImpl implements Calc{
    @Override
    public int add(int a, int b) {
//        System.out.println("==>执行add()方法,参数a:" + a + ",b:" + b);
        int result = a + b;
//        System.out.println("==>执行add()方法,结果:" + result);
        return result;
    }

    @Override
    public int sub(int a, int b) {
        int result = a - b;
        return result;
    }

    @Override
    public int mul(int a, int b) {
        int result = a * b;
        return result;
    }

    @Override
    public int div(int a, int b) {
        int result = a / b;
        return result;
    }
}
