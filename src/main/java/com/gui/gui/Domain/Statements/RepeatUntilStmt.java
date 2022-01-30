package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.ADT.MyIStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.BoolType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public class RepeatUntilStmt implements IStmt{

    IStmt stmt;
    Exp exp;

    public RepeatUntilStmt(IStmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new RepeatUntilStmt(stmt.clone(), exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        MyIHeap heap = state.getHeap();
        if(exp.eval(symtbl, heap).getType().equals(new BoolType())){
            stack.push(new WhileNotStmt(exp.clone(), stmt.clone()));
            stack.push(stmt);
        }
        else {
            throw new MyException(exp + " must be evaluated to a BoolValue");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        } else
            throw new MyException("The condition of REPEAT UNTIL does not have the type bool");
    }

    @Override
    public String toString() {
        return "repeat{" + stmt + "} until(" + exp + ")";
    }
}
