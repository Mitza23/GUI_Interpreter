package com.gui.gui.Domain.Values;

import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Types.Type;

public interface Value extends Clonable<Value> {
    Type getType();
}
