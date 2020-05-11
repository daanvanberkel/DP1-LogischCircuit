package nl.daanvanberkel.LogischCircuit.Models;

public class OrGate extends Gate {
    @Override
    protected boolean computeResult() {
        for (boolean value : values.values()) {
            if (value) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getNodeType() { return "OR"; }
}
