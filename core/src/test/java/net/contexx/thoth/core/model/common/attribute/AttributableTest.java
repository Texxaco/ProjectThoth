package net.contexx.thoth.core.model.common.attribute;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static net.contexx.thoth.core.model.common.DataType.NUMBER;
import static net.contexx.thoth.core.model.common.DataType.STRING;
import static org.junit.jupiter.api.Assertions.*;

class AttributableTest {

    private static final Attribute<String> ATTR_STRING = new Attribute<>("test", "TestStringAttrib", STRING);
    private static final Attribute<Integer> ATTR_NUMBER = new Attribute<>("test", "TestNumberAttrib", NUMBER);

    @Test
    void getAttributeValues() {
        //given
        final Set<AttributeValue<?>> givenAttributes = Collections.singleton(new AttributeValue<String>(ATTR_STRING, "foobar"));
        final Attributable attributable = new Attributable(givenAttributes) {};
        attributable.setValue(ATTR_NUMBER, 1);


        //when
        final Set<AttributeValue<?>> attributeValues = attributable.getAttributeValues();

        //then
        assertEquals(2, attributeValues.size());
        assertEquals(1, attributeValues.parallelStream().filter(av -> av.getAttribute() == ATTR_STRING).count());
        assertEquals(1, attributeValues.parallelStream().filter(av -> av.getAttribute() == ATTR_NUMBER).count());
    }

    @Test
    void getValue() {
        //given
        final String givenValue = "foobar";
        final Attributable attributable = new Attributable(Collections.singleton(new AttributeValue<String>(ATTR_STRING, givenValue))) {};

        //when
        final String actualValue = attributable.getValue(ATTR_STRING);

        //then
        assertEquals(givenValue, actualValue);
    }

    @Test
    void setValue() {
        //given
        final String initialValue = "foo";
        final String changeValue = "bar";
        final Attributable attributable = new Attributable(Collections.singleton(new AttributeValue<String>(ATTR_STRING, initialValue))) {};

        //when
        attributable.setValue(ATTR_STRING, changeValue);

        //then
        assertEquals(changeValue, attributable.getValue(ATTR_STRING));
    }
}