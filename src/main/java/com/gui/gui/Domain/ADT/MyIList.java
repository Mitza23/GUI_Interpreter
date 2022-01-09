package com.gui.gui.Domain.ADT;

import com.gui.gui.Domain.Clonable;

import java.util.List;

public interface MyIList<T> extends Clonable<MyIList<T>> {
    void add(T v);
    T remove(int index);
    List<T> getList();
    int size();
}
