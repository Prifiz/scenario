package org.myhomeapps.adapters;

import org.apache.commons.lang3.StringUtils;
import org.myhomeapps.menuentities.Bindings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandLineAdapter {

//    @Override
//    public final void update(Observable o, Object arg) {
//        MenuFrame currentFrame = (MenuFrame) o;
//        updateBindFields(currentFrame);
//        updateBindMethods(currentFrame);
//    }



    public void bind(Bindings bindings, String userInput) {
        updateBindFields(bindings.getField(), userInput);
        updateBindMethods(bindings.getMethod());
    }

    private void updateBindFields(String bindField, String userInput) {
        // TODO specify field & method as Classname1.field & Classname2.method
        //  (not to require unique fields names of different classes)
        if (StringUtils.isBlank(bindField)) {
            return;
        }
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

    private void updateBindMethods(String bindMethod) {
        if (StringUtils.isBlank(bindMethod)) {
            return;
        }
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
