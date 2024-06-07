package com.github.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponentA {

    private  MyComponentB componentB;

    public void sayHello(){
        String message = componentB.sayHello() + ", 그리고 난 MyComponentA";
        System.out.println(message);
    }

    public MyComponentA(MyComponentB componentB) {
        this.componentB = componentB;
    }

    @Autowired
    public void setComponentB(MyComponentB componentB) {
        this.componentB = componentB;
    }
}
