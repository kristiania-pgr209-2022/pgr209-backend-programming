package no.kristiania.infrastructure;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;
import org.slf4j.spi.LocationAwareLogger;

public class EclipseLinkSlf4jLogger extends AbstractSessionLog {
    private static final LocationAwareLogger logger = (LocationAwareLogger) LoggerFactory.getLogger("org.eclipse.persistence");


    @Override
    public void log(SessionLogEntry entry) {
        logger.log(
                MarkerFactory.getMarker(entry.getMessage()),
                entry.getSourceClassName(),
                getLevelInt(entry),
                formatMessage(entry),
                null,
                entry.getException()
        );
    }

    @Override
    public boolean shouldLog(int level) {
        return logger.isEnabledForLevel(getLevel(level));
    }

    @Override
    public boolean shouldLog(int level, String category) {
        return logger.isEnabledForLevel(getLevel(level));
    }

    private int getLevelInt(SessionLogEntry sessionLogEntry) {
        return getLevelInt(getLevel(sessionLogEntry.getLevel()));
    }

    private int getLevelInt(Level level) {
        return switch (level) {
            case TRACE -> 0;
            case DEBUG -> 10;
            case INFO -> 20;
            case WARN -> 30;
            case ERROR -> 40;
        };
    }

    private Level getLevel(int level) {
        return switch (level) {
            case 7 -> Level.ERROR;
            case 6 -> Level.WARN;
            case 5 -> Level.INFO;
            case 4 -> Level.DEBUG;
            case 3 -> Level.DEBUG;
            case 2 -> Level.TRACE;
            case 1 -> Level.TRACE;
            default -> Level.WARN;
        };
    }
}
