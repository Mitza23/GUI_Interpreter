package com.gui.gui.Domain.Types;

import com.gui.gui.Domain.Values.StringValue;
import com.gui.gui.Domain.Values.Value;

public class StringType implements Type{
    @Override
    public String toString() {
        return "string";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public Type clone() {
        return new StringType();
    }

    @Override
    public Value getDefault() {
        return new StringValue("");
    }
}
