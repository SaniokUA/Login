package azaza.login.database.mongo.service;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import azaza.login.database.mongo.service.core.BService;

/**
 * Created by Alex on 23.03.2015.
 */
public class ServiceContainer {
    private static ServiceContainer instance;
    private Map<Object, BService> services;

    // <editor-fold defaultstate="collapsed" desc="Add service, get service, get instance">
    private final <T extends BService> T addService(T service) {
        services.put(service.getClass(), service);
        return service;
    }

    public <T extends BService> T getService(Class classService){
        T service = (T) services.get(classService);
        if (service == null) {
            try {
                service=addService((T)classService.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return service;
    }

    public static ServiceContainer getInstance() {
        ServiceContainer localConnection = instance;
        if (localConnection == null) {
            synchronized (ServiceContainer.class) {
                localConnection = instance;
                if (localConnection == null) {
                    instance = localConnection = new ServiceContainer();
                }
            }
        }
        return instance;
    }

    private ServiceContainer(){
        services=new HashMap<>();
    }
    // </editor-fold>
}
