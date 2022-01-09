package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.Value;

public class VarDeclStmt  implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        typ = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        if (symtbl.isDefined(name)) {
            throw new MyException("Symbol already defined");
        }
        symtbl.put(name, typ.getDefault());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, typ);
        return typeEnv;

    }

    @Override
    public String toString() {
        return "" + typ + " " + name + ";";
    }

    public IStmt clone() {
        return new VarDeclStmt(name, typ);
    }
}
