package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIBarrierTable;
import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.ADT.MyIStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.IntType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.IntValue;
import com.gui.gui.Domain.Values.Value;

public class AwaitBarrierStmt implements IStmt{

    String var;

    public AwaitBarrierStmt(String var) {
        this.var = var;
    }

    @Override
    public IStmt clone() {
        return new AwaitBarrierStmt(var);
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        MyIHeap heap = state.getHeap();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        if(symtbl.isDefined(var)){
            if(symtbl.lookup(var).getType().equals(new IntType())){
                int key = ((IntValue)symtbl.lookup(var)).getVal();
                barrierTable.addEntry(key, state.getId());
                if(!barrierTable.barrierFull(key)){
                    stack.push(this.clone());
                }
            }
            else {
                throw new MyException("Variable " + var + " must be an integer value");
            }
        }
        else {
            throw new MyException("Variable " + var + " is not defined");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = typeEnv.lookup(var);
        if(type.equals(new IntType())){
            return typeEnv;
        }
        else{
            throw new MyException("Variable " + var + " must be an integer value");
        }
    }

    @Override
    public String toString() {
        return "awaitBarrierStmt(" + var + ')';
    }
}
