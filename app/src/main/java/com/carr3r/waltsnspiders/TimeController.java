package com.carr3r.waltsnspiders;

import com.carr3r.waltsnspiders.listeners.ClockListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wneto on 26/10/2015.
 */
public class TimeController {

    public static final String TAG = "TimeController";

    protected int elapsed = 0;
    protected long last = 0;
    protected long diff = 0;
    protected List<ClockListener> listeners;

    TimeController() {
        listeners = new ArrayList<ClockListener>();
    }

    public int getElapsed() {
        return elapsed;
    }

    public void resetTime() {
        last = diff = elapsed = 0;
    }

    public void addListener(ClockListener newListener) {
        listeners.add(newListener);
    }

    public void removeListener(ClockListener listener) {
        listeners.remove(listener);
    }

    public void reset() {
        last = System.currentTimeMillis();
    }

    public void fireSecond() {
        Iterator it = listeners.iterator();
        while (it.hasNext())
            ((ClockListener) it.next()).tickSecond();
    }

    public void tick() {
        diff += System.currentTimeMillis() - last;
        if (diff > 1000) {
            fireSecond();
            diff = 0;
            elapsed++;
        }

        last = System.currentTimeMillis();

    }
}
