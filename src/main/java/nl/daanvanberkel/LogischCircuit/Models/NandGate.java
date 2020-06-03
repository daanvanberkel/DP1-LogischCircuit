package nl.daanvanberkel.LogischCircuit.Models;

public class NandGate extends Gate {
    @Override
    public Boolean computeResult() {
        return values.containsValue(false);
    }

    @Override
    public String getType() {
        return "NAND";
    }
}
