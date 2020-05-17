package nl.daanvanberkel.LogischCircuit.Models;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AndGateTests {

    @Test
    public void andGateShouldReturnTrueWhenAllInputsAreTrue() {
        AndGate input1 = new AndGate();
        AndGate input2 = new AndGate();

        AndGate gate = new AndGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, true);

        assertTrue(gate.computeResult());
    }

    @Test
    public void andGateShouldReturnFalseWhenOneInputIsFalse() {
        AndGate input1 = new AndGate();
        AndGate input2 = new AndGate();

        AndGate gate = new AndGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void andGateShouldReturnTheRightType() {
        AndGate gate = new AndGate();

        assertEquals("AND", gate.getNodeType());
    }
}
