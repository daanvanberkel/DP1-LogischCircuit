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
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1,true);
        gate.setValue(input2, true);

        assertTrue(gate.computeResult());
    }

    @Test
    public void andGateShouldReturnFalseWhenOneInputIsFalse() {
        AndGate input1 = new AndGate();
        AndGate input2 = new AndGate();

        AndGate gate = new AndGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void andGateShouldReturnTheRightType() {
        AndGate gate = new AndGate();

        assertEquals("AND", gate.getType());
    }
}
