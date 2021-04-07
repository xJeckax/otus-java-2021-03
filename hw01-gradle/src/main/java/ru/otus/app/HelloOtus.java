package ru.otus.app;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<String> yes = new ArrayList<>();
        yes.add("Sinitsyn Evgeny");
        yes.add("|\\/|");
        yes.add("student");
        System.out.println(Lists.reverse(yes));
    }
}