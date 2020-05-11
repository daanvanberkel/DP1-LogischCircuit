package nl.daanvanberkel.LogischCircuit.Models;

public class NorGate extends Gate {
    @Override
    protected boolean computeResult() {
        boolean lastValue = true;

        for (boolean value : values.values()) {
            if (!value && lastValue) {
                lastValue = false;
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getNodeType() { return "NOR"; }
}
