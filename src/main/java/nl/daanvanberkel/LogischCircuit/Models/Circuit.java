package nl.daanvanberkel.LogischCircuit.Models;

public class Circuit extends CircuitComposite {
    @Override
    public void setValue(ICircuitComponent sender, Boolean value) { }

    @Override
    public String getType() {
        return "CIRCUIT";
    }

    public void start() {
        for(ICircuitComponent child : children) {
            child.setValue(this,null);
        }
    }
}
