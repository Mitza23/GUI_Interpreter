package com.gui.gui.Domain.Values;

import com.gui.gui.Domain.Types.StringType;
import com.gui.gui.Domain.Types.Type;

import java.util.Objects;


public class StringValue implements Value {
    String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Value clone() {
        return new StringValue(value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return value;
    }

    @Override
    public String toString() {
        return "string: " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StringValue){
            return Objects.equals(((StringValue) obj).getVal(), value);
        }
        return false;
    }
}
