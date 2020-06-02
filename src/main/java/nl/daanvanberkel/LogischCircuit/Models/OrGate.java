package nl.daanvanberkel.LogischCircuit.Models;

public class OrGate extends Gate {
    @Override
    public Boolean computeResult() {
        return values.containsValue(true);
    }

    @Override
    public String getType() {
        return "OR";
    }
}
