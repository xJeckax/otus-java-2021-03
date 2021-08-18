package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        try {
            ObjectForMessage ofm = msg.getField13().clone();
            Message message = msg.toBuilder().field13(ofm).build();
            messageMap.put(message.getId(), message);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(messageMap.get(id));
    }
}
