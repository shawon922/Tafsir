/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jolpai.tafsir.custom.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author User
 */
public class CustomException extends Exception {

    private ErrorCode code;
    private String message;
    private Exception parentException = null;

    private CustomException() {
    }

    public CustomException(ErrorCode code, String message) {
        super(message);
        this.code = code;
        this.message=message;

    }

    public CustomException(ErrorCode code, String message, Exception parentException) {
        super(message);
        this.code = code;
        this.message = message;
        this.parentException = parentException;
        this.getMessage();
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStackTraceInfo() {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        if (parentException == null) {
            this.printStackTrace(pw);
        } else {
            parentException.printStackTrace(pw);
        }
        return sw.getBuffer().toString();
    }

}
