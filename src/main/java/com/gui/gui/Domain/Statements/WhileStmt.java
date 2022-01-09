package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.ADT.MyIStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.BoolType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.BoolValue;
import com.gui.gui.Domain.Values.Value;

public class WhileStmt implements IStmt {
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public IStmt clone() {
        return new WhileStmt(exp.clone(), stmt.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        MyIHeap heap = state.getHeap();
        if (exp.eval(symtbl, heap).getType().equals(new BoolType())) {
            boolean ok = ((BoolValue) exp.eval(symtbl, heap)).getVal();
            if (ok) {
                stack.push(this.clone());
                stack.push(stmt);
            }
        } else
            throw new MyException(exp + " must be evaluated to a BoolValue");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        } else
            throw new MyException("The condition of WHILE has not the type bool");
    }

    @Override
    public String toString() {
        return "while(" + exp + "){ " + stmt + "}";
    }
}
