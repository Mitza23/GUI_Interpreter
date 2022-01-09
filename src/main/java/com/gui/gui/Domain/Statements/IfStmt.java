package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.BoolType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.BoolValue;
import com.gui.gui.Domain.Values.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}

    @Override
    public String toString() {
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    public PrgState execute(PrgState state) throws MyException {
        Value v= exp.eval(state.getSymTable(), state.getHeap());
        if(v instanceof BoolValue){
            boolean cond = ((BoolValue) v).getVal();
            if (cond) {
                state.getStk().push(thenS);
            } else {
                state.getStk().push(elseS);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        } else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStmt clone() {
        return new IfStmt(exp.clone(), thenS.clone(), elseS.clone());
    }
}
