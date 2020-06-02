package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NorGateTests {

    @Test
    public void norGateShouldReturnTrueWhenAllInputsAreFalse() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, false);
        gate.setValue(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnFalseWhenOneInputIsTrue() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnFalseWhenAllInputsAreTrue() {
        NorGate input1 = new NorGate();
        NorGate input2 = new NorGate();

        NorGate gate = new NorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void norGateShouldReturnRightType() {
        NorGate gate = new NorGate();

        assertEquals("NOR", gate.getType());
    }
}
