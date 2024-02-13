package oliverloeffler;

public interface Logger {

    boolean enabled();

    void log(LogEntry... logEntries);

    void log(Exception e, LogEntry... logEntries);
}
