package nl.daanvanberkel.LogischCircuit.Models;

public abstract class InputNode extends CircuitComposite {
    public void toggleValue() {
        lastResult = !lastResult;
    }

    @Override
    public String getType() { return "IN"; }

    @Override
    public void setValue(ICircuitComponent sender, Boolean value) {
        for(ICircuitComponent child : children) {
            child.setValue(this, lastResult);
        }
    }
}
