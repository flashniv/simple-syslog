package ua.org.serverhelp.cron;

import lombok.extern.log4j.Log4j2;
import ua.org.serverhelp.Metrics;

@Log4j2
public class MetricsCommitCronTask extends CronTask {
    private final Metrics metrics;

    public MetricsCommitCronTask(Metrics metrics) {
        super(60);
        this.metrics=metrics;
    }

    @Override
    protected void task() {
        metrics.commitMetrics();
    }
}
