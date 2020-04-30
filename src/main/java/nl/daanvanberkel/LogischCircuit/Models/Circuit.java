package nl.daanvanberkel.LogischCircuit.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Circuit implements OutputHandler {
    private final ArrayList<InputNode> inputNodes = new ArrayList<>();
    private final ArrayList<OutputNode> outputNodes = new ArrayList<>();

    public void addInputNode(InputNode node) {
        inputNodes.add(node);
    }

    public void addOutputNode(OutputNode node) {
        outputNodes.add(node);
        node.setOutputHandler(this);
    }

    public List<InputNode> getInputNodes() {
        return Collections.unmodifiableList(inputNodes);
    }

    public List<OutputNode> getOutputNodes() {
        return Collections.unmodifiableList(outputNodes);
    }

    public void start() {
        for(InputNode node: inputNodes) {
            node.start();
        }
    }

    public void handleOutput(OutputNode node) {
        System.out.println("Probe " + node.getName() + " received " + node.getResult());
    }
}
