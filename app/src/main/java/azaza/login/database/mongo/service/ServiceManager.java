package azaza.login.database.mongo.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import azaza.login.database.mongo.service.core.AServiceMethod;
import azaza.login.database.mongo.service.essence.EsAccount;
import azaza.login.database.mongo.service.essence.core.BMongoEssence;
import azaza.login.database.mongo.service.implement.AccountService;
import azaza.login.database.mongo.service.implement.ResultService;

/**
 * Created by Alex on 23.03.2015.
 */
public class ServiceManager extends Service {

    private Map<Object, Class> clazzs = new HashMap<>();
    final String LOG_TAG = "myLogs";

    public ServiceManager() {
        clazzs.put("AccountService", AccountService.class);
        clazzs.put("ResultService", ResultService.class);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");

        String nameC = "AccountService";
        String nameM = "verifyAccount";
        EsAccount esAccount = new EsAccount();

        Class<?> clazz = clazzs.get(nameC);
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(AServiceMethod.class)) {
                AServiceMethod test = method.getAnnotation(AServiceMethod.class);
                if (nameM == test.name()) {
                    try {
                        Object _class = clazz.newInstance();
                        method.invoke(_class, (BMongoEssence) esAccount);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
