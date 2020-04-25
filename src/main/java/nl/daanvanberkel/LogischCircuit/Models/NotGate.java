package nl.daanvanberkel.LogischCircuit.Models;

public class NotGate extends Gate {
    @Override
    protected boolean computeResult() {
        return !values.get(inputNodes.get(0));
    }
}
