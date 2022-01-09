package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp v) {
        exp = v;
    }

    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public IStmt clone() {
        return new PrintStmt(exp);
    }
}
