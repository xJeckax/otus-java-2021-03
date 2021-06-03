package sinitsyn.student.otus.myClass;

import sinitsyn.student.otus.annotation.Log;

public class Terminator implements TerminatorFactory {

    private int identityNumber;

    public Terminator() {
        this.identityNumber = (int) (Math.random() * 1000);
    }

    @Override
    @Log
    public void getQuote(String quote) {
        System.out.println("Terminator say : " + quote);
    }

    @Override
    @Log
    public int getIdentityNumber() {
        return this.identityNumber;
    }
}
