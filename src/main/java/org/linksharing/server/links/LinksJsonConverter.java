package org.linksharing.server.links;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class LinksJsonConverter implements AttributeConverter<List<Link>, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<List<Link>> TYPE_REF = new TypeReference<>() {
    };

    @Override
    public String convertToDatabaseColumn(List<Link> links) {
        try {
            return MAPPER.writeValueAsString(links);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Link> convertToEntityAttribute(String dbData) {
        try {
            return MAPPER.readValue(dbData, TYPE_REF);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
