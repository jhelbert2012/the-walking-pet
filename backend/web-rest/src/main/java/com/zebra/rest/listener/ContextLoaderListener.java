package com.zebra.rest.listener;

import com.zebra.rest.temp.PersistenceStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextLoaderListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
        PersistenceStorage persistenceStorage = (PersistenceStorage) applicationContext.getBean("PersistenceStorage");
        persistenceStorage.createStorage();
    }
}
