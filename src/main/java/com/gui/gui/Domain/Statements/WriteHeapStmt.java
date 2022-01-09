package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.RefType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.RefValue;
import com.gui.gui.Domain.Values.Value;

public class WriteHeapStmt implements IStmt{

    String varName;
    Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new WriteHeapStmt(varName, exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
       if(symtbl.isDefined(varName)){
           Value v = symtbl.lookup(varName);
           if(v instanceof RefValue){
               int addr = ((RefValue) v).getAddr();
               MyIHeap heap = state.getHeap();
               if(heap.isDefined(addr)){
                   Value expEval = exp.eval(symtbl, heap);
                   if(expEval.getType().equals(((RefValue) v).getLocationType())){
                        heap.update(addr, expEval);
                        return null;
                   } else
                       throw new MyException("Incompatible types " + expEval.getType() + " and " +
                               ((RefValue) v).getLocationType());
               } else
                   throw new MyException("Address " + addr + " is not referenced");
           } else
               throw new MyException("Variable " + varName + " it's not a RefValue");
       } else
           throw new MyException("Variable " + varName + " not defined");
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(varName);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else {
            throw new MyException("WriteHeap stmt: right hand side and left hand side have different types: "
                    + typeVar + " != " + typeExp);
        }
    }

    @Override
    public String toString() {
        return "WriteHeapStmt(" +
                varName +
                " <- " + exp +
                ')';
    }
}
