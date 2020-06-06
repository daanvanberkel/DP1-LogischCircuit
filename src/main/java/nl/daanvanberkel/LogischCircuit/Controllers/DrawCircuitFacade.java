package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.InfiniteLoopException;
import nl.daanvanberkel.LogischCircuit.Exceptions.InvalidNodeConnectionException;
import nl.daanvanberkel.LogischCircuit.Exceptions.ProbeNotReachedException;
import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.*;

import java.util.HashMap;
import java.util.Map;

public class DrawCircuitFacade {
    private final ReadFileController readFileController = new ReadFileController();
    private final NodeFactory nodeFactory = new NodeFactory();
    private Circuit circuit;

    private String errorMessage;

    public void createCircuit(String path){
        errorMessage = "";

        prepareFactory();

        HashMap<String, String> nodeDefinitions = readFileController.getNodes(path);
        HashMap<String, String> nodeConnections = readFileController.getNodeConnections(path);

        CircuitBuilder circuitBuilder = new CircuitBuilder(nodeFactory);

        addNodeToCircuit(circuitBuilder, nodeDefinitions);
        connectNodesInCircuit(circuitBuilder, nodeConnections);

        try {
            circuit = circuitBuilder.buildCircuit();
        } catch (InfiniteLoopException e) {
            errorMessage = "Cannot show circuit with infinite loop!";
            return;
        }

        circuit.start();
    }

    private void addNodeToCircuit(CircuitBuilder circuitBuilder, HashMap<String, String> nodeDefinitions){
        for (Map.Entry<String, String> nodeDefinition : nodeDefinitions.entrySet()) {
            try {
                circuitBuilder.addNode(nodeDefinition.getKey(), nodeDefinition.getValue());
            } catch (UnsupportedGateTypeException e) {
                errorMessage = "A unsupported gate has been found";
                return;
            }
        }
    }

    private void connectNodesInCircuit(CircuitBuilder circuitBuilder, HashMap<String, String> nodeConnections){
        for(Map.Entry<String, String> nodeConnection : nodeConnections.entrySet()) {
            String[] connections = nodeConnection.getValue().split(",");

            try {
                circuitBuilder.connectNodes(nodeConnection.getKey(), connections);
            } catch (InvalidNodeConnectionException e) {
                errorMessage = "Nodes cannot be connected";
                return;
            }
        }
    }

    private void prepareFactory(){
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

    public Circuit getCircuit(){
        return circuit;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
