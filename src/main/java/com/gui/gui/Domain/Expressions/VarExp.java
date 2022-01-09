package com.gui.gui.Domain.Expressions;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String v) {
        id = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        if (tbl.isDefined(id))
            return tbl.lookup(id);
        else
            throw new MyException("Variable " + id + " not defined");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Exp clone() {
        return new VarExp(id);
    }
}
