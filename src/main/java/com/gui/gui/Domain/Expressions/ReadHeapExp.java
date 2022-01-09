package com.gui.gui.Domain.Expressions;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Types.RefType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.RefValue;
import com.gui.gui.Domain.Values.Value;

public class ReadHeapExp implements Exp {
    Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Exp clone() {
        return new ReadHeapExp(exp.clone());
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        if (exp.eval(tbl, heap) instanceof RefValue) {
            int addr = ((RefValue) exp.eval(tbl, heap)).getAddr();
            Value v = heap.getValue(addr);
            if (v == null) {
                throw new MyException("Address " + addr + " does not exist in the heap");
            }
            return v;
        } else
            throw new MyException("Expression is not a RefValue");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else {
            throw new MyException("the rH argument is not a Ref Type");
        }
    }

    @Override
    public String toString() {
        return "ReadHeapExp(" + exp + ')';
    }
}
