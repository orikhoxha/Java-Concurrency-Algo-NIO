package oliverloeffler;

public interface ScheduledTask {
    void cancel();

    ScheduledTask reschedule();
}
