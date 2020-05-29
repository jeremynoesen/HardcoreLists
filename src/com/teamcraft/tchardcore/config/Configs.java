package com.teamcraft.tchardcore.config;

/**
 * initialize all configs
 */
public class Configs {
    
    private static final Config players = new Config(ConfigType.PLAYERS);
    private static final Config message = new Config(ConfigType.MESSAGE);
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