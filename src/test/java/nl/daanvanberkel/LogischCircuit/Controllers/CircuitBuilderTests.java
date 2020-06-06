package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Exceptions.InfiniteLoopException;
import nl.daanvanberkel.LogischCircuit.Exceptions.InvalidNodeConnectionException;
import nl.daanvanberkel.LogischCircuit.Exceptions.UnsupportedGateTypeException;
import nl.daanvanberkel.LogischCircuit.Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircuitBuilderTests {

    private NodeFactory nodeFactory;
    private CircuitBuilder circuitBuilder;

    @BeforeEach
    public void createNodeFactoryAndCircuitBuilder() {
        nodeFactory = new NodeFactory();
        nodeFactory.addNodeType("IN", InputHighNode.class);
        nodeFactory.addNodeType("AND", AndGate.class);
        nodeFactory.addNodeType("OR", OrGate.class);

        circuitBuilder = new CircuitBuilder(nodeFactory);
    }

    @Test
    public void addNodeShouldThrowExceptionWhenAddingUnknownNodeType() {
        Exception exception = assertThrows(UnsupportedGateTypeException.class, () -> {
            circuitBuilder.addNode("NODE1", "UNKNOWN");
        });

        assertTrue(exception.getMessage().contains("UNKNOWN"));
    }

    @Test
    public void connectNodesShouldThrowExceptionWhenFromNodeDoesntExist() {
        try {
            circuitBuilder.addNode("NODE1", "AND");
        } catch (UnsupportedGateTypeException e) {
            fail("The AND node type is always present in these tests");
        }

        Exception exception = assertThrows(InvalidNodeConnectionException.class, () -> {
            circuitBuilder.connectNodes("NODE0", "NODE1");
        });

        assertTrue(exception.getMessage().contains("doesn't exist"));
    }

    @Test
    public void connectNodesShouldThrowExceptionWhenToNodeDoesntExist() {
        try {
            circuitBuilder.addNode("NODE1", "AND");
        } catch (UnsupportedGateTypeException e) {
            fail("The AND node type is always present in these tests");
        }

        Exception exception = assertThrows(InvalidNodeConnectionException.class, () -> {
            circuitBuilder.connectNodes("NODE1", "NODE2");
        });

        assertTrue(exception.getMessage().contains("doesn't exist"));
    }

    @Test
    public void connectNodesShouldThrowExceptionWhenFromNodeNotACircuitComposite() {
        nodeFactory.addNodeType("PROBE", OutputNode.class);

        try {
            circuitBuilder.addNode("PROBE1", "PROBE");
            circuitBuilder.addNode("NODE1", "AND");
        } catch (UnsupportedGateTypeException e) {
            fail("The AND and PROBE node types are always present in these tests");
        }

        Exception exception = assertThrows(InvalidNodeConnectionException.class, () -> {
            circuitBuilder.connectNodes("PROBE1", "NODE1");
        });

        assertTrue(exception.getMessage().contains("The from node cannot contain children"));
    }

    @Test
    public void buildCircuitShouldThrowExceptionWhenInfiniteLoopDetected() {
        try {
            circuitBuilder.addNode("IN", "IN");
            circuitBuilder.addNode("NODE1", "AND");
            circuitBuilder.addNode("NODE2", "OR");

            circuitBuilder.connectNodes("IN", "NODE1");
            circuitBuilder.connectNodes("NODE1", "NODE2");
            circuitBuilder.connectNodes("NODE2", "NODE1");
        } catch (UnsupportedGateTypeException e) {
            fail("The IN, AND and OR node types are always present in these tests");
        } catch (InvalidNodeConnectionException e) {
            fail("Node connections are valid");
        }

        assertThrows(InfiniteLoopException.class, () -> {
            circuitBuilder.buildCircuit();
        });
    }

    @Test
    public void buildCircuitShouldReturnValidCircuitWhenAllDataIsValid() {
        try {
            circuitBuilder.addNode("IN", "IN");
            circuitBuilder.addNode("NODE1", "AND");
            circuitBuilder.addNode("NODE2", "OR");

            circuitBuilder.connectNodes("IN", "NODE1");
            circuitBuilder.connectNodes("NODE1", "NODE2");
        } catch (UnsupportedGateTypeException e) {
            fail("The IN, AND and OR node types are always present in these tests");
        } catch (InvalidNodeConnectionException e) {
            fail("Node connections are valid");
        }

        Circuit circuit;

        try {
            circuit = circuitBuilder.buildCircuit();
        } catch (InfiniteLoopException e) {
            fail("Circuit must be valid");
            return;
        }

        assertEquals(1, circuit.getChildren().size());
        assertEquals("nl.daanvanberkel.LogischCircuit.Models.InputHighNode", circuit.getChildren().get(0).getClass().getName());
        assertTrue(circuit.getChildren().get(0) instanceof CircuitComposite);

        CircuitComposite composite = (CircuitComposite) circuit.getChildren().get(0);

        assertEquals(1, composite.getChildren().size());
        assertEquals("nl.daanvanberkel.LogischCircuit.Models.AndGate", composite.getChildren().get(0).getClass().getName());
    }
}
