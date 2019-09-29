package exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FireSafetyException extends BusinessException {
    public FireSafetyException(String message) {
        super(message);
    }
}
