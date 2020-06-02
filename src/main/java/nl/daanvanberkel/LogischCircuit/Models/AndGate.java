package nl.daanvanberkel.LogischCircuit.Models;

public class AndGate extends Gate {
    @Override
    public Boolean computeResult() {
        return !values.containsValue(false);
    }

    @Override
    public String getType() {
        return "AND";
    }
}
