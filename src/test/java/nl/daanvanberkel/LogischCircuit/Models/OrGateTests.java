package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrGateTests {

    @Test
    public void orGateShouldReturnFalseWhenAllInputsAreFalse() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, false);
        gate.setInputValueFor(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnTrueWhenOneInputIsTrue() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnTrueWhenAllInputsAreTrue() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, true);

        assertTrue(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnRightType() {
        OrGate gate = new OrGate();

        assertEquals("OR", gate.getNodeType());
    }

}
