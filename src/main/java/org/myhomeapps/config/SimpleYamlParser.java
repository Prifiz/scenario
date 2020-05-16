package org.myhomeapps.config;

import com.github.sbaudoin.yamllint.Linter;
import com.github.sbaudoin.yamllint.YamlLintConfig;
import com.github.sbaudoin.yamllint.YamlLintConfigException;
import org.apache.commons.io.IOUtils;
import org.myhomeapps.menuentities.MacrosParser;
import org.myhomeapps.menuentities.MenuItem;
import org.myhomeapps.menuentities.MenuSettings;
import org.myhomeapps.menuentities.MenuSystem;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;

public class SimpleYamlParser implements ConfigParser {

    private File config;

    public SimpleYamlParser(String filePath) throws IOException {
        config = loadConfig(filePath);
//        try {
//            Linter.run(new YamlLintConfig(IOUtils.toString(new FileInputStream("default.yml"))), config);
//        } catch (YamlLintConfigException e) {
//            e.printStackTrace();
//        }

        /*Yaml yaml = new Yaml();
        Items items = yaml.loadAs(new InputStreamReader(new FileInputStream(config)), Items.class);
        //MenuItem item = yaml.loadAs(new InputStreamReader(new FileInputStream(config)), MenuItem.class);
        items.getItems().forEach(item -> {
            System.out.println(item.getName());
        });
        //System.out.println(item);
        //System.out.println(item);


*/


//        StandaloneItems standaloneItems = yaml.loadAs(new InputStreamReader(new FileInputStream(config)), StandaloneItems.class);
//        for (MenuItem item : standaloneItems.getStandaloneItems()) {
//            System.out.println(item);
//        }

//        MenuItem item1 = new MenuItem(1, "one", "ONE", "");
//        MenuItem item2 = new MenuItem(2, "two", "TWO", "one");
//        List<MenuItem> items = new ArrayList<>();
//        items.add(item1);
//        items.add(item2);
//        Yaml yaml = new Yaml();
//        String ymlText = yaml.dumpAs(items, Tag.STR, DumperOptions.FlowStyle.BLOCK);
//        System.out.println(ymlText);

        //System.out.println("test");
    }

    private File loadConfig(String configPath) throws IOException {
        File config = new File(configPath);
        if(!config.exists()) {
            throw new IOException(String.format("Config [%s] doesn't exits", configPath));
        }
        if(!config.isFile()) {
            throw new IOException(String.format("Config [%s] is not a file", configPath));
        }
        return config;
    }

    @Override
    public MenuSystem parseMenuSystem() throws FileNotFoundException {
        return new Yaml().loadAs(new InputStreamReader(new FileInputStream(config)), MenuSystem.class);
        // FIXME check for null elements
        // two items/frames with the same names
        //menuSystem.getMenuSystem().forEach(System.out::println);
    }

    @Override
    public List<MenuItem> parseStandaloneItems() {
        return null;
    }

    @Override
    public MenuSettings parseSettings() {
        return null;
    }
}
