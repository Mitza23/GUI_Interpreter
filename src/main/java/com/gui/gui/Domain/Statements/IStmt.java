package com.gui.gui.Domain.Statements;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Types.Type;

public interface IStmt extends Clonable<IStmt> {
    public PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement.

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
