package ru.otus.processor;

import ru.otus.model.Message;

import java.util.Date;

public class ExceptionProcessor implements Processor {
    @Override
    public Message process(Message message) {
        Date date = new Date();
        long second = date.getTime() / 1000;
        if ((second % 2) == 0) {
            throw new RuntimeException(String.format("Even second - %s", second));
        }
        return message;
    }
}