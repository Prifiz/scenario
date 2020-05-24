package org.myhomeapps.config;

import org.myhomeapps.menuentities.MenuSystem;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class SimpleYamlParser implements ConfigParser {

    private final File config;

    public SimpleYamlParser(String filePath) throws IOException {
        config = loadConfig(filePath);
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
    }
}
