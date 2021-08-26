package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Manager;

import javax.naming.OperationNotSupportedException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;

    public DbServiceManagerImpl(TransactionRunner transactionRunner, DataTemplate<Manager> managerDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
    }

    @Override
    public Manager saveManager(Manager manager) {
        return transactionRunner.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel());
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            try {
                managerDataTemplate.update(connection, manager);
            } catch (OperationNotSupportedException e) {
                e.printStackTrace();
            }
            log.info("updated manager: {}", manager);
            return manager;
        });
    }

    @Override
    public Optional<Manager> getManager(long no) {
        return transactionRunner.doInTransaction(connection -> {
            Optional<Manager> managerOptional = null;
            try {
                managerOptional = managerDataTemplate.findById(connection, no);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            log.info("manager: {}", managerOptional);
            return managerOptional;
        });
    }

    @Override
    public List<Manager> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("managerList:{}", managerList);
            return managerList;
       });
    }
}
