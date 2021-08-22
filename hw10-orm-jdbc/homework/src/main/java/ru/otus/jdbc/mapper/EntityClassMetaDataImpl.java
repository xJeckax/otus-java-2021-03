package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private final String className;
    private final Constructor constructor;
    private final Field[] fields;

    public EntityClassMetaDataImpl(Class aClass) throws NoSuchMethodException {
        this.className = aClass.getSimpleName();
        this.fields = aClass.getDeclaredFields();
        this.constructor = aClass.getConstructor(Long.class,String.class);
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public <T> Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(fields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fieldList = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldList.add(field);
            }
        });
        return fieldList;
    }
}
