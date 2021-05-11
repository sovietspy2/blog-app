package com.example.blogapp.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public final class LoggingTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable task) {
        Map<String, String> webThreadContext = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(webThreadContext);
                task.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
