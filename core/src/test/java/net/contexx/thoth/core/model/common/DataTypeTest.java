package net.contexx.thoth.core.model.common;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static net.contexx.thoth.core.model.common.DataType.*;
import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {

    @Test
    void stringTest() {
        assertEquals("foobar", STRING.toString("foobar"));
        assertEquals("foobar", STRING.valueOf("foobar"));
    }

    @Test
    void numberTest() {
        assertEquals("42", NUMBER.toString(42));
        assertEquals(42, NUMBER.valueOf("42"));
    }

    @Test
    void booleanTest() {
        assertEquals("true", BOOLEAN.toString(true));
        assertEquals(true, BOOLEAN.valueOf("true"));
    }

    @Test
    void instantTest() {
        //given
        final Instant givenInstant = Instant.now();

        //when
        final Instant resultingInstant = DataType.INSTANT.valueOf(DataType.INSTANT.toString(givenInstant));

        //then
        assertEquals(givenInstant, resultingInstant);
    }

    @Test
    void testFind() {
        assertSame(STRING, DataType.find(String.class).get());
        assertSame(NUMBER, DataType.find(Integer.class).get());
        assertSame(BOOLEAN, DataType.find(Boolean.class).get());
        assertSame(INSTANT, DataType.find(Instant.class).get());
        assertTrue(DataType.find(DataTypeTest.class).isEmpty());
    }
}