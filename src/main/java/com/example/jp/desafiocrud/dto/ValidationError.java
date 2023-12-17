package com.example.jp.desafiocrud.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {

    private List<FieldMessage> fieldMessageList = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getFieldMessageList() {
        return fieldMessageList;
    }

    public void addError(String fieldName, String message) {
        fieldMessageList.add(new FieldMessage(fieldName, message));
    }
}
