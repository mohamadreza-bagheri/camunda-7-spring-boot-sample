package com.example.camundaservice.model;

import java.time.Instant;
import java.util.Map;

public record TaskEvent(
    String taskId,
    String taskType,
    String assignee,
    Map<String, Object> variables,
    Instant timestamp
) {}