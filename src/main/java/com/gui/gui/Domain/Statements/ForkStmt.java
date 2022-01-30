package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;

public class ForkStmt implements IStmt {
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public IStmt clone() {
        return new ForkStmt(stmt.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
//        System.out.println(stmt);
        MyStack<IStmt> stack = new MyStack<IStmt>();
        stack.push(stmt);
        return new PrgState(stack, state.getSymTable().clone(), state.getOut(), state.getFileTable(), state.getHeap(), state.getBarrierTable());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "ForkStmt(" + stmt + ')';
    }
}
