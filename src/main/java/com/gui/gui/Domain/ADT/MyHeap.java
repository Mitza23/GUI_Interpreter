package com.gui.gui.Domain.ADT;

import com.gui.gui.Domain.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap implements MyIHeap{
    Map<Integer, Value> map;
    int position;

    public MyHeap() {
        map = new ConcurrentHashMap<Integer, Value>();
        this.position = 1;
    }

    public MyHeap(Map<Integer, Value> map, int position) {
        this.map = map;
        this.position = position;
    }

    @Override
    public synchronized int nextFree() {
        for (int i = 1; i <= position; i++) {
            if (!isDefined(i))
                return i;
        }
        position++;
        return position;
    }

    @Override
    public int addEntry(Value value) {
        int p = nextFree();
        map.put(p, value);
        return p;
    }

    @Override
    public Value getValue(int address) {
        return map.get(address);
    }

    @Override
    public boolean isDefined(int address) {
        return map.get(address) != null;
    }

    @Override
    public MyIHeap clone() {
        HashMap<Integer, Value> clone = new HashMap<Integer, Value>();
        for(Map.Entry<Integer, Value> entry : map.entrySet()){
            clone.put(entry.getKey(), entry.getValue().clone());
        }
        return new MyHeap(clone, position);
    }

    public void update(int address, Value value){
        map.replace(address, value);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("Heap:\n");
        for (HashMap.Entry<Integer, Value> p : map.entrySet()) {
            r.append(p.getKey().toString() + " --> " + p.getValue().toString() + "\n");
        }
        return r.toString();
    }

    @Override
    public void setContent(Map<Integer, Value> map) {
        this.map = map;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return map;
    }
}
