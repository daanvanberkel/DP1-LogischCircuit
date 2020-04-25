package nl.daanvanberkel.LogischCircuit.Models;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {
    protected String name;
    protected ArrayList<Node> inputNodes = new ArrayList<>();
    protected ArrayList<Node> outputNodes = new ArrayList<>();
    protected HashMap<Node, Boolean> values = new HashMap<>();

    public void addInputNode(Node node) {
        inputNodes.add(node);
    }

    public void addOutputNode(Node node) {
        outputNodes.add(node);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected boolean hasValueForAllInputs() {
        for (Node node: inputNodes) {
            if (values.get(node) == null) {
                return false;
            }
        }

        return true;
    }

    abstract public void setInputValueFor(Node node, Boolean value);
}
