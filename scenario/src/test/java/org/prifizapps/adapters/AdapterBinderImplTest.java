package org.prifizapps.adapters;

import lombok.Getter;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.Bindings;

import java.util.Collections;

public class AdapterBinderImplTest {

    @Test
    public void registerTest() {
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        CommandLineAdapter adapter = () -> "";
        adapterBinder.register(adapter);
        Assert.assertArrayEquals(
                Collections.singletonList(adapter).toArray(),
                adapterBinder.getAdapters().toArray());
    }

    @Test
    public void bindFieldOnlyTest() {
        class FieldOnlyAdapter implements CommandLineAdapter {
            @Getter
            private String fieldToBind;

            @Override
            public String execute() {
                return null;
            }
        }
        FieldOnlyAdapter adapter = new FieldOnlyAdapter();
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        adapterBinder.register(adapter);
        final String bindValue = "bindValue";
        Bindings bindings = new Bindings("fieldToBind", "");
        Assert.assertTrue(adapterBinder.bind(bindings, bindValue));
        Assert.assertEquals(bindValue, adapter.getFieldToBind());
    }

    @Test
    public void runAdapterOnlyTest() {
        final String executeValue = "executeValue";
        class RunOnlyAdapter implements CommandLineAdapter {
            @Override
            public String execute() {
                return executeValue;
            }
        }
        RunOnlyAdapter adapter = new RunOnlyAdapter();
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        adapterBinder.register(adapter);
        Bindings bindings = new Bindings("", "RunOnlyAdapter");
        Assert.assertTrue(adapterBinder.bind(bindings, ""));
        Assert.assertEquals(executeValue, adapterBinder.getRunAdapterOutput());
    }

    @Test
    public void bindFieldAndRunAdapterTest() {
        final String executeValue = "executeValue";
        final String bindValue = "bindValue";
        class FieldAndRunOnlyAdapter implements CommandLineAdapter {
            @Getter
            private String fieldToBind;

            @Override
            public String execute() {
                return executeValue;
            }
        }
        FieldAndRunOnlyAdapter adapter = new FieldAndRunOnlyAdapter();
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        adapterBinder.register(adapter);
        Bindings bindings = new Bindings("fieldToBind", "FieldAndRunOnlyAdapter");
        Assert.assertTrue(adapterBinder.bind(bindings, bindValue));
        Assert.assertEquals(bindValue, adapter.getFieldToBind());
        Assert.assertEquals(executeValue, adapterBinder.getRunAdapterOutput());
    }

    @Test
    public void noAdaptersTest() {
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        Bindings bindings = new Bindings("fieldToBind", "FieldAndRunOnlyAdapter");
        Assert.assertFalse(adapterBinder.bind(bindings, ""));
    }

    @Test
    public void bindFieldFailTest() {
        final String defaultFieldValue = "default";
        class FieldBindFailAdapter implements CommandLineAdapter {
            @Getter
            private String fieldWithNoBinding = defaultFieldValue;

            @Override
            public String execute() {
                return null;
            }
        }
        FieldBindFailAdapter adapter = new FieldBindFailAdapter();
        AdapterBinderImpl adapterBinder = new AdapterBinderImpl();
        adapterBinder.register(adapter);
        final String bindValue = "bindValue";
        Bindings bindings = new Bindings("fieldToBind", "");
        Assert.assertFalse(adapterBinder.bind(bindings, bindValue));
        Assert.assertEquals(defaultFieldValue, adapter.getFieldWithNoBinding());
    }
}
