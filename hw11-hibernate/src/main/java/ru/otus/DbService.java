package ru.otus;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.ArrayList;
import java.util.List;

public class DbService {

    private static final Logger log = LoggerFactory.getLogger(DbService.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var sessionFactory = getSessionFactory();
        var dbServiceClient = getClientDBService(sessionFactory, Client.class);


        var address = new Address("Pushkin street");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(new Phone("+7900-666-66-66"));
        phoneList.add(new Phone("+7900-666-66-55"));

        var firstClient = new Client();
        firstClient.setName("Egor");
        firstClient.setAddress(address);
        firstClient.setPhoneList(phoneList);

        var address1 = new Address("Lenin street");
        List<Phone> phoneList1 = new ArrayList<>();
        phoneList1.add(new Phone("+7900-666-66-33"));
        phoneList1.add(new Phone("+7900-666-66-25"));

        var secondClient = new Client();
        secondClient.setName("Masha");
        secondClient.setAddress(address1);
        secondClient.setPhoneList(phoneList1);


        var firstClientClone = dbServiceClient.save(firstClient);

        var secondClientClone = dbServiceClient.save(secondClient);

        var clientFirstSelected = dbServiceClient.getById(firstClientClone.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + firstClientClone.getId()));
        log.info("clientFirstSelected:{}", firstClientClone);

        dbServiceClient.save(new Client(clientFirstSelected.getId(), "dbServiceSecondUpdated",clientFirstSelected.getAddress().clone()));
        var clientUpdated = dbServiceClient.getById(secondClientClone.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + secondClientClone.getId()));
        log.info("clientUpdated:{}", clientUpdated);
        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }

    private static SessionFactory getSessionFactory() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        return HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
    }

    private static DbServiceClientImpl getClientDBService(SessionFactory sessionFactory, Class aClass) {
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(aClass);

        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
