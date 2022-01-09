package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt v1, IStmt v2) {
        first = v1;
        second = v2;
    }


    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = second.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStmt clone() {
        return new CompStmt(first.clone(), second.clone());
    }
}

