package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Arrays.asList(id), rs -> {
            try {
                T object = (T) entityClassMetaData.getConstructor().newInstance();
                if (rs.next()) {
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(object, rs.getObject(field.getName(), field.getType()));

                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new DataTemplateException(e);
                        }
                    });
                    return object;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T object = (T) entityClassMetaData.getConstructor().newInstance();
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(object, rs.getObject(field.getName(), field.getType()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    clientList.add(object);
                }
                return clientList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> arguments = entityClassMetaData.getFieldsWithoutId().stream().map(field -> {
            field.setAccessible(true);
            try {
                return (String) field.get(client);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new DataTemplateException(e);
            }
        }).collect(Collectors.toList());

        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), arguments);
    }

    @Override
    public void update(Connection connection, T client) throws Exception {
        List<Object> arguments = new ArrayList<>();
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            try {
                arguments.add(field.get(client));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new DataTemplateException(e);
            }
        });
        Field filed = entityClassMetaData.getIdField();
        filed.setAccessible(true);
        arguments.add(filed.get(client));

        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), arguments);
    }
}
