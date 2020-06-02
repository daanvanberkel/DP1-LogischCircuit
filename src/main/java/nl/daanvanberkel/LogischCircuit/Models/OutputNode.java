package nl.daanvanberkel.LogischCircuit.Models;

import java.util.HashMap;
import java.util.Map;

public class OutputNode implements ICircuitComponent {
    private String name;
    private Boolean value;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<ICircuitComponent, Boolean> getResult() {
        Map<ICircuitComponent, Boolean> result = new HashMap<>();

        result.put(this, value);

        return result;
    }

    @Override
    public void setValue(ICircuitComponent sender, Boolean value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "PROBE";
    }
}
