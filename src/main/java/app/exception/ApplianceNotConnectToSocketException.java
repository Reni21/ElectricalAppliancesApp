package app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplianceNotConnectToSocketException extends BusinessException {

    public ApplianceNotConnectToSocketException(String message) {
        super(message);
    }
}
