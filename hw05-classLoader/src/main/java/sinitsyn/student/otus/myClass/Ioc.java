package sinitsyn.student.otus.myClass;

import sinitsyn.student.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static TerminatorFactory createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new Terminator());
        return (TerminatorFactory) Proxy.newProxyInstance(TerminatorFactory.class.getClassLoader(),
                new Class<?>[]{TerminatorFactory.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TerminatorFactory myClass;
        private final List<String> logging = new ArrayList<>();

        DemoInvocationHandler(TerminatorFactory myClass) {
            this.myClass = myClass;
            Method[] methods = myClass.getClass().getDeclaredMethods();
            for (Method meth : methods) {
                Arrays.stream(meth.getDeclaredAnnotations()).forEach(annotation -> {
                    if (annotation instanceof Log) {
                        logging.add(meth.getName());
                    }
                });
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Before method - " + method.getName());

            if (logging.contains(method.getName())) {
                List<String> strings = Arrays.stream(args).map(String::valueOf).collect(Collectors.toList());
                System.out.printf("executed method : %s , params : %s%n", method.getName(),
                        String.join(", ", strings));
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
