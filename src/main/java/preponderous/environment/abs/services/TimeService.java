/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environment.abs.services;

import preponderous.environment.misc.CONFIG;
import preponderous.environment.utils.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Daniel McCoy Stephenson
 * @since January 8th, 2022
 */
public abstract class TimeService extends Thread {
    private boolean running;

    @Override
    public void run() {
        while (isRunning()) {
            elapse();
            try {
                TimeUnit.SECONDS.sleep(CONFIG.TIME_SLOT_LENGTH_IN_SECONDS);
            } catch (Exception e) {
                Logger.getInstance().log("Time stream was interrupted.");
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {

    }

    public abstract void elapse();
}