package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XorGateTests {

    @Test
    public void xorGateShouldReturnFalseWhenAllInputsAreFalse() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, false);
        gate.setValue(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void xorGateShouldReturnTrueWhenOneInputIsTrue() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void xorGateShouldReturnFalseWhenAllInputsAreTrue() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void xorShouldReturnRightType() {
        XorGate gate = new XorGate();

        assertEquals("XOR", gate.getType());
    }

}
