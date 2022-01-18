/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib.utils;

import preponderous.environmentlib.misc.CONFIG;

/**
 * @author Daniel McCoy Stephenson
 * @since January 17th, 2022
 */
public class Logger {
    private static Logger instance;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * This can be used to send a debug message to the console.
     * @param message The message to log to the console.
     */
    public void log(String message) {
        if (isLocalDebugFlagEnabled()) {
            System.out.println("[DEBUG] " + message);
        }
    }

    public static void setInstance(Logger instance) {
        Logger.instance = instance;
    }

    public boolean isLocalDebugFlagEnabled() {
        return CONFIG.DEBUG_FLAG;
    }

    public void setLocalDebugFlagEnabled(boolean localDebugFlag) {
        CONFIG.DEBUG_FLAG = localDebugFlag;
    }
}