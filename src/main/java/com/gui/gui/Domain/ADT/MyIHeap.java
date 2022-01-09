package com.gui.gui.Domain.ADT;

import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Values.Value;

import java.util.Map;

public interface MyIHeap extends Clonable<MyIHeap> {
    int nextFree();

    int addEntry(Value value);

    Value getValue(int address);

    boolean isDefined(int address);

    void update(int address, Value value);

    void setContent(Map<Integer, Value> map);

    Map<Integer, Value> getContent();
}
