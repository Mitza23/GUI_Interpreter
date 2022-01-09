package com.gui.gui.Domain.Values;

import com.gui.gui.Domain.Types.RefType;
import com.gui.gui.Domain.Types.Type;

public class RefValue implements Value{

    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value clone() {
        return new RefValue(address, locationType.clone());
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return  "address = " + address + " -> "  + locationType;
    }
}
