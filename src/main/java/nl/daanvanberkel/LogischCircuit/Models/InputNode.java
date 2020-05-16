package nl.daanvanberkel.LogischCircuit.Models;

public abstract class InputNode extends Node {
    protected boolean initialValue;

    public void toggleValue() {
        initialValue = !initialValue;
    }

    public void start() {
        for(Node node : outputNodes) {
            node.setInputValueFor(this, initialValue);
        }
    }

    @Override
    public String getNodeType() { return "IN"; }

    @Override
    public boolean getLastResult() {
        return initialValue;
    }
}
