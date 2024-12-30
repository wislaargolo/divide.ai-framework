package com.ufrn.imd.divide.ai.framework.closure;

import com.ufrn.imd.divide.ai.framework.model.Group;

public interface GroupClosureStrategy<T extends Group> {
    boolean shouldCloseGroup(T group);
}
