package com.zakharov.handler;

import com.zakharov.annotation.FieldInfo;
import com.zakharov.annotation.TypeInfo;
import com.zakharov.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TypeInfoHandler {

    public String getTypeName(Class<User> clazz) {
        TypeInfo nameAnnotation = clazz.getAnnotation(TypeInfo.class);
        return nameAnnotation.name();
    }
}
