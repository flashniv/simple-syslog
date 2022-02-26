package ua.org.serverhelp.cron;

import java.time.Instant;

public abstract class CronTask {
    private final int quantificator;

    public CronTask(int quantificator) {
        this.quantificator = quantificator;
    }
    public boolean runTask(){
        if(Instant.now().getEpochSecond()%quantificator==0){
            task();
            return true;
        }
        return false;
    }
    protected abstract void task();
}
