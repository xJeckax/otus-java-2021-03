package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.memento.ExceptionProcessorState;

import java.time.LocalDateTime;
import java.util.Date;

public class ExceptionProcessor implements Processor {
    private LocalDateTime date;

    public ExceptionProcessor() {
        this.date = LocalDateTime.now();
    }

    @Override
    public Message process(Message message) {
        Date date = new Date();
        long second = date.getTime() / 1000;
        if ((second % 2) == 0) {
            throw new RuntimeException(String.format("Even second - %s", second));
        }
        return message;
    }

    public ExceptionProcessorState saveState() {
        return new ExceptionProcessorState(() -> date);
    }

    public void restoreState(ExceptionProcessorState state) {
        this.date = state.getLocalDateTime();
    }
}
