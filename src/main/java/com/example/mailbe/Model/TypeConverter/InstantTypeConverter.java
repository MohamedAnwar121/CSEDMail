package com.example.mailbe.Model.TypeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;

@Converter
public class InstantTypeConverter implements AttributeConverter<Instant, String> {

    @Override
    public String convertToDatabaseColumn(Instant instant) {
        return instant == null ? null : instant.toString();
    }

    @Override
    public Instant convertToEntityAttribute(String string) {
        return string == null ? null : Instant.parse(string);
    }

}
