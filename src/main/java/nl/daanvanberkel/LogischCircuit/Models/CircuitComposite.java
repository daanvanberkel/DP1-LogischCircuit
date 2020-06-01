package nl.daanvanberkel.LogischCircuit.Models;

import java.util.*;

public abstract class CircuitComposite implements ICircuitComponent {
    protected List<ICircuitComponent> children = new ArrayList<>();
    protected String name;
    protected Boolean lastResult;

    @Override
    public Map<ICircuitComponent, Boolean> getResult() {
        Map<ICircuitComponent, Boolean> result = new HashMap<>();

        for(ICircuitComponent child : children) {
            result.putAll(child.getResult());
        }

        return result;
    }

    public void addChild(ICircuitComponent child) {
        children.add(child);
    }

    public List<ICircuitComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLastResult() {
        return lastResult;
    }
}
