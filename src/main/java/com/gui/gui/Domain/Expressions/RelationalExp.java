package com.gui.gui.Domain.Expressions;

import com.gui.gui.Domain.ADT.MyIDictionary;
import com.gui.gui.Domain.ADT.MyIHeap;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Types.BoolType;
import com.gui.gui.Domain.Types.IntType;
import com.gui.gui.Domain.Types.Type;
import com.gui.gui.Domain.Values.BoolValue;
import com.gui.gui.Domain.Values.IntValue;
import com.gui.gui.Domain.Values.Value;

import java.util.Objects;

public class RelationalExp implements Exp{
    Exp e1;
    Exp e2;
    String op;

    public RelationalExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Exp clone() {
        return new RelationalExp(e1.clone(), e2.clone(), op);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;
        v1= e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(Objects.equals(op, "<")){
                    return new BoolValue(n1 < n2);
                }
                if(Objects.equals(op, "<=")){
                    return new BoolValue(n1 <= n2);
                }
                if(Objects.equals(op, "==")){
                    return new BoolValue(n1 == n2);
                }
                if(Objects.equals(op, "!=")){
                    return new BoolValue(n1 != n2);
                }
                if(Objects.equals(op, ">")){
                    return new BoolValue(n1 > n2);
                }
                if(Objects.equals(op, ">=")){
                    return new BoolValue(n1 >= n2);
                }
                throw new MyException("Invalid operator");
            } else
                throw new MyException("The second expression does not evaluate to an int");
        } else
            throw new MyException("The first expression does not evaluate to an int");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("second operand is not an integer");
            }
        } else {
            throw new MyException("first operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op.toString() + " " + e2.toString();
    }
}
