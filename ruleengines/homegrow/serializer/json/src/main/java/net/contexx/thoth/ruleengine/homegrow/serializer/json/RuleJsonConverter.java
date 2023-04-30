package net.contexx.thoth.ruleengine.homegrow.serializer.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.contexx.thoth.core.model.common.DataType;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.core.OptionalAbstractJsonConverter;
import net.contexx.thoth.ruleengine.homegrow.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.contexx.thoth.json.core.JsonConverterUtils.writeArray;

public class RuleJsonConverter<T> extends OptionalAbstractJsonConverter<Rule<?>> {

    private static final String TYPE = "type";
    private static final String RULES = "rules";
    private static final String CONDITION_DATATYPE = "datatype";
    private static final String CONDITION_PROVIDER = "provider";
    private static final String CONDITION_OPERATION = "operation";
    private static final String CONDITION_SHOULDS = "shoulds";

    public RuleJsonConverter() {
        super(Rule.class, true);
    }

    @Override
    public void serialize(Rule<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        if(value instanceof Condition<?, ?> condition) {
            serializeCondition(gen, condition);
        } else if(value instanceof AndRule andRule) {
            gen.writeStringField(TYPE, "AND");
            writeArray(RULES, andRule.getRules(), gen);
        } else if(value instanceof OrRule orRule) {
            gen.writeStringField(TYPE, "OR");
            writeArray(RULES, orRule.getRules(), gen);
        } else {
            //todo throw Exception
        }

        gen.writeEndObject();
    }

    private static <T> void serializeCondition(JsonGenerator gen, Condition<T, ?> condition) throws IOException {
        final DataType<T> dataType = condition.getDataType();

        gen.writeStringField("foobar", "blub");
        gen.writeStringField(TYPE, "condition");
        gen.writeStringField(CONDITION_DATATYPE, dataType.getName());
        gen.writeStringField(CONDITION_PROVIDER, condition.getProvider().getName());
        gen.writeStringField(CONDITION_OPERATION, condition.getOperator().getName());
        writeArray(CONDITION_SHOULDS, condition.getShoulds().parallelStream().map(dataType::toString).collect(Collectors.toList()), gen); //todo
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Optional<Rule<?>> load(JsonNode node, Domain<?> domain, int version) throws IOException {
        final String type = node.get(TYPE).asText();

        switch (type) {
            case "condition" -> {
                final DataType<T> dataType = (DataType<T>) DataType.find(node.get(CONDITION_DATATYPE).asText()).orElseThrow(); //todo Exception
                return Optional.of(new Condition(
                        dataType,
                        domain.findProvider(node.get(CONDITION_PROVIDER).asText()).orElseThrow(() -> new IOException(String.format("Unable to fine Provider '%s'", node.get(CONDITION_PROVIDER).asText()))), //todo Exception
                        Operators.find(node.get(CONDITION_OPERATION).asText()).orElseThrow(() -> new IOException(String.format("Unable to fine Operator '%s'", node.get(CONDITION_OPERATION).asText()))), //todo Exception
                        readShoulds(dataType, node.get(CONDITION_SHOULDS))
                ));
            }
            case "and" -> {
                return Optional.of(new AndRule(loadRules(node.get(RULES), domain, version)));
            }
            case "or" -> {
                return Optional.of(new OrRule(loadRules(node.get(RULES), domain, version)));
            }
            default -> throw new RuntimeException("Unknown Rule type '" + type + "'");
        }
    }

    private List<T> readShoulds(DataType<T> datatype, JsonNode shouldsArray) {
        final List<T> result = new ArrayList<>(shouldsArray.size());
        for(int i=0, s=shouldsArray.size();i<s;i++) {
            result.add(datatype.valueOf(shouldsArray.get(i).asText()));
        }
        return result;
    }

    private List<Rule<?>> loadRules(JsonNode rulesArray, Domain<?> domain, int version) throws IOException {
        final List<Rule<?>> result = new ArrayList<>(rulesArray.size());
        for(int i=0, s=rulesArray.size();i<s;i++) {
            load(rulesArray.get(i), domain, version)
                    .ifPresent(result::add);
        }
        return result;
    }
}
