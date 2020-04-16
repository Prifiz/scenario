package org.myhomeapps.adapters;

import org.myhomeapps.MenuFrame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class CommandLineAdapter implements Observer {

    @Override
    public final void update(Observable o, Object arg) {
        //System.out.println("CHANGED!");
        MenuFrame currentFrame = (MenuFrame) o;

        String bindField = currentFrame.getBindField();
        if(bindField != null && !bindField.isEmpty()) {
            for(Field field : this.getClass().getDeclaredFields()) {
                if(field.getName().equals(bindField)) {
                    try {
                        field.setAccessible(true);
                        field.set(this, currentFrame.getUserInput());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String bindMethod = currentFrame.getBindMethod();
        if(bindMethod != null && !bindMethod.isEmpty()) {
            for(Method method : this.getClass().getDeclaredMethods()) {
                if(method.getName().equals(bindMethod)) {
                    try {
                        method.invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
