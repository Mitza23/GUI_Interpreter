package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.Exp;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.IntType;
import com.gui.gui.Domain.Types.StringType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.IntValue;
import com.gui.gui.Domain.Values.StringValue;
import com.gui.gui.Domain.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    Exp exp;
    String var_name;

    public ReadFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, BufferedReader> fileTbl = state.getFileTable();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if(symTbl.isDefined(var_name)){
            if(symTbl.lookup(var_name).getType().equals(new IntType())){
                if(exp.eval(symTbl, state.getHeap()) instanceof StringValue){
                    String name = ((StringValue) exp.eval(symTbl, state.getHeap())).getVal();
                    if(!fileTbl.isDefined(name))
                        throw new MyException("File not opened");
                    BufferedReader buff = fileTbl.lookup(name);
                    try {
                        String txt = buff.readLine();
                        int val;
                        if(txt == null){
                            val = 0;
                        }
                        else{
                            val = Integer.parseInt(txt);
                        }
                        symTbl.update(var_name, new IntValue(val));
                    }
                    catch (IOException e){
                        throw new MyException(e.toString());
                    }
                }
                else{
                    throw new MyException("File name must be a string");
                }
            } else {
                throw new MyException(var_name + "does not store an integer value");
            }
        } else {
            throw new MyException(var_name + "is not defined");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(var_name);
        Type expType = exp.typecheck(typeEnv);
        if (varType.equals(new IntType())) {
            if (expType.equals(new StringType())) {
                return typeEnv;
            } else {
                throw new MyException("Expression does not evaluate to a String: " + expType);
            }
        } else {
            throw new MyException("Variable is not a Integer: " + varType);
        }
    }

    @Override
    public IStmt clone() {
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + var_name + ')';
    }
}
