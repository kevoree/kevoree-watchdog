package org.kevoree.watchdog;

/**
 * Created by duke on 17/05/13.
 */
public class ChildManager implements Runnable {

    public void setSubProcess(WatchDogCheck subProcess) {
        this.subProcess = subProcess;
    }

    private WatchDogCheck subProcess = null;

    @Override
    public void run() {
        try {
            if(subProcess!=null){
                subProcess.destroyChild();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
