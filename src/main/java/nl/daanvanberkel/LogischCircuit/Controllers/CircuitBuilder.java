package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.InfiniteLoopException;
import nl.daanvanberkel.LogischCircuit.Exceptions.InvalidNodeConnectionException;
import nl.daanvanberkel.LogischCircuit.Exceptions.ProbeNotReachedException;
import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CircuitBuilder {

    private final HashMap<String, ICircuitComponent> nodes = new HashMap<>();
    private final NodeFactory factory;

    public CircuitBuilder(NodeFactory factory) {
        this.factory = factory;
    }

    public void addNode(String name, String type) throws UnsupportedGateTypeException {
        ICircuitComponent node = factory.createNode(type);

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
        ICircuitComponent fromNode = nodes.get(fromName);
        ICircuitComponent toNode = nodes.get(toName);

        if (fromNode == null || toNode == null) {
            throw new InvalidNodeConnectionException("Cannot connect node " + fromName + " to " + toName + ". To from node or to node doesn't exist.");
        }

        if (fromNode instanceof CircuitComposite) {
            CircuitComposite composite = (CircuitComposite) fromNode;
            composite.addChild(toNode);
        } else {
            throw new InvalidNodeConnectionException("Cannot connect node " + fromName + " to " + toName + ". The from node cannot contain children.");
        }
    }

    public Circuit buildCircuit() throws InfiniteLoopException, ProbeNotReachedException {
        Circuit circuit = new Circuit();

        for(ICircuitComponent node : nodes.values()) {
            if (node instanceof InputNode) {
                circuit.addChild(node);
            }
        }

        detectInfiniteLoop(circuit);

        return circuit;
    }

    private void detectInfiniteLoop(Circuit circuit) throws InfiniteLoopException {
        ArrayList<ICircuitComponent> foundNodes = new ArrayList<>();

        try {
            for (ICircuitComponent in : circuit.getChildren()) {
                detectInfiniteLoopNodes(in, foundNodes);
            }
        } catch (StackOverflowError e) {
            throw new InfiniteLoopException();
        }
    }

    private void detectInfiniteLoopNodes(ICircuitComponent node, ArrayList<ICircuitComponent> foundNodes) throws InfiniteLoopException {
        if (!(node instanceof CircuitComposite)) {
            return;
        }

        CircuitComposite composite = (CircuitComposite) node;

        for(ICircuitComponent n : composite.getChildren()) {
            foundNodes.add(n);
            detectInfiniteLoopNodes(n, foundNodes);
        }
    }
}
