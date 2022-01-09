package com.gui.gui.Domain.Expressions;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public interface Exp extends Clonable<Exp> {
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException;

    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
