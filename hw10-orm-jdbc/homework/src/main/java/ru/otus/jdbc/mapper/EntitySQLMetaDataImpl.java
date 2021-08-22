package ru.otus.jdbc.mapper;

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
        return String.format("select %s, %s from %s where %s = ?",
                entityClassMetaData.getIdField().getName(),
                entityClassMetaData.getFieldsWithoutId().get(0).getName(),
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s(%s) values (?)",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().get(0).getName());
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s = ? where %s = ?",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().get(0).getName(),
                entityClassMetaData.getIdField().getName());
    }
}
