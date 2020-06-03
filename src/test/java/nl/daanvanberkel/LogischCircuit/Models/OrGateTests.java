package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrGateTests {

    @Test
    public void orGateShouldReturnFalseWhenAllInputsAreFalse() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, false);
        gate.setValue(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnTrueWhenOneInputIsTrue() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnTrueWhenAllInputsAreTrue() {
        OrGate input1 = new OrGate();
        OrGate input2 = new OrGate();

        OrGate gate = new OrGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, true);

        assertTrue(gate.computeResult());
    }

    @Test
    public void orGateShouldReturnRightType() {
        OrGate gate = new OrGate();

        assertEquals("OR", gate.getType());
    }

}
