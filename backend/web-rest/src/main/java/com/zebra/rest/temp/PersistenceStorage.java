package com.zebra.rest.temp;

public interface PersistenceStorage {

    public void createStorage();
    
    public Storage getStorage();
    
    public void reloadStorage();
}
