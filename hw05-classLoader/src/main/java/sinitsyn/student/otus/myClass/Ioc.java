package sinitsyn.student.otus.myClass;

import sinitsyn.student.otus.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

        DemoInvocationHandler(TerminatorFactory myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method[] methods = myClass.getClass().getMethods();
            List<String> strings = Arrays.stream(args).map(String::valueOf).collect(Collectors.toList());
            System.out.println("Before method - " + method.getName());
            for (Method meth : methods) {
                if (meth.getName().equals(method.getName())) {
                    for (Annotation ann : meth.getDeclaredAnnotations()) {
                        if (ann instanceof Log) {
                            System.out.printf("executed method : %s , params ; %s%n", method.getName(),
                                    String.join(", ", String.join(", ", strings)));
                        }
                    }
                }
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
