package nl.daanvanberkel.LogischCircuit.Models;

public class XorGate extends Gate {
    protected boolean computeResult() {
        Boolean lastValue = null;

        for (boolean value : values.values()) {
            if (lastValue != null && lastValue == value) {
                return false;
            }

            lastValue = value;
        }

        return true;
    }

    @Override
    public String getNodeType() { return "XOR"; }
}
