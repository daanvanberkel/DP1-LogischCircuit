package nl.daanvanberkel.LogischCircuit.Models;

public class InputNode extends Node {
    private boolean initialValue;

    public InputNode(boolean value) {
        initialValue = value;
    }

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
