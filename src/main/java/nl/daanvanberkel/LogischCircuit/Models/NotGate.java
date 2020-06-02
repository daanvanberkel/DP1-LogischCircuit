package nl.daanvanberkel.LogischCircuit.Models;

public class NotGate extends Gate {
    @Override
    public Boolean computeResult() {
        if (values.size() < 1) {
            return false;
        }

        return !values.values().iterator().next();
    }

    @Override
    public String getType() {
        return "NOT";
    }
}
