package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                if (rs.next()) {
                    long ID = rs.getLong(entityClassMetaData.getIdField().getName());
                    String name = rs.getString(entityClassMetaData.getFieldsWithoutId().get(0).getName());
                    return (T)entityClassMetaData.getConstructor().newInstance(ID,name);
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
                    Constructor<T> constructor = entityClassMetaData.getConstructor();
                    clientList.add(constructor.newInstance(rs.getInt(1), rs.getString(2)));
                }
                return clientList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            Field field = entityClassMetaData.getFieldsWithoutId().get(0);
            field.setAccessible(true);
            String valueField = (String) field.get(client);
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), Arrays.asList(valueField));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
