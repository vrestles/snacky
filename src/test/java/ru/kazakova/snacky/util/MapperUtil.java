package ru.kazakova.snacky.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kazakova.snacky.model.business.Product;

public class MapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Product deserializeProduct(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Product.class);
    }
}
