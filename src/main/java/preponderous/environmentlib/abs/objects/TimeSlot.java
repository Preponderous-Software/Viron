/*
  Copyright (c) 2022 Preponderous Software
  MIT License
 */
package preponderous.environmentlib.abs.objects;

import java.time.LocalDateTime;

/**
 * @author Daniel McCoy Stephenson
 * @since January 8th, 2022
 * @brief This class is intended to represent a finite slot of time.
 */
public abstract class TimeSlot {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int milliseconds;

    public TimeSlot(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public boolean isActive() {
        return LocalDateTime.now().isBefore(timestamp.plusSeconds(milliseconds/1000));
    }
}