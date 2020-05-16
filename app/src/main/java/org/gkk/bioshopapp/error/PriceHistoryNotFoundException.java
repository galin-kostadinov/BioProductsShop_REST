package org.gkk.bioshopapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.gkk.bioshopapp.constant.ErrorMessageConstant.PRICE_HISTORY_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = PRICE_HISTORY_NOT_FOUND)
public class PriceHistoryNotFoundException extends RuntimeException {

    private int status;

    public PriceHistoryNotFoundException() {
        this.status = 404;
    }

    public PriceHistoryNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
