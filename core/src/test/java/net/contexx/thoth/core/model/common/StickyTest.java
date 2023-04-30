package net.contexx.thoth.core.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StickyTest {
    private class StickyDummy extends Sticky{}
    private static final Key<String> KEY_ONE = new Key<>("KEY_ONE");

    @Test
    void put() {
        //given
        final String value = "FOO";
        final StickyDummy sticky = new StickyDummy();

        //when
        sticky.stick(KEY_ONE, value);

        //then
        assertSame(value, sticky.get(KEY_ONE));
    }

    @Test
    void testPutOverrides() {
        //given
        final String value1 = "FOO";
        final String value2 = "BAR";
        final StickyDummy sticky = new StickyDummy();
        sticky.stick(KEY_ONE, value1);
        assertEquals(value1, sticky.get(KEY_ONE));

        //when
        sticky.stick(KEY_ONE, value2);

        //then
        assertSame(value2, sticky.get(KEY_ONE));
    }

    @Test
    void get() {
        //given
        final String value = "FOO";
        final StickyDummy sticky = new StickyDummy();
        sticky.stick(KEY_ONE, value);
        assertEquals(value, sticky.get(KEY_ONE));

        //when
        final String resultValue = sticky.get(KEY_ONE);
        
        //then
        assertEquals(value, resultValue);
    }

    @Test
    void testGetWhenNew() {
        //given
        final StickyDummy sticky = new StickyDummy();

        //when
        final String value = sticky.get(KEY_ONE);

        //then
        assertNull(value);
    }

    @Test
    void getOrDefault() {
        //given
        final String defaultValue = "FOO";
        final StickyDummy sticky = new StickyDummy();
        assertNull(sticky.get(KEY_ONE));
        
        //when
        final String value = sticky.getOrDefault(KEY_ONE, defaultValue);

        //then
        assertSame(defaultValue, value);
    }

    @Test
    void contains() {
        //given
        final StickyDummy sticky = new StickyDummy();
        sticky.stick(KEY_ONE, "FOO");

        //when
        final boolean contains = sticky.isSticked(KEY_ONE);

        //then
        assertTrue(contains);
    }

    @Test
    void testNotContaining() {
        //given
        final StickyDummy sticky = new StickyDummy();

        //when
        final boolean contains = sticky.isSticked(KEY_ONE);

        //then
        assertFalse(contains);
    }

    @Test
    void remove() {
        //given
        final String value = "FOO";
        final StickyDummy sticky = new StickyDummy();
        sticky.stick(KEY_ONE, value);
        assertEquals(value, sticky.get(KEY_ONE));
        assertTrue(sticky.isSticked(KEY_ONE));

        //when
        sticky.remove(KEY_ONE);

        //then
        assertFalse(sticky.isSticked(KEY_ONE));
        assertNull(sticky.get(KEY_ONE));
    }
}