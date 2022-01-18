/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environment;

/**
 * @author Daniel McCoy Stephenson
 * @since 10/12/2021
 */
public class EnvironmentLib {
    private boolean debugFlag = false;

    public String getVersion() {
        return "v0.15-alpha-1";
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