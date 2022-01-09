package com.gui.gui.Domain.Expressions;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value v) {
        e = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp clone() {
        return new ValueExp(e.clone());
    }
}
