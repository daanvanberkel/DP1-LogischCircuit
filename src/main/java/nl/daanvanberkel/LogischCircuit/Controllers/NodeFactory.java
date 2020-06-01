package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.ICircuitComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class NodeFactory {

    private final HashMap<String, Class<? extends ICircuitComponent>> nodeTypes = new HashMap<>();

    public void addNodeType(String name, Class<? extends ICircuitComponent> type) {
        nodeTypes.put(name, type);
    }

    public ICircuitComponent createNode(String name) {
        Class<? extends ICircuitComponent> type = nodeTypes.get(name);

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
