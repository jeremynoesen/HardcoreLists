package com.teamcraft.tchardcore.config;

import com.teamcraft.tchardcore.TCHardcore;

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
        return new File(TCHardcore.getInstance().getDataFolder(), fileName);
    }
    
    /**
     * @return input stream of resource from inside plugin jar
     */
    public InputStream getResource() {
        return TCHardcore.getInstance().getResource(fileName);
    }
    
    /**
     * save resource from plugin jar to plugin folder
     */
    public void saveResource() {
        TCHardcore.getInstance().saveResource(fileName, false);
    }
}
