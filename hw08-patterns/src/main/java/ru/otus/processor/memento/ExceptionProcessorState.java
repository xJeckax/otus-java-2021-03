package ru.otus.processor.memento;

import java.time.LocalDateTime;

public class ExceptionProcessorState {
    private final LocalDateTime localDateTime;

    public ExceptionProcessorState(DateTimeProvider date) {
        this.localDateTime = date.getDate();
    }

    public ExceptionProcessorState(ExceptionProcessorState state) {
        this.localDateTime = state.getLocalDateTime();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
