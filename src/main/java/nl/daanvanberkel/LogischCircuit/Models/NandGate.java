package nl.daanvanberkel.LogischCircuit.Models;

public class NandGate extends Gate {
    @Override
    protected boolean computeResult() {
        return values.values().contains(false);
    }

    @Override
    public String getNodeType() { return "NAND"; }
}
