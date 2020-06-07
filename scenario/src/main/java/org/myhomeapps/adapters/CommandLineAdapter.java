package org.myhomeapps.adapters;

import org.apache.commons.lang3.StringUtils;
import org.myhomeapps.menuentities.Bindings;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineAdapter {

    public void bind(Bindings bindings, String userInput) throws IOException {
        updateBindFields(bindings.getField(), userInput);
        updateBindMethods(bindings.getMethod());
    }

    private static final String FIELD_NAME_PATTERN = "[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static final String METHOD_NAME_PATTERN = "[a-z][a-zA-Z0-9]*";
    private static final String CLASS_NAME_PATTERN = "[A-Z][a-zA-Z0-9]*";

    static final String INCORRECT_FIELD_MSG = "Incorrect field binding declaration: ";
    static final String INCORRECT_METHOD_MSG = "Incorrect method binding declaration: ";

    private void updateBindFields(String bindField, String userInput) throws IOException {
        Pattern bindFieldOnlyPattern = Pattern.compile("^" + FIELD_NAME_PATTERN + "$");
        Pattern bindFieldOfCertainClassPattern = Pattern.compile(
                "^(" + CLASS_NAME_PATTERN + ")\\.(" + FIELD_NAME_PATTERN + ")$");

        Matcher fieldOfClassMatcher = bindFieldOfCertainClassPattern.matcher(bindField);

        if(StringUtils.isNotBlank(bindField)) {
            if (bindFieldOnlyPattern.matcher(bindField).matches()) {
                doBindField(bindField, userInput);
            } else if (fieldOfClassMatcher.matches()) {
                String clazzNameToBind = fieldOfClassMatcher.group(1);
                if (this.getClass().getSimpleName().equals(clazzNameToBind)) {
                    doBindField(fieldOfClassMatcher.group(2), userInput);
                }
            } else {
                throw new IOException(INCORRECT_FIELD_MSG + bindField);
            }
        }
    }

    private void doBindField(String bindField, String userInput) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (bindField.equals(field.getName())) {
                try {
                    field.setAccessible(true);
                    field.set(this, userInput);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();// FIXME add logging
                }
            }
        }
    }

    private void updateBindMethods(String bindMethodName) throws IOException {
        Pattern bindMethodOnlyPattern = Pattern.compile("^" + METHOD_NAME_PATTERN + "$");
        Pattern bindMethodOfCertainClassPattern = Pattern.compile(
                "^(" + CLASS_NAME_PATTERN + ")\\.(" + METHOD_NAME_PATTERN + ")$");

        Matcher methodOfClassMatcher = bindMethodOfCertainClassPattern.matcher(bindMethodName);

        if (StringUtils.isNotBlank(bindMethodName)) {
            if (bindMethodOnlyPattern.matcher(bindMethodName).matches()) {
                bindMethod(bindMethodName);
            } else if (methodOfClassMatcher.matches()) {
                String clazzNameToBind = methodOfClassMatcher.group(1);
                if (this.getClass().getSimpleName().equals(clazzNameToBind)) {
                    bindMethod(methodOfClassMatcher.group(2));
                }
            } else {
                throw new IOException(INCORRECT_METHOD_MSG + bindMethodName);
            }
        }
    }

    private void bindMethod(String bindMethod) {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (bindMethod.equals(method.getName())) {
                doRunMethod(method);
            }
        }
    }

    private void doRunMethod(Method method) {
        try {
            method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();// FIXME add logging
        }
    }


}
