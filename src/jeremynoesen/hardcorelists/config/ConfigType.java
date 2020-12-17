package jeremynoesen.hardcorelists.config;

import jeremynoesen.hardcorelists.HardcoreLists;

import java.io.File;
import java.io.InputStream;

/**
 * enum to make getting separate configs easier
 *
 * @author Jeremy Noesen
 */
public enum ConfigType {
    TIME("time.yml"), MESSAGE("messages.yml"), PLAYERS("players.yml");
    
    /**
     * name of file
     */
    public String fileName;
    
    /**
     * @param file file name
     */
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
