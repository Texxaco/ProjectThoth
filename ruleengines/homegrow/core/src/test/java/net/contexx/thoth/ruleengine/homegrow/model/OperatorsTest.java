package net.contexx.thoth.ruleengine.homegrow.model;

import org.junit.jupiter.api.Test;

import static net.contexx.thoth.ruleengine.homegrow.model.Operators.*;
import static org.junit.jupiter.api.Assertions.*;

class OperatorsTest {

    @Test
    void getName() {
        assertEquals("REGEXP", REGEXP.getName());
    }

    @Test
    void checkEquals() {
        assertTrue(EQUALS.check("a String", "a String"));
        assertFalse(EQUALS.check("a String", "another String"));
    }

    @Test
    void checkSame() {
        //given
        final String A_STRING = "a String";
        final String ANOTHER_STRING = "another String";

        //when
        assertTrue(SAME.check(A_STRING, A_STRING));
        assertFalse(SAME.check(A_STRING, ANOTHER_STRING));
    }

    @Test
    void checkGreaterThen() {
        assertTrue( GREATER_THEN.check(2, 1));
        assertFalse(GREATER_THEN.check(1, 1));
        assertFalse(GREATER_THEN.check(0, 1));
    }

    @Test
    void checkLowerThen() {
        assertTrue( LOWER_THEN.check(1, 2));
        assertFalse(LOWER_THEN.check(1, 1));
        assertFalse(LOWER_THEN.check(1, 0));
    }

    @Test
    void find() {
        //given
        //when
        final Operator<?> same = Operators.find("SAME").orElseGet(() -> {fail("SAME not found"); return null;});
        //then
        assertSame(Operators.SAME, same);
    }
}