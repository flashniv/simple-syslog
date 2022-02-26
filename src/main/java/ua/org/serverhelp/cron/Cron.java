package ua.org.serverhelp.cron;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
public class Cron extends Thread{
    private final ArrayList<CronTask> cronTasks=new ArrayList<>();

    @Override
    public void run() {
        while(true){
            for (CronTask cronTask: cronTasks){
                if(cronTask.runTask()){
                    log.info("Cron run task "+cronTask.getClass().getCanonicalName());
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error start sleeping", e);
            }
        }
    }

    public void addCronTask(CronTask cronTask) {
        cronTasks.add(cronTask);
    }
}
