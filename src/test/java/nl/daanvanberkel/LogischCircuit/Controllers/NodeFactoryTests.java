package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.CircuitComposite;
import nl.daanvanberkel.LogischCircuit.Models.ICircuitComponent;
import nl.daanvanberkel.LogischCircuit.Models.InputLowNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeFactoryTests {

    private NodeFactory nodeFactory;

    @BeforeEach
    private void createNodeFactory() {
        nodeFactory = new NodeFactory();
    }

    @Test
    public void createNodeShouldReturnNullWhenNodeTypeUnknown() {
        ICircuitComponent circuitComponent = nodeFactory.createNode("UNKNOWN");

        assertNull(circuitComponent);
    }

    @Test
    public void createNodeShouldReturnNullWhenTryingToInstantiateAnAbstractClass() {
        nodeFactory.addNodeType("ABSTRACT", CircuitComposite.class);

        ICircuitComponent circuitComponent = nodeFactory.createNode("ABSTRACT");

        assertNull(circuitComponent);
    }

    @Test
    public void createNodeShouldReturnRightTypeIsTypeIsKnown() {
        nodeFactory.addNodeType("IN", InputLowNode.class);

        ICircuitComponent circuitComponent = nodeFactory.createNode("IN");

        assertTrue(circuitComponent instanceof InputLowNode);
    }
}
