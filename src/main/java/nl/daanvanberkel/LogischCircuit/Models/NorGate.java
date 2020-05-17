package nl.daanvanberkel.LogischCircuit.Models;

public class NorGate extends Gate {
    @Override
    protected boolean computeResult() {
        return !values.values().contains(true);
    }

    @Override
    public String getNodeType() { return "NOR"; }
}
