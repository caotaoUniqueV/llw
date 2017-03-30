package com.linwang.uitls.idsquence;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceRange {
    private final long min;
    private final long max;

    private final AtomicLong value;

    private volatile boolean overflow;

    public SequenceRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    public long getAndIncrement() {
        long currentValue = value.getAndIncrement();
        if (currentValue > max) {
            overflow = true;
            return -1;
        }

        return currentValue;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public boolean isOverflow() {
        return overflow;
    }
}
