package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.ExceptionProcessor;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.Processor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.SwapFieldProcessor;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        List<Processor> processors = List.of(new ExceptionProcessor(LocalDateTime::now), new LoggerProcessor(new ProcessorConcatFields()), new LoggerProcessor(new ProcessorUpperField10()),
                new LoggerProcessor(new SwapFieldProcessor()), new ExceptionProcessor(LocalDateTime::now));

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, e -> System.err.println(e));

        ListenerPrinterConsole listenerPrinter = new ListenerPrinterConsole();
        HistoryListener historyListener = new HistoryListener();
        complexProcessor.addListener(listenerPrinter);
        complexProcessor.addListener(historyListener);

        List<String> strings = List.of("DEesda", "sadsA", "Udjlksa", "asdjkjadkj");
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(strings);
        var message = new Message.Builder(1L)
                .field1("FFFF")
                .field2("AAAA")
                .field3("WWWW")
                .field4("VVVV")
                .field5("NNNN")
                .field6("GGGG")
                .field7("LLLL")
                .field8("EEEE")
                .field9("QQQQ")
                .field10("cccc")
                .field11("MMMM")
                .field12("PPPP")
                .field13(objectForMessage)
                .build();


        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
        complexProcessor.removeListener(historyListener);
    }
}
