package org.myhomeapps.walkers.validators;

import java.util.List;

public interface ValidationExecutor {
    List<? extends GraphIssue> validate();
}
