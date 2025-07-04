package com.log.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.log.controller.ILogController;
import com.log.model.LogEntry;
import com.log.service.ILogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController implements ILogController {

    private final ILogService logService;

    @GetMapping
    public List<LogEntry> getAllLogs() {
        return logService.getAllLogs();
    }
}