package nl.daanvanberkel.LogischCircuit.Models;

public class AndGate extends Gate {
    @Override
    protected boolean computeResult() {
        for (boolean value : values.values()) {
            if (!value) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getNodeType() { return "AND"; }
}
