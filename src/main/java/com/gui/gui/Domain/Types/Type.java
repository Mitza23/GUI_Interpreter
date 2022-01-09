package com.gui.gui.Domain.Types;

import com.gui.gui.Domain.Clonable;
import com.gui.gui.Domain.Values.Value;

public interface Type extends Clonable<Type> {
    Value getDefault();
}
