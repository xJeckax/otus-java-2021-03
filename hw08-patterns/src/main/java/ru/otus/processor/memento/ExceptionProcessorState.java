package ru.otus.processor.memento;

import java.util.Date;

public class ExceptionProcessorState {
    private Date date;

    public ExceptionProcessorState(Date date) {
        this.date = date;
    }

    public ExceptionProcessorState(ExceptionProcessorState state) {
        this.date = new Date(state.getDate().getTime());
    }

    public Date getDate() {
        return date;
    }
}
