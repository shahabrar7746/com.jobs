package com.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Main {
    @Autowired
    private static helper help1;
    private static  helper help2;

    public static void main(String[] args) {
        System.out.println(help1 == help2);
        System.out.println(help1);
    }
}
