package com.effizent.esplapp.RetroApiResponses;

public class CreatePostResult {
   private String Code,Status;

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }

    public CreatePostResult(String code, String status) {
        Code = code;
        Status = status;
    }
}
