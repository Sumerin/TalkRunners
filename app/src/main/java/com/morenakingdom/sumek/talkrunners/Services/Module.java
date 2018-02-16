package com.morenakingdom.sumek.talkrunners.Services;

/**
 * Wrapper so no need to handle reference to object and his thread.
 * Created by sumek on 2/16/18.
 */
public abstract class Module implements Runnable {

    Thread currentModuleThread;

    /**
     * Assign thread to its object.
     * Should be call right after running module in another thread
     *
     * @param currentModuleThread thread which run current object
     */
    public void setThread(Thread currentModuleThread) {
        this.currentModuleThread = currentModuleThread;
    }

    /**
     * Interrupt and release the handle to thread.
     * Should be called whenever module is ready to be trashed. Used to avert reference loop
     */
    public void releaseThread() {
        if (currentModuleThread != null) {
            if (currentModuleThread.isAlive()) {
                currentModuleThread.interrupt();
                currentModuleThread = null;
            }
        }
    }
}
