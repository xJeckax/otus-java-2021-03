package sinitsyn.student.otus.myClass;

public class AppStart {
    public static void main(String[] args) throws NoSuchMethodException {
        TerminatorFactory newTerminator = Ioc.createMyClass();
        newTerminator.getQuote("I'll be back");
    }
}
