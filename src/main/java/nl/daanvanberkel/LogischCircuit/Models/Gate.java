package nl.daanvanberkel.LogischCircuit.Models;

import java.util.HashMap;
import java.util.Map;

public abstract class Gate extends CircuitComposite {
    protected Map<ICircuitComponent, Boolean> values = new HashMap<>();

    @Override
    public void setValue(ICircuitComponent sender, Boolean value) {
        values.put(sender, value);

        lastResult = computeResult();

        for (ICircuitComponent child : children) {
            child.setValue(this, lastResult);
        }
    }

    public abstract Boolean computeResult();
}
