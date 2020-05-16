package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.InfiniteLoopException;
import nl.daanvanberkel.LogischCircuit.Exceptions.InvalidNodeConnectionException;
import nl.daanvanberkel.LogischCircuit.Exceptions.ProbeNotReachedException;
import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;
import nl.daanvanberkel.LogischCircuit.Views.MainView;
import nl.daanvanberkel.LogischCircuit.Views.NodeView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map.Entry;

public class CircuitController extends MouseAdapter {
    private final ReadFileController readFileController = new ReadFileController();
    private final NodeFactory nodeFactory = new NodeFactory();
    private MainView main;
    private CircuitView circuitView;
    private Circuit circuit;

    public CircuitController() {
        // TODO: Use dependency injection for all the node types
        nodeFactory.addNodeType("AND", AndGate.class);
        nodeFactory.addNodeType("INPUT_LOW", InputLowNode.class);
        nodeFactory.addNodeType("INPUT_HIGH", InputHighNode.class);
        nodeFactory.addNodeType("NAND", NandGate.class);
        nodeFactory.addNodeType("NOR", NorGate.class);
        nodeFactory.addNodeType("NOT", NotGate.class);
        nodeFactory.addNodeType("OR", OrGate.class);
        nodeFactory.addNodeType("PROBE", OutputNode.class);
        nodeFactory.addNodeType("XOR", XorGate.class);
    }

    public void showCircuit(String path) {
        HashMap<String, String> nodeDefinitions = readFileController.getNodes(path);
        HashMap<String, String> nodeConnections = readFileController.getNodeConnections(path);

        CircuitBuilder circuitBuilder = new CircuitBuilder(nodeFactory);

        for (Entry<String, String> nodeDefinition : nodeDefinitions.entrySet()) {
            try {
                circuitBuilder.addNode(nodeDefinition.getKey(), nodeDefinition.getValue());
            } catch (UnsupportedGateTypeException e) {
                // TODO: Show error message when unsupported gate detected
            }
        }

        for(Entry<String, String> nodeConnection : nodeConnections.entrySet()) {
            String[] connections = nodeConnection.getValue().split(",");

            try {
                circuitBuilder.connectNodes(nodeConnection.getKey(), connections);
            } catch (InvalidNodeConnectionException e) {
                // TODO: Show error message when nodes cannot connect
            }
        }

        try {
            circuit = circuitBuilder.buildCircuit();
        } catch (InfiniteLoopException e) {
            System.out.println("Cannot show circuit with infinite loop!");
            // TODO: Show alert to user
            return;
        } catch (ProbeNotReachedException e) {
            System.out.println("Cannot show circuit with unreachable probes!");
            // TODO: Show alert to user
            return;
        }

        circuit.start();

        circuitView = new CircuitView(circuit, this);
        circuitView.drawCircuit();

        main.showCircuit(circuitView);
    }

    public void showWindow(){
        main = new MainView(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle clicks on NodeView
        if (e.getSource() instanceof NodeView) {
            NodeView view = (NodeView) e.getSource();
            Node node = view.getNode();

            // Only toggle node if the clicked node is an inputNode
            if (node instanceof InputNode) {
                InputNode inputNode = (InputNode) node;

                inputNode.toggleValue();

                // Recalculate the output and repaint the circuit
                circuit.start();
                circuitView.repaint();
            }
        }
    }
}
