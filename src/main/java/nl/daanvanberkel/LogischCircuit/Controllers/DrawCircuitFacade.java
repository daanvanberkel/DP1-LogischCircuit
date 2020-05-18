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

        for (Map.Entry<String, String> nodeDefinition : nodeDefinitions.entrySet()) {
            try {
                circuitBuilder.addNode(nodeDefinition.getKey(), nodeDefinition.getValue());
            } catch (UnsupportedGateTypeException e) {
                // TODO: Show error message when unsupported gate detected
            }
        }

        for(Map.Entry<String, String> nodeConnection : nodeConnections.entrySet()) {
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
            errorMessage = "Cannot show circuit with infinite loop!";
            return;
        } catch (ProbeNotReachedException e) {
            errorMessage = "Cannot show circuit with unreachable probes!";
            return;
        }

        circuit.start();
    }

    private void prepareFactory(){
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

    public Circuit getCircuit(){
        return circuit;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
