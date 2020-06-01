package nl.daanvanberkel.LogischCircuit.Models;

public class NorGate extends Gate {
    @Override
    public Boolean computeResult() {
        return !values.containsValue(true);
    }

    @Override
    public String getType() {
        return "NOR";
    }
}
