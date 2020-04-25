package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class CircuitController {
    private Circuit circuit;
    private CircuitView circuitView;

    private HashMap<String, Node> nodes = new HashMap<>();
    private boolean parsingNodeDefinition = true;

    public void buildCircuitFromFile(String path) {
        circuit = new Circuit();

        try {
            Stream<String> stream = Files.lines(Paths.get(path));

            stream
                .forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Handle exception
        }

        circuit.start();
    }

    private void handleLine(String line) {
        if (line.length() < 1) {
            parsingNodeDefinition = false;
            return;
        }

        if (line.charAt(0) == '#') {
            return;
        }

        if (parsingNodeDefinition) {
            // Add new node to nodes map
            boolean parsingName = true;
            String nodeName = "";
            String nodeType = "";

            for(char c: line.toCharArray()) {
                if (c == ':') {
                    // Found the end of the name
                    parsingName = false;
                    continue;
                }

                if (c == ';') {
                    // Found end of the statement
                    break;
                }

                if (c == ' ') {
                    // Skip is character is an empty space
                    continue;
                }

                if (parsingName) {
                    nodeName += c;
                } else {
                    nodeType += c;
                }
            }

            nodeName = nodeName.trim();
            nodeType = nodeType.trim();

            Node node;

            try {
                node = createNode(nodeName, nodeType);
            } catch (UnsupportedGateTypeException e) {
                e.printStackTrace(); // TODO: Handle exception
                return;
            }

            nodes.put(nodeName, node);

            if (nodeType.equals("INPUT_HIGH") || nodeType.equals("INPUT_LOW")) {
                circuit.addInputNode(node);
            }

            if (nodeType.equals("PROBE")) {
                circuit.addOutputNode(node);
            }
        } else {
            // Connect nodes to each other
            boolean parsingName = true;
            String nodeName = "";
            String connectingNodeNames = "";

            for(char c: line.toCharArray()) {
                if (c == ':') {
                    // Found the end of the name
                    parsingName = false;
                    continue;
                }

                if (c == ';') {
                    // Found end of the statement
                    break;
                }

                if (c == ' ') {
                    // Skip is character is an empty space
                    continue;
                }

                if (parsingName) {
                    nodeName += c;
                } else {
                    connectingNodeNames += c;
                }
            }

            nodeName = nodeName.trim();
            connectingNodeNames = connectingNodeNames.trim();

            Node node = nodes.get(nodeName);

            for(String connectingNodeName : connectingNodeNames.split(",")) {
                Node connectingNode = nodes.get(connectingNodeName);

                node.addOutputNode(connectingNode);
                connectingNode.addInputNode(node);
            }
        }
    }

    private Node createNode(String name, String nodeType) throws UnsupportedGateTypeException {
        Node node;
        
        switch (nodeType) {
            case "INPUT_HIGH":
                node = new InputNode(true);
                break;
            case "INPUT_LOW":
                node = new InputNode(false);
                break;
            case "PROBE":
                node = new OutputNode();
                break;
            case "AND":
                node = new AndGate();
                break;
            case "OR":
                node = new OrGate();
                break;
            case "NOT":
                node = new NotGate();
                break;
            case "NAND":
                node = new NandGate();
                break;
            case "NOR":
                node = new NorGate();
                break;
            case "XOR":
                node = new XorGate();
                break;
            default:
                throw new UnsupportedGateTypeException(nodeType);
        }

        node.setName(name);

        return node;
    }
}
