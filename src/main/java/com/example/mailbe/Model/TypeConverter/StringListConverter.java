package com.example.mailbe.Model.TypeConverter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Collections.emptyList;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings != null ? String.join(SPLIT_CHAR, strings) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return s != null ? new ArrayList<>(List.of(s.split(SPLIT_CHAR))) : emptyList();
    }
}
