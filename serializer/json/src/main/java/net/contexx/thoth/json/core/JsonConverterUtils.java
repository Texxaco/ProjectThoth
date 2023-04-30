package net.contexx.thoth.json.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.contexx.thoth.core.model.phasea.Domain;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JsonConverterUtils {

    private JsonConverterUtils() { /*no instanciation*/ }

    public static <T> Set<T> loadArray(JsonNode jsonNode, AbstractJsonConverter<T> converter, Domain<?> domain, int version) throws IOException {
        final Set<T> result = new HashSet<>(jsonNode.size());
        for(int i=0, s=jsonNode.size();i<s;i++){
            result.add(converter.load(jsonNode.get(i), domain, version));
        }
        return result;
    }

    //todo kann diese Methode weg?
//    public static <T> Set<T> loadArray(JsonNode jsonNode, Domain<?> domain, int version, SupportiveOptionalAbstractJsonConverter<T>...converters) {
//        final Set<T> result = new HashSet<>(jsonNode.size());
//        final List<SupportiveOptionalAbstractJsonConverter<T>> list = Arrays.asList(converters);
//        for(int i=0, s=jsonNode.size();i<s;i++){
//            final JsonNode elem = jsonNode.get(i);
//            final Optional<SupportiveOptionalAbstractJsonConverter<T>> converter = list.stream().filter(c -> c.supports(elem, domain, version)).findFirst();
//            converter.ifPresent(c -> c.load(elem, domain, version).ifPresent(result::add));
//        }
//        return result;
//    }

    public static <T> Set<T> loadArray(JsonNode jsonNode, OptionalAbstractJsonConverter<T> converter, Domain<?> domain, int version) throws IOException {
        final Set<T> result = new HashSet<>(jsonNode.size());
        for(int i=0, s=jsonNode.size();i<s;i++){
            converter.load(jsonNode.get(i), domain, version).ifPresent(result::add);
        }
        return result;
    }

    public static <T, U extends Comparable<? super U>> void writeArray(String fieldName, Collection<T> entrys, Function<? super T, ? extends U> keyExtractor, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart(fieldName);
        for (T entry : entrys.stream().sorted(Comparator.comparing(keyExtractor)).collect(Collectors.toList())){
            gen.writeObject(entry);
        }
        gen.writeEndArray();
    }

    public static <T> void writeArray(String fieldName, List<T> entrys, JsonGenerator gen) throws IOException {
        gen.writeArrayFieldStart(fieldName);
        for (T entry : entrys){
            gen.writeObject(entry);
        }
        gen.writeEndArray();
    }
}
