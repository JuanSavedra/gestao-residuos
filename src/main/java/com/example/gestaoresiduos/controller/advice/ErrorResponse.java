package com.example.gestaoresiduos.controller.advice;

public record ErrorResponse(String timestamp, int status, String error, String message, String path) {}
