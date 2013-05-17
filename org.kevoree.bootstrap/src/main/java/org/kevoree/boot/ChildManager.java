package org.kevoree.boot;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by duke on 17/05/13.
 */
public class ChildManager implements Runnable {

    private AtomicInteger pid = new AtomicInteger();

    public void setPID(Integer it){
        pid.set(it);
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("kill " + pid).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
