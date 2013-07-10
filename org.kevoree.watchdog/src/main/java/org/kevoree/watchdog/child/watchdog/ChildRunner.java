package org.kevoree.watchdog.child.watchdog;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by duke on 17/05/13.
 */
public class ChildRunner {

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    private static WatchdogClient client = null;//new WatchdogClient();

    private static final String kevoreeMainClass = "org.kevoree.platform.standalone.App";

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("Kevoree Daemon");
        try {
            configureSystemProps();
            client = new WatchdogClient(internalPort);
            pool.scheduleAtFixedRate(client, 0, checkTime / 2, TimeUnit.MILLISECONDS);
            URL[] kevURLS = new URL[1];
            Object kevRuntime = System.getProperty("kevruntime");
            kevURLS[0] = new File(kevRuntime.toString()).toURI().toURL();
            URLClassLoader cl = new URLClassLoader(kevURLS);
            Class miniMainClass = cl.loadClass(kevoreeMainClass);
            Method mainM = miniMainClass.getMethod("main", String[].class);
            String[] argsFork = new String[0];
            mainM.invoke(null, (Object) argsFork);
        } catch (Exception e) {

            System.out.println(System.getProperty("kevruntime"));

            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static final String pingPortProperty = "ping.port";
    public static final String pingTimeoutProperty = "ping.timeout";
    public static Integer internalPort = 9999;
    public static Integer checkTime = 3000;

    public static void configureSystemProps() {
        //Configuration of property
        Object pingportValue = System.getProperty(pingPortProperty);
        if (pingportValue != null) {
            try {
                internalPort = Integer.parseInt(pingportValue.toString());
            } catch (Exception e) {
                System.err.println("Bad ping port specified : " + pingportValue + ", take default value : " + internalPort);
            }
        }
        Object pingTimeoutValue = System.getProperty(pingTimeoutProperty);
        if (pingTimeoutValue != null) {
            try {
                checkTime = Integer.parseInt(pingTimeoutValue.toString());
            } catch (Exception e) {
                System.err.println("Bad ping timeout specified : " + pingportValue + ", take default value : " + checkTime);
            }
        }
    }

}
