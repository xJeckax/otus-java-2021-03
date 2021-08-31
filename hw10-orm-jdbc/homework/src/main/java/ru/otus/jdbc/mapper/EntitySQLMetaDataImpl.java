package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select %s from %s where %s = ?",
                entityClassMetaData.getAllFields().stream().map(Field::getName).collect(Collectors.joining(", ")),
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        String[] strings = new String[entityClassMetaData.getFieldsWithoutId().size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "?";
        }
        return String.format("insert into %s(%s) values (%s)",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(", ")),
                String.join(", ", strings));
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s = ? where %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(", ")),
                entityClassMetaData.getIdField().getName());
    }
}