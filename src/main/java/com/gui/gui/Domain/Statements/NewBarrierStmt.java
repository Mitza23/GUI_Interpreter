package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIBarrierTable;
import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.IntType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.IntValue;
import com.gui.gui.Domain.Values.Value;

public class NewBarrierStmt implements IStmt{

    String var;
    Exp exp;

    public NewBarrierStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new NewBarrierStmt(var, exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyIBarrierTable barrierTable = state.getBarrierTable();
        if(exp.eval(symtbl, heap).getType().equals(new IntType())){
            int nr = ((IntValue)exp.eval(symtbl, heap)).getVal();
            int key = barrierTable.newBarrier(nr);
            if(symtbl.isDefined(var)){
                symtbl.update(var, new IntValue(key));
            }
            else {
                throw new MyException("Variable " + var + " not defined");
            }
        }
        else{
            throw new MyException("Size of the barrier must be an integer value");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        Type typeVar = typeEnv.lookup(var);
        if(typeExp.equals(new IntType()) && typeVar.equals(typeExp)){
            return typeEnv;
        }
        else{
            throw new MyException("Size of the barrier must be an integer value");
        }
    }

    @Override
    public String toString() {
        return "NewBarrier(" + var + ", " + exp + ')';
    }
}
