package com.victor.cardverification.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper myObjectMapper = new ObjectMapper();
            myObjectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            return myObjectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T objectFromResponseStr(String response, String jsonPathStr) {
        // TODO Auto-generated method stub
        return JsonPath.parse(response).read(jsonPathStr);
    }

}
