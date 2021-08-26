package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Address;

import java.util.List;
import java.util.Optional;

public class DBServiceAddressImpl implements DBService<Address> {
    private static final Logger log = LoggerFactory.getLogger(DBServiceAddressImpl.class);

    private final DataTemplate<Address> addressDataTemplate;
    private final TransactionManager transactionManager;

    public DBServiceAddressImpl(TransactionManager transactionManager, DataTemplate<Address> addressDataTemplate) {
        this.addressDataTemplate = addressDataTemplate;
        this.transactionManager = transactionManager;
    }

    @Override
    public Address save(Address object) {
        return transactionManager.doInTransaction(session -> {
            var addressCloned = object.clone();
            if (object.getId() == null) {
                addressDataTemplate.insert(session, addressCloned);
                log.info("created address: {}", addressCloned);
                return addressCloned;
            }
            addressDataTemplate.update(session, addressCloned);
            log.info("updated address: {}", addressCloned);
            return addressCloned;
        });
    }

    @Override
    public Optional<Address> getById(long id) {
        return transactionManager.doInTransaction(session -> {
            var addressOptional = addressDataTemplate.findById(session, id);
            log.info("address: {}", addressOptional);
            return addressOptional;
        });
    }

    @Override
    public List<Address> findAll() {
        return transactionManager.doInTransaction(session -> {
            var addresstList = addressDataTemplate.findAll(session);
            log.info("addressList:{}", addresstList);
            return addresstList;
        });
    }
}
