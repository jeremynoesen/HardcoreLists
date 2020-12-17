package jeremynoesen.hardcorelists.config;

/**
 * initialize all configs
 *
 * @author Jeremy Noesen
 */
public class Configs {
    
    /**
     * player list file
     */
    private static final Config players = new Config(ConfigType.PLAYERS);
    
    /**
     * message config file
     */
    private static final Config message = new Config(ConfigType.MESSAGE);
    
    /**
     * time config file
     */
    private static final Config time = new Config(ConfigType.TIME);
    
    /**
     * @param type config type
     * @return config manager for the selected type
     */
    public static Config getConfig(ConfigType type) {
        
        switch (type) {
            case MESSAGE:
                return message;
            
            case PLAYERS:
                return players;
            
            case TIME:
                return time;
            
        }
        return null;
    }
    
}
