package org.myhomeapps.adapters;

import lombok.Getter;
import lombok.Setter;
import mockit.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.myhomeapps.menuentities.Bindings;

import java.io.IOException;

import static org.myhomeapps.adapters.CommandLineAdapter.INCORRECT_FIELD_MSG;
import static org.myhomeapps.adapters.CommandLineAdapter.INCORRECT_METHOD_MSG;

public class CommandLineAdapterTest {

    private static final String FIELD_START_VALUE = "start_value";
    private static final String METHOD_RETURN_VALUE = "method_return_value";

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private class FieldAdapter extends CommandLineAdapter {
        @Getter
        @Setter
        private String field = FIELD_START_VALUE;
    }

    @Test
    public void updateBindFieldsEmptyFieldTest() throws IOException {
        FieldAdapter adapter = new FieldAdapter();
        Bindings bindings = new Bindings("", "");
        adapter.bind(bindings, "");
        Assert.assertEquals(FIELD_START_VALUE, adapter.getField());
    }

    @Test
    public void updateBindFieldOnlyTest() throws IOException {
        FieldAdapter adapter = new FieldAdapter();
        final String fieldNewValue = "UPDATED_FIELD";
        Bindings bindings = new Bindings("field", "");
        adapter.bind(bindings, fieldNewValue);
        Assert.assertEquals(fieldNewValue, adapter.getField());
    }

    @Test
    public void updateBindFieldOfCertainClassTest() throws IOException {
        FieldAdapter adapter = new FieldAdapter();
        final String fieldNewValue = "UPDATED_FIELD_OF_CLASS";
        Bindings bindings = new Bindings("FieldAdapter.field", "");
        adapter.bind(bindings, fieldNewValue);
        Assert.assertEquals(fieldNewValue, adapter.getField());
    }

    @Test
    public void updateBindFieldsIncorrectDeclarationTest() throws IOException {
        FieldAdapter adapter = new FieldAdapter();
        final String fieldNewValue = "UPDATED_FIELD_OF_CLASS";
        final String incorrectField = "Incorrect.field.declaration";
        Bindings bindings = new Bindings(incorrectField, "");
        expectedEx.expect(IOException.class);
        expectedEx.expectMessage(INCORRECT_FIELD_MSG + incorrectField);
        adapter.bind(bindings, fieldNewValue);
    }

    private class MethodAdapter extends CommandLineAdapter {

        public String method() {
            System.out.println("I'm called");
            return METHOD_RETURN_VALUE;
        }
    }

    @Test
    public void updateBindMethodsEmptyMethodTest() throws IOException {
        MethodAdapter adapter = new MethodAdapter();
        Bindings bindings = new Bindings("", "");
        adapter.bind(bindings, "");
        new Verifications(){{
           adapter.method(); times = 0;
        }};
    }


    @Tested @Mocked
    MethodAdapter adapter = new MethodAdapter();

    @Test
    public void updateBindMethodOnlyTest() throws IOException {
        Bindings bindings = new Bindings("", "method");
        adapter.bind(bindings, "");

        new Verifications() {{
           adapter.method(); times = 1;
        }};
    }

    @Test
    public void updateBindMethodOfCertainClassTest() throws IOException {
        Bindings bindings = new Bindings("", "MethodAdapter.method");
        adapter.bind(bindings, "");

        new Verifications() {{
            adapter.method(); times = 1;
        }};
    }

    @Test
    public void updateBindMethodsIncorrectDeclarationTest() throws IOException {
        MethodAdapter adapter = new MethodAdapter();
        final String incorrectMethod = "Incorrect.Method";
        Bindings bindings = new Bindings("", incorrectMethod);
        expectedEx.expect(IOException.class);
        expectedEx.expectMessage(INCORRECT_METHOD_MSG + incorrectMethod);
        adapter.bind(bindings, "");
    }

    private class FieldAndMethodAdapter extends CommandLineAdapter {

        @Getter
        @Setter
        private String field;

        public String method() {
            System.out.println("I'm called!!!");
            return field;
        }
    }

    @Tested @Mocked
    FieldAndMethodAdapter fieldAndMethodAdapter = new FieldAndMethodAdapter();

    @Test
    public void updateFieldAndCallAMethodTest() throws IOException {
        final String fieldNewValue = "UPDATED_FIELD_OF_CLASS";
        Bindings bindings = new Bindings("field", "method");
        fieldAndMethodAdapter.bind(bindings, fieldNewValue);
        new Verifications() {{
           fieldAndMethodAdapter.method(); times = 1;
           Assert.assertEquals(fieldNewValue, fieldAndMethodAdapter.field);
        }};
    }
}
