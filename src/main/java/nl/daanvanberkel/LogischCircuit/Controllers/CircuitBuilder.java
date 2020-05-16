package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.InfiniteLoopException;
import nl.daanvanberkel.LogischCircuit.Exceptions.InvalidNodeConnectionException;
import nl.daanvanberkel.LogischCircuit.Exceptions.ProbeNotReachedException;
import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.Circuit;
import nl.daanvanberkel.LogischCircuit.Models.InputNode;
import nl.daanvanberkel.LogischCircuit.Models.Node;
import nl.daanvanberkel.LogischCircuit.Models.OutputNode;

import java.util.ArrayList;
import java.util.HashMap;

public class CircuitBuilder {

    private final HashMap<String, Node> nodes = new HashMap<>();
    private final NodeFactory factory;

    public CircuitBuilder(NodeFactory factory) {
        this.factory = factory;
    }

    public void addNode(String name, String type) throws UnsupportedGateTypeException {
        Node node = factory.createNode(type);

        if (node == null) {
            throw new UnsupportedGateTypeException("Node " + type + " cannot be handled by this system.");
        }

        node.setName(name);
        nodes.put(name, node);
    }

    public void connectNodes(String fromName, String[] toNames) throws InvalidNodeConnectionException {
        for (String toName : toNames) {
            connectNodes(fromName, toName);
        }
    }

    public void connectNodes(String fromName, String toName) throws InvalidNodeConnectionException {
        Node fromNode = nodes.get(fromName);
        Node toNode = nodes.get(toName);

        if (fromNode == null || toNode == null) {
            throw new InvalidNodeConnectionException("Cannot connect node " + fromName + " to " + toName + ". To from node or to node doesn't exist.");
        }

        fromNode.addOutputNode(toNode);
        toNode.addInputNode(fromNode);
    }

    public Circuit buildCircuit() throws InfiniteLoopException, ProbeNotReachedException {
        Circuit circuit = new Circuit();

        for(Node node : nodes.values()) {
            if (node instanceof InputNode) {
                circuit.addInputNode((InputNode) node);
            }

            if (node instanceof OutputNode) {
                OutputNode outputNode = (OutputNode) node;

                if (outputNode.getInputNodes().size() < 1) {
                    throw new ProbeNotReachedException("Probe " + outputNode.getName() + " cannot be reached.");
                }

                circuit.addOutputNode(outputNode);
            }
        }

        detectInfiniteLoop(circuit);

        return circuit;
    }

    private void detectInfiniteLoop(Circuit circuit) throws InfiniteLoopException {
        ArrayList<Node> foundNodes = new ArrayList<>();

        try {
            for (InputNode in : circuit.getInputNodes()) {
                detectInfiniteLoopNodes(in, foundNodes);
            }
        } catch (StackOverflowError e) {
            throw new InfiniteLoopException();
        }
    }

    private void detectInfiniteLoopNodes(Node node, ArrayList<Node> foundNodes) throws InfiniteLoopException {
        for(Node n : node.getOutputNodes()) {
            foundNodes.add(n);
            detectInfiniteLoopNodes(n, foundNodes);
        }
    }
}
