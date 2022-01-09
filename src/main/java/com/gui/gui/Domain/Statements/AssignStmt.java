package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIStack;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String v, Exp valueExp) {
        id = v;
        exp = valueExp;
    }

    public String toString(){
        return id + " = " + exp.toString() + ";";

    }
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();

        if(symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, state.getHeap());
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else {
                System.out.println(id + " supports " + typId);
                System.out.println(exp + " is " + val.getType());
                throw new MyException("declared type of variable " + id + " and type of the assigned expression " + exp
                        + " do not match");
            }
        } else throw new MyException("the used variable" + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types: "
                    + typevar + " != " + typexp);
    }

    @Override
    public IStmt clone() {
        return new AssignStmt(id, exp.clone());
    }
}
