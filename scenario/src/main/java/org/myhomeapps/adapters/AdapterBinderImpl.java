package org.myhomeapps.adapters;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.myhomeapps.menuentities.Bindings;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AdapterBinderImpl implements AdapterBinder {

    Logger logger = LogManager.getLogger(getClass());

    @Getter
    private final Set<CommandLineAdapter> adapters = new HashSet<>();
    private String runAdapterOutput = "";

    private static final String FIELD_NAME_PATTERN = "[a-zA-Z_$][a-zA-Z_$0-9]*";
    private static final String CLASS_NAME_PATTERN = "[A-Z][a-zA-Z0-9]*";
    static final String INCORRECT_FIELD_MSG = "Incorrect field binding declaration: ";

    @Override
    public void register(CommandLineAdapter adapter) {
        this.adapters.add(adapter);
    }

    @Override
    public boolean bind(Bindings bindings, String userInput) {
        if(adapters.isEmpty()) {
            logger.error("No adapters registered for bindings: {}", bindings::toString);
            return false;
        }
        try {
            bindField(bindings.getField(), userInput);
            this.runAdapterOutput = executeRunAdapter(bindings.getRunAdapter());
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());// todo logging
            return false;
        }
    }

    @Override
    public String getRunAdapterOutput() {
        return runAdapterOutput;
    }

    private void bindField(String bindField, String userInput) throws Exception {
        for (CommandLineAdapter commandLineAdapter : adapters) {
            updateBindFields(commandLineAdapter, bindField, userInput);
        }
    }

    private void updateBindFields(CommandLineAdapter adapter, String bindField, String userInput) throws Exception {
        Pattern bindFieldOnlyPattern = Pattern.compile("^" + FIELD_NAME_PATTERN + "$");
        Pattern bindFieldOfCertainClassPattern = Pattern.compile(
                "^(" + CLASS_NAME_PATTERN + ")\\.(" + FIELD_NAME_PATTERN + ")$");

        Matcher fieldOfClassMatcher = bindFieldOfCertainClassPattern.matcher(bindField);

        if (StringUtils.isNotBlank(bindField)) {
            if (bindFieldOnlyPattern.matcher(bindField).matches()) {
                doBindField(adapter, bindField, userInput);
            } else if (fieldOfClassMatcher.matches()) {
                String clazzNameToBind = fieldOfClassMatcher.group(1);
                if (adapter.getClass().getSimpleName().equals(clazzNameToBind)) {
                    doBindField(adapter, fieldOfClassMatcher.group(2), userInput);
                }
            } else {
                throw new IOException(INCORRECT_FIELD_MSG + bindField);
            }
        }
    }

    private void doBindField(CommandLineAdapter adapter, String bindField, String userInput)
            throws Exception {
        int boundFields = 0;
        for (Field field : adapter.getClass().getDeclaredFields()) {
            if (bindField.equals(field.getName())) {
                field.setAccessible(true);
                field.set(adapter, userInput);
                boundFields++;
            }
        }
        if(boundFields == 0) {
            throw new IOException(String.format("No fields found to bind for name [%s] in adapter [%s]",
                    bindField, adapter.getClass().getSimpleName()));
        }
    }

    private String executeRunAdapter(String runAdapter) throws Exception {
        for (CommandLineAdapter adapter : adapters) {
            if (adapter.getClass().getSimpleName().equals(runAdapter)) {
                return (String) adapter.getClass().getMethod("execute").invoke(adapter);
            }
        }
        return "";
    }
}
