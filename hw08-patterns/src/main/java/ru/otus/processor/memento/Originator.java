package ru.otus.processor.memento;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class Originator {
    //Фактически, это stack
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void saveState(ExceptionProcessorState state) {
        stack.push(new Memento(state, LocalDateTime.now()));
    }

    public ExceptionProcessorState restoreState() {
        var memento = stack.pop();
        System.out.println("createdAt:" + memento.getCreatedAt());
        return memento.getState();
    }
}
