package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotGateTests {

    @Test
    public void notGateShouldReturnFalseWhenInputIsTrue() {
        NotGate input = new NotGate();

        NotGate gate = new NotGate();
        gate.addChild(input);
        gate.setValue(input, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void notGateShouldReturnTrueWhenInputIsFalse() {
        NotGate input = new NotGate();

        NotGate gate = new NotGate();
        gate.addChild(input);
        gate.setValue(input, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void notGateShouldReturnRightType() {
        NotGate gate = new NotGate();

        assertEquals("NOT", gate.getType());
    }
}
