/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.IllegalStateException
 *  java.lang.InterruptedException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Observable
 */
package com.kontagent;

import java.util.Observable;

public class Stateful
extends Observable {
    private State mState = State.STOPPED;
    private final Object mStateSync = new Object();

    private /* varargs */ void assertState(State ... arrstate) {
        State state = this.getState();
        int n = arrstate.length;
        int n2 = 0;
        do {
            block4 : {
                boolean bl;
                block3 : {
                    bl = false;
                    if (n2 >= n) break block3;
                    if (state != arrstate[n2]) break block4;
                    bl = true;
                }
                if (bl) break;
                Object[] arrobject = new Object[]{state.name()};
                throw new IllegalStateException(String.format((String)"Operation can not be exuected in %s state.", (Object[])arrobject));
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setState(State state) {
        Object object;
        Object object2 = object = this.mStateSync;
        synchronized (object2) {
            if (this.mState != state) {
                this.mState = state;
                this.mStateSync.notifyAll();
                this.setChanged();
                this.notifyObservers();
            }
            return;
        }
    }

    protected void assertResumed() {
        State[] arrstate = new State[]{State.RUNNING};
        this.assertState(arrstate);
    }

    protected void assertStarted() {
        State[] arrstate = new State[]{State.PAUSED, State.RUNNING};
        this.assertState(arrstate);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public State getState() {
        Object object;
        Object object2 = object = this.mStateSync;
        synchronized (object2) {
            return this.mState;
        }
    }

    public boolean isPaused() {
        return this.getState() == State.PAUSED;
    }

    public boolean isResumed() {
        return this.getState() == State.RUNNING;
    }

    public boolean isStarted() {
        State state = this.getState();
        return state == State.PAUSED || state == State.RUNNING;
    }

    public boolean isStopped() {
        return this.getState() == State.STOPPED;
    }

    protected void onPause() {
    }

    protected void onResume() {
    }

    protected void onStart() {
    }

    protected void onStop() {
    }

    public boolean pause() {
        boolean bl = true;
        switch (1.$SwitchMap$com$kontagent$Stateful$State[this.getState().ordinal()]) {
            default: {
                bl = false;
            }
            case 2: {
                return bl;
            }
            case 1: {
                this.onPause();
                this.setState(State.PAUSED);
                return bl;
            }
            case 3: 
        }
        return false;
    }

    public boolean resume() {
        boolean bl = true;
        switch (1.$SwitchMap$com$kontagent$Stateful$State[this.getState().ordinal()]) {
            default: {
                bl = false;
            }
            case 1: {
                return bl;
            }
            case 2: {
                this.onResume();
                this.setState(State.RUNNING);
                return bl;
            }
            case 3: 
        }
        return false;
    }

    public void start() {
        switch (1.$SwitchMap$com$kontagent$Stateful$State[this.getState().ordinal()]) {
            default: {
                return;
            }
            case 2: {
                this.resume();
                return;
            }
            case 3: 
        }
        this.onStart();
        this.setState(State.PAUSED);
        this.onResume();
        this.setState(State.RUNNING);
    }

    public void stop() {
        switch (this.getState()) {
            default: {
                return;
            }
            case RUNNING: {
                this.onPause();
                this.setState(State.PAUSED);
                this.onStop();
                this.setState(State.STOPPED);
                return;
            }
            case PAUSED: 
        }
        this.onStop();
        this.setState(State.STOPPED);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* varargs */ State waitStates(long l, State ... arrstate) throws InterruptedException {
        Object object;
        Object object2 = object = this.mStateSync;
        synchronized (object2) {
            long l2 = l + System.currentTimeMillis();
            block3 : do {
                int n = arrstate.length;
                int n2 = 0;
                do {
                    if (n2 < n) {
                        State state = arrstate[n2];
                        if (this.mState.equals((Object)state)) {
                            return this.mState;
                        }
                    } else {
                        long l3 = l2 - System.currentTimeMillis();
                        if (l3 < 0L) {
                            return null;
                        }
                        this.mStateSync.wait(l3);
                        continue block3;
                    }
                    ++n2;
                } while (true);
                break;
            } while (true);
        }
    }

    public /* varargs */ State waitStates(State ... arrstate) throws InterruptedException {
        State state;
        while ((state = this.waitStates(Long.MAX_VALUE, arrstate)) == null) {
        }
        return state;
    }

    public static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State PAUSED;
        public static final /* enum */ State RUNNING;
        public static final /* enum */ State STOPPED;

        static {
            STOPPED = new State();
            PAUSED = new State();
            RUNNING = new State();
            State[] arrstate = new State[]{STOPPED, PAUSED, RUNNING};
            $VALUES = arrstate;
        }

        public static State valueOf(String string2) {
            return (State)Enum.valueOf(State.class, (String)string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }
    }

}

