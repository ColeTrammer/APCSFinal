package engine.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that allows for actions to be applied between
 * a certain time interval repeatedly. Main use is spawning
 * entities at certain time intervals.
 */
public class Timer {
    private final List<TimerObject> list;
    private float elapsedTime;

    /**
     * Creates a timer with no actions.
     */
    public Timer() {
        this.list = new ArrayList<>();
        elapsedTime = 0;
    }

    /**
     * Adds the actions with the specified time information.
     *
     * @param startTime   The time to begin spawning things.
     * @param endTime     The time to end spawning things.
     * @param repeatDelay The delay before spawning again.
     * @param action      The code to execute when the time conditions are satisfied.
     */
    @SuppressWarnings("unused")
    public void addAction(float startTime, float endTime, float repeatDelay, Runnable action) {
        list.add(new TimerObject(startTime, endTime, repeatDelay, action).setTimer(this));
    }

    /**
     * Ticks the timer and applies the actions if warranted.
     *
     * @param delta The time since this method was last called.
     */
    public void tick(float delta) {
        elapsedTime += delta;
        for (int i = list.size() - 1; i >= 0; i--) {
            list.get(i).apply(elapsedTime);
        }
    }

    /**
     * Gets the elapsed time from the timer.
     *
     * @return the time that has elapsed.
     */
    @SuppressWarnings("unused")
    public float getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Removes this object from the timer.
     *
     * @param object The object to remove.
     */
    private void removeObject(TimerObject object) {
        list.remove(object);
    }

    /**
     * Object that represents the information needed to
     * store actions that must be executed at a certain time
     * repeatedly.
     */
    private class TimerObject {
        private int count;
        private final float startTime;
        private final float endTime;
        private final float repeatDelay;
        private final Runnable action;
        private Timer timer;

        /**
         * Creates a TimerObject with the specified conditions.
         *
         * @param startTime   The time to begin spawning things.
         * @param endTime     The time to end spawning things.
         * @param repeatDelay The delay before spawning again.
         * @param action      The code to execute when the time conditions are satisfied.
         */
        protected TimerObject(float startTime, float endTime, float repeatDelay, Runnable action) {
            this.count = 0;
            this.startTime = startTime;
            this.endTime = endTime;
            this.repeatDelay = repeatDelay;
            this.action = action;
        }

        /**
         * Sets the timer. Chains itself by returning "this".
         *
         * @param timer The timer that controls this object.
         * @return This object.
         */
        protected TimerObject setTimer(Timer timer) {
            this.timer = timer;
            return this;
        }

        /**
         * Uses the elapsedTime to determine whether or not to call the action in this object.
         *
         * @param elapsedTime The amount of time that has elapsed since this Object was created.
         */
        protected void apply(float elapsedTime) {
            if (elapsedTime > endTime) {
                timer.removeObject(this);
                return;
            }
            float relativeTime = elapsedTime - startTime;
            if (relativeTime >= 0 && (repeatDelay == 0 || relativeTime / repeatDelay >= count)) {
                action.run();
                count++;
            }
        }
    }
}