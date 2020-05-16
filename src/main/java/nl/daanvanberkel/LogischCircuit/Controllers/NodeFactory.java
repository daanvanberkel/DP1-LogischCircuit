package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class NodeFactory {

    private final HashMap<String, Class<? extends Node>> nodeTypes = new HashMap<>();

    public void addNodeType(String name, Class<? extends Node> type) {
        nodeTypes.put(name, type);
    }

    public Node createNode(String name) {
        Class<? extends Node> type = nodeTypes.get(name);

        if (type == null) {
            return null;
        }

        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }
}
