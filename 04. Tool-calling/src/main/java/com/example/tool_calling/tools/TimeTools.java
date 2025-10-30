package com.example.tool_calling.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class TimeTools {

    public static final Logger logger = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name = "getCurrentLocalTime", description = "Get the current local time in the user's timezone.")
    public String getCurrentLocalTime() {
        logger.info("Getting current local time in the user's timezone.");
        return LocalTime.now().toString();
    }

    @Tool(name = "getCurrentTimeWithSpecificTimeZone", description = "Get the current time in a specific timezone.")
    public String getCurrentTimeWithSpecificTimeZone(@ToolParam(description = "Value representing the timezone") String timeZone) {
        logger.info("Getting current time in the specified timezone: {}", timeZone);
        return LocalTime.now(ZoneId.of(timeZone)).toString();

    }
}
