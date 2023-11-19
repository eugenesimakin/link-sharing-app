package org.linksharing.server.links;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class LinksJsonConverter implements AttributeConverter<Map<String, Link>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<Map<String, Link>> typeRef = new TypeReference<>() {
    };

    @Override
    public String convertToDatabaseColumn(Map<String, Link> links) {
        try {
            return mapper.writeValueAsString(links);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Link> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
