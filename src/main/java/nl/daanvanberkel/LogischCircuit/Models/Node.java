package nl.daanvanberkel.LogischCircuit.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class Node {
    protected String name;
    protected HashMap<Node, Boolean> values = new HashMap<>();
    protected ArrayList<Node> inputNodes = new ArrayList<>();
    protected ArrayList<Node> outputNodes = new ArrayList<>();

    public abstract String getNodeType();

    public void addInputNode(Node node) {
        inputNodes.add(node);
    }

    public List<Node> getInputNodes() {
        return Collections.unmodifiableList(inputNodes);
    }

    public void addOutputNode(Node node) {
        outputNodes.add(node);
    }

    public List<Node> getOutputNodes() {
        return Collections.unmodifiableList(outputNodes);
    }

    protected boolean hasValueForAllInputs() {
        for (Node node: inputNodes) {
            if (values.get(node) == null) {
                return false;
            }
        }

        return true;
    }

    public void setInputValueFor(Node node, boolean value) {
        values.put(node, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean getLastResult();
}
