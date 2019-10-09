package app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends Exception{

    public BusinessException(String message) {
        super(message);
    }
}
