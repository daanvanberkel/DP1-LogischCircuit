package nl.daanvanberkel.LogischCircuit.Models;

public class NandGate extends Gate {
    @Override
    protected boolean computeResult() {
        boolean lastValue = false;

        for (boolean value : values.values()) {
            if (value && !lastValue) {
                lastValue = true;
            } else {
                return false;
            }
        }

        return true;
    }
}
