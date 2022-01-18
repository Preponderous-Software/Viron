/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public class EnvironmentLib {
    private boolean debugFlag = false;

    public String getVersion() {
        return "v0.2-alpha-1";
    }

    public boolean isDebugEnabled() {
        return debugFlag;
    }

    public void setDebugFlag(boolean b) {
        debugFlag = b;
    }

    /**
     * Method to log a message if debug is set to true.
     * @param message to log.
     * @return boolean signifying success
     */
    public boolean log(String message) {
        if (debugFlag) {
            System.out.println("[EnvironmentLib] " + message);
            return true;
        }
        return false;
    }
}