package ru.mamapapa.task5;

public class StateManager {
    public enum State {
        A,
        B,
        C,
        D,
        E,;
    }

    private static StateManager instance;
    private State state;

    private StateManager() {
        this.state = State.A;
    }

    public static synchronized StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public void setState(String state) {
        this.state = State.valueOf(state);
    }

    public void setNextState() {
        State[] values = State.values();
        int ordinal = state.ordinal();
        this.state = values[values.length - 1 == ordinal ? 0 : ++ordinal];
    }

    public String getState() {
        return state.name();
    }
}
