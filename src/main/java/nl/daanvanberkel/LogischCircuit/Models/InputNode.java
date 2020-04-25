package nl.daanvanberkel.LogischCircuit.Models;

public class InputNode extends Node {
    private final boolean initialValue;

    public InputNode(boolean value) {
        initialValue = value;
    }

    public void start() {
        for(Node node : outputNodes) {
            node.setInputValueFor(this, initialValue);
        }
    }
}
