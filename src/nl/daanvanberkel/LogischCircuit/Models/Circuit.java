package nl.daanvanberkel.LogischCircuit.Models;

import java.util.ArrayList;

public class Circuit {
    private ArrayList<Node> inputNodes = new ArrayList<>();
    private ArrayList<Node> outputNodes = new ArrayList<>();

    public void addInputNode(Node node) {
        inputNodes.add(node);
    }

    public void addOutputNode(Node node) {
        outputNodes.add(node);
    }

    public void start() {
        for(Node node: inputNodes) {
            node.setInputValueFor(null, null);
        }
    }
}
