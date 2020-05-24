package org.myhomeapps.adapters;

import org.apache.commons.lang3.StringUtils;
import org.myhomeapps.menuentities.MenuFrame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class CommandLineAdapter implements Observer {

    @Override
    public final void update(Observable o, Object arg) {
        MenuFrame currentFrame = (MenuFrame) o;
        updateBindFields(currentFrame);
        updateBindMethods(currentFrame);
    }

    private void updateBindFields(MenuFrame currentFrame) {
        String bindField = currentFrame.getField();
        if (StringUtils.isBlank(bindField)) {
            return;
        }
        for (Field field : this.getClass().getDeclaredFields()) {
            if (bindField.equals(field.getName())) {
                try {
                    field.setAccessible(true);
                    field.set(this, currentFrame.getUserInput());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();// FIXME add logging
                }
            }
        }
    }

    private void updateBindMethods(MenuFrame currentFrame) {
        String bindMethod = currentFrame.getMethod();
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
