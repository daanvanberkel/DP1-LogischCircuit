package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NorGateTests {

    @Test
    public void norGateShouldReturnTrueWhenAllInputsAreFalse() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, false);
        gate.setInputValueFor(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnFalseWhenOneInputIsTrue() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnFalseWhenAllInputsAreTrue() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnRightType() {
        NorGate gate = new NorGate();

        assertEquals("NOR", gate.getNodeType());
    }
}
