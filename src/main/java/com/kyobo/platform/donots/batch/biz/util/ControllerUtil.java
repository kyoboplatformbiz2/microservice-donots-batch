package com.kyobo.platform.donots.batch.biz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.json.simple.JSONObject;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ControllerUtil {

    /**
     * Argument와 맵핑되는 Property와 값을 직렬화하여 반환한다
     * 맵핑되는 Property가 없을 경우 빈 값을 반환한다
     * (존재하지 않는 Property가 있더라도 오류는 발생하지 않는다)
     */
    public static MappingJacksonValue serializeEntitySetWithPropertyFilter(Object entitySet, String filterId, Set<String> propertiesToFilter) {
        // Make property filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertiesToFilter);

        // Serialize and apply property filter
        MappingJacksonValue filteredEntitySet = new MappingJacksonValue(entitySet);
        filteredEntitySet.setFilters(new SimpleFilterProvider().addFilter(filterId, filter));

        return filteredEntitySet;
    }

    public static MappingJacksonValue serializeEntitySetWithPropertyFilter(Object entitySet, String filterId, String... propertiesToFilter) {
        // Make property filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertiesToFilter);

        // Serialize and apply property filter
        MappingJacksonValue filteredEntitySet = new MappingJacksonValue(entitySet);
        filteredEntitySet.setFilters(new SimpleFilterProvider().addFilter(filterId, filter));

        return filteredEntitySet;
    }

    public static MappingJacksonValue serializeEntitySetWithAllProperties(Object entitySet) {
        // Make all-property filter
        SimpleBeanPropertyFilter allPropertyFilter = SimpleBeanPropertyFilter.serializeAll();

        // Serialize and apply all-property filter as default
        MappingJacksonValue unfilteredEntitySet = new MappingJacksonValue(entitySet);
        unfilteredEntitySet.setFilters(new SimpleFilterProvider().setDefaultFilter(allPropertyFilter));

        return unfilteredEntitySet;
    }

    public static Object wrapWithDatabodyProperty(Object object) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        registerModuleWithLocalDateTimeSerializerTo(om);
        om.setFilterProvider(new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll()));
        String jsonString = om.writeValueAsString(object);
        jsonString = convertNullToEmptyString(jsonString);
        Object filteredObject= new JSONObject(om.readValue(jsonString, new TypeReference<>(){}));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("databody", filteredObject);

        return (Object) jsonObject;
    }

    public static Object wrapWithDatabodyProperty(List<?> list) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        registerModuleWithLocalDateTimeSerializerTo(om);
        om.setFilterProvider(new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll()));
        String jsonString = om.writeValueAsString(list);
        jsonString = convertNullToEmptyString(jsonString);

        TypeReference<List<?>> listTypeRef = new TypeReference<List<?>>() {};
        List<?> readList = om.readValue(jsonString, listTypeRef);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("databody", readList);

        return (Object) jsonObject;
    }

    public static Object wrapWithDatabodyProperty(MappingJacksonValue mappingJacksonValueWithFilter) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        registerModuleWithLocalDateTimeSerializerTo(om);
        om.setFilterProvider(mappingJacksonValueWithFilter.getFilters());
        String jsonString = om.writeValueAsString(mappingJacksonValueWithFilter);
        jsonString = convertNullToEmptyString(jsonString);
        Object filteredObject= new JSONObject(om.readValue(jsonString, new TypeReference<>(){})).get("value");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("databody", filteredObject);

        return (Object) jsonObject;
    }

    private static void registerModuleWithLocalDateTimeSerializerTo(ObjectMapper om) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        om.registerModule(module);
    }

    private static String convertNullToEmptyString(String jsonString) {
        // JSON의 null 값을 Empty String("")으로 변환한다. (Frontend 요청)
        // FIXME 속성이 레퍼런스 타입인 경우도 null을 Empty String("")으로 변환하므로 유의해야 한다.
        return jsonString.replaceAll("\":null", "\":\"\"");
    }
}
