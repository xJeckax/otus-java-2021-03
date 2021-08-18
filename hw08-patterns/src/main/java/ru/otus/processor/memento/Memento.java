package ru.otus.processor.memento;

import java.time.LocalDateTime;

class Memento {
    private final ExceptionProcessorState state;
    private final LocalDateTime createdAt;

    Memento(ExceptionProcessorState state, LocalDateTime createdAt) {
        this.state = new ExceptionProcessorState(state);
        this.createdAt = createdAt;
    }

    ExceptionProcessorState getState() {
        return state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
