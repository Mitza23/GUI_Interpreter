package com.gui.gui.Domain.ADT;

import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Exceptions.MyException;

import java.util.Stack;

public interface MyIStack <T> extends Clonable<MyIStack<T>> {
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    Stack<T> getStack();
    int getSize();
}
