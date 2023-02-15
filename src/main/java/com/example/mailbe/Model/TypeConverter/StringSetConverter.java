package com.example.mailbe.Model.TypeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.List;

@Converter
public class StringSetConverter implements AttributeConverter<HashSet<String>, String> {
    public static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(HashSet<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public HashSet<String> convertToEntityAttribute(String string) {
        return string != null ? new HashSet<>(List.of(string.split(SPLIT_CHAR))) : new HashSet<>();
    }

}
