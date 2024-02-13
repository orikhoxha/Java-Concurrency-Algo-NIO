package oliverloeffler;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DebugLogger implements Logger {

    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public void log(LogEntry... logEntries) {
        long upTime = runtimeMXBean.getUptime();
        String text = Stream.of(logEntries)
                .map(e -> e.key() + "=" + e.value())
                .collect(Collectors.joining(",", "[" + upTime + "] ", ""));
        System.out.println(text);
    }

    @Override
    public void log(Exception e, LogEntry... logEntries) {
        e.printStackTrace();
        log(logEntries);
    }
}
