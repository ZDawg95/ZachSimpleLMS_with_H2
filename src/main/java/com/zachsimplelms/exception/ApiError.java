package com.zachsimplelms.exception;

public record ApiError(int status, String error) {
}
