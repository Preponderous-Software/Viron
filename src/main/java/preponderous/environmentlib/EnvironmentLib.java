/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib;

import preponderous.environmentlib.misc.CONFIG;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public class EnvironmentLib {

    public String getVersion() {
        return "v0.3";
    }

    public boolean isDebugEnabled() {
        return CONFIG.DEBUG_FLAG;
    }

    public void setDebugFlag(boolean b) {
        CONFIG.DEBUG_FLAG = b;
    }

    /**
     * Method to log a message if debug is set to true.
     * @param message to log.
     * @return boolean signifying success
     */
    public boolean log(String message) {
        if (isDebugEnabled()) {
            System.out.println("[EnvironmentLib] " + message);
            return true;
        }
        return false;
    }
}