package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.StringType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.StringValue;
import com.gui.gui.Domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    Exp exp;

    public CloseRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public IStmt clone() {
        return new CloseRFileStmt(exp.clone());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(!(exp.eval(symTbl, state.getHeap()) instanceof StringValue)){
            throw new MyException("The expression must evaluate to a string");
        }
        else{
            String fileName = ((StringValue) exp.eval(symTbl, state.getHeap())).getVal();
            if(!fileTbl.isDefined(fileName)){
                throw new MyException("File is not defined");
            }
            else{
                BufferedReader buff = fileTbl.lookup(fileName);
                try {
                    buff.close();
                } catch (IOException e) {
                    throw new MyException("Error closing the file");
                }
                fileTbl.remove(fileName);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typecheck(typeEnv);
        if (expType.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("Expression does not evaluate to a string: " + expType);
        }
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ')';
    }
}
