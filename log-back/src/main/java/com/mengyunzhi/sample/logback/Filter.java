package com.mengyunzhi.sample.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.Arrays;
import java.util.List;

/**
 * @author panjie
 */
public class Filter extends AbstractMatcherFilter {
    @Override
    public FilterReply decide(Object event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }

        LoggingEvent loggingEvent = (LoggingEvent) event;

        // 当级别为warn或error，时触发日志。
        List<Level> eventsToKeep = Arrays.asList(Level.WARN, Level.ERROR);
        if (eventsToKeep.contains(loggingEvent.getLevel())) {
            return FilterReply.NEUTRAL;
        } else {
            return FilterReply.DENY;
        }

    }
}
