package nl.daanvanberkel.LogischCircuit.Models;

public class InputNode extends Node {
    private boolean initialValue;

    public InputNode(boolean value) {
        initialValue = value;
    }

    @Override
    public void setInputValueFor(Node n, Boolean v) {
        for (Node node: outputNodes) {
            node.setInputValueFor(this, initialValue);

            System.out.println("SENDING " + initialValue + " TO " + node.getName() + " FROM INPUT " + getName());
        }
    }
}
