package com.zakharov.handler;

import com.zakharov.annotation.FieldInfo;
import com.zakharov.entity.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class FieldInfoHandler {

    public Map<String, String> getFieldNameMap(Class<User> clazz) {
        HashMap<String, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            FieldInfo annotation = field.getAnnotation(FieldInfo.class);
            if (annotation != null) {
                map.put(field.getName(), annotation.name());
            }
        }
        return map;
    }
}
