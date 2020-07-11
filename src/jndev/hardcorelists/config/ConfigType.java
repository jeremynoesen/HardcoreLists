package jndev.hardcorelists.config;

import jndev.hardcorelists.HardcoreLists;

import java.io.File;
import java.io.InputStream;

/**
 * enum to make getting separate configs easier
 *
 * @author JNDev (Jeremaster101)
 */
public enum ConfigType {
    TIME("time.yml"), MESSAGE("messages.yml"), PLAYERS("players.yml");
    
    public String fileName;
    
    ConfigType(String file) {
        this.fileName = file;
    }
    
    /**
     * @return config file of the matching file name
     */
    public File getFile() {
        return new File(HardcoreLists.getInstance().getDataFolder(), fileName);
    }
    
    /**
     * @return input stream of resource from inside plugin jar
     */
    public InputStream getResource() {
        return HardcoreLists.getInstance().getResource(fileName);
    }
    
    /**
     * save resource from plugin jar to plugin folder
     */
    public void saveResource() {
        HardcoreLists.getInstance().saveResource(fileName, false);
    }
}
