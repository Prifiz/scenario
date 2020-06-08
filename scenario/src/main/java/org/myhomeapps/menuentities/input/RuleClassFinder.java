package org.myhomeapps.menuentities.input;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Set;
import java.util.stream.Collectors;

public class RuleClassFinder implements AnnotatedClassFinder {

    public Set<Class<?>> find() {
        Set<Class<?>> foundByAnnotation = new Reflections("",
                new TypeAnnotationsScanner(),
                new SubTypesScanner()
        ).getTypesAnnotatedWith(InputCheckingRule.class); // fixme cannot find IsNumber if called from outside app

        return foundByAnnotation.stream()
                .filter(AbstractInputRule.class::isAssignableFrom)
                .collect(Collectors.toSet());
    }
}
