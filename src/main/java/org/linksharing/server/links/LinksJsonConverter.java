package org.linksharing.server.links;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

@Converter(autoApply = true)
public class LinksJsonConverter implements AttributeConverter<Map<String, Link>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<Map<String, Link>> TYPE_REF = new TypeReference<>() {
    };

    @Override
    public String convertToDatabaseColumn(Map<String, Link> links) {
        try {
            return MAPPER.writeValueAsString(links);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Link> convertToEntityAttribute(String dbData) {
        try {
            return MAPPER.readValue(dbData, TYPE_REF);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
