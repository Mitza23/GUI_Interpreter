package com.gui.gui.Domain.ADT;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface MyIBarrierTable {
    void addEntry(int key, int id);

    boolean existsEntry(int key, int id);

    int getBarrierLength(int key);

    int getCurrentLength(int key);

    int newBarrier(int size);

    int nextFree();

    boolean barrierFull(int key);

    Map<Integer, Pair<Integer, List<Integer>>> getContent();
}
