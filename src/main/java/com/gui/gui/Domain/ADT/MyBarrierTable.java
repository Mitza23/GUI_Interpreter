package com.gui.gui.Domain.ADT;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBarrierTable implements MyIBarrierTable{

    static int position = 0;
    HashMap<Integer, Pair<Integer, List<Integer>>> map;

    public MyBarrierTable() {
        this.map = new HashMap<>();
    }

    @Override
    public synchronized void addEntry(int key, int id) {
        if(!existsEntry(key, id))
            map.get(key).getValue().add(id);
    }

    @Override
    public synchronized boolean existsEntry(int key, int id) {
        return map.get(key).getValue().contains(id);
    }

    @Override
    public synchronized int getBarrierLength(int key) {
        return map.get(key).getKey();
    }

    @Override
    public synchronized int getCurrentLength(int key) {
        return map.get(key).getValue().size();
    }

    @Override
    public synchronized int  newBarrier(int size) {
        int key = nextFree();
        map.put(key, new Pair<>(size, new ArrayList<>()));
        return key;
    }

    @Override
    public synchronized int nextFree() {
        int tmp = position;
        position++;
        return tmp;
    }

    @Override
    public boolean barrierFull(int key) {
        return getCurrentLength(key) == getBarrierLength(key);
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return map;
    }

    @Override
    public synchronized String toString() {
        StringBuilder r = new StringBuilder("BarrierTable:\n");
        for (HashMap.Entry<Integer, Pair<Integer, List<Integer>>> p : map.entrySet()) {
            r.append(p.getKey().toString() + " --> " + "size: " + p.getValue().getKey() + "{" + p.getValue().toString()
                    + "}\n");
        }
        String s = r.toString();
        return s;
    }
}
