package app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OverLoadElectricityException extends BusinessException {

    public OverLoadElectricityException(String message) {
        super(message);
    }
}
