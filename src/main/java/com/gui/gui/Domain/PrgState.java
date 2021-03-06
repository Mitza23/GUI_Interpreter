package com.gui.gui.Domain;

import com.gui.gui.Domain.ADT.*;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Statements.IStmt;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

import java.io.BufferedReader;


public class PrgState implements Clonable<PrgState> {
    static int prgCount = 0;
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<String, BufferedReader> fileTable;
    MyIHeap heap;
    MyIBarrierTable barrierTable;
    int id;
    IStmt originalProgram; //optional field, but good to have

    public MyIHeap getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public synchronized int getAndIncrementPrgCount() {
        prgCount++;
        return prgCount;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, IStmt originalProgram, MyIBarrierTable barrierTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        this.originalProgram = originalProgram;
        this.id = getAndIncrementPrgCount();
        exeStack.push(originalProgram);
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, MyIBarrierTable barrierTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        this.id = getAndIncrementPrgCount();
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = new MyBarrierTable();
        this.originalProgram = originalProgram;
        this.id = getAndIncrementPrgCount();
        exeStack.push(originalProgram);
    }


    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = new MyBarrierTable();
        this.id = getAndIncrementPrgCount();
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot, IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram = prg;
//        originalProgram=deepCopy(prg);//recreate the entire original prg
        stk.push(prg);
    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Value> dict, MyIList<Value> list) {
        exeStack = stack;
        symTable = dict;
        out = list;
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out,
                    MyIDictionary<String, BufferedReader> fileTable, IStmt stmt) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        exeStack.push(stmt);
        this.heap = new MyHeap();
        stmt.typecheck(new MyDictionary<String, Type>());
    }

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append("ID: ")
                .append(id)
                .append("\n")
                .append(exeStack.toString())
                .append("\n")
                .append(symTable.toString())
                .append("\n")
                .append(out.toString())
                .append("\n")
                .append(fileTable.toString())
                .append("\n")
                .append(heap.toString())
                .append("\n")
                .append(barrierTable.toString());
        return r.toString();
    }


    @Override
    public PrgState clone() {
        MyIStack<IStmt> stack = exeStack.clone();
        MyIDictionary<String, Value> dict = symTable.clone();
        MyIList<Value> list = out.clone();
        PrgState clone = new PrgState(stack, dict, list);
        return clone;
    }

    public boolean isNotCompleted() {
//        System.out.println(exeStack.toString());
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
//        System.out.println(this);
        if (exeStack.isEmpty()) {
            throw new MyException("prgstate stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public MyIBarrierTable getBarrierTable() {
        return barrierTable;
    }
}
