package org.prifizapps.adapters;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.prifizapps.menuentities.Bindings;

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
            String field = bindings.getField();
            boolean anyAdapterBound = bindField(field, userInput);
            this.runAdapterOutput = executeRunAdapter(bindings.getRunAdapter());
            if(StringUtils.isNotBlank(field) && !anyAdapterBound) {
                logger.error("No adapters found for field binding: [{}]", field);
                return false;
            }
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public String getRunAdapterOutput() {
        return runAdapterOutput;
    }

    private boolean bindField(String bindField, String userInput) throws Exception {
        boolean result = false;
        for (CommandLineAdapter commandLineAdapter : adapters) {
            if(updateBindFields(commandLineAdapter, bindField, userInput)) {
                result = true;
            }
        }
        return result;
    }

    private boolean updateBindFields(CommandLineAdapter adapter, String bindField, String userInput) throws Exception {
        Pattern bindFieldOnlyPattern = Pattern.compile("^" + FIELD_NAME_PATTERN + "$");
        Pattern bindFieldOfCertainClassPattern = Pattern.compile(
                "^(" + CLASS_NAME_PATTERN + ")\\.(" + FIELD_NAME_PATTERN + ")$");

        Matcher fieldOfClassMatcher = bindFieldOfCertainClassPattern.matcher(bindField);

        if (StringUtils.isNotBlank(bindField)) {
            if (bindFieldOnlyPattern.matcher(bindField).matches()) {
                return doBindField(adapter, bindField, userInput);
            } else if (fieldOfClassMatcher.matches()) {
                String clazzNameToBind = fieldOfClassMatcher.group(1);
                if (adapter.getClass().getSimpleName().equals(clazzNameToBind)) {
                    return doBindField(adapter, fieldOfClassMatcher.group(2), userInput);
                }
            } else {
                throw new IOException(INCORRECT_FIELD_MSG + bindField);
            }
        }
        return false;
    }

    private boolean doBindField(CommandLineAdapter adapter, String bindField, String userInput) throws Exception {
        boolean result = false;
        for (Field field : adapter.getClass().getDeclaredFields()) {
            if (bindField.equals(field.getName())) {
                field.setAccessible(true);
                field.set(adapter, userInput);
                result = true;
            }
        }
        return result;
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
