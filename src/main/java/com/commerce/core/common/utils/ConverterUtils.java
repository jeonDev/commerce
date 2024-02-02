package com.commerce.core.common.utils;

import com.commerce.core.common.exception.CommerceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 변환 Utils
 */
public class ConverterUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * String To Generic Object Converter
     */
    public static <T> T strToObjectConverter(String data, Class<T> tClass) {
        try {
            return objectMapper.readValue(data, tClass);
        } catch (JsonProcessingException e) {
            throw new CommerceException(e);
        }
    }

    /**
     * String To Json Type Data
     */
    public static String strToJsonData(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new CommerceException(e);
        }
    }
}
