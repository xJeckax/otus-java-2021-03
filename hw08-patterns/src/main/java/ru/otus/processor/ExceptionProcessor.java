package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.memento.DateTimeProvider;
import ru.otus.processor.memento.ExceptionProcessorState;

public class ExceptionProcessor implements Processor {
    private DateTimeProvider date;

    public ExceptionProcessor(DateTimeProvider date) {
        this.date = date;
    }

    @Override
    public Message process(Message message) {

        if ((date.getDate().getSecond() % 2) == 0) {
            throw new RuntimeException(String.format("Even second - %s", date.getDate().getSecond()));
        }
        return message;
    }

    public ExceptionProcessorState saveState() {
        return new ExceptionProcessorState(date);
    }

    public void restoreState(ExceptionProcessorState state) {
        this.date = state::getLocalDateTime;
    }
}
