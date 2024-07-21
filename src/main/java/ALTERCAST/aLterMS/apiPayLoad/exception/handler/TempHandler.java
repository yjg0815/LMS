package ALTERCAST.aLterMS.apiPayLoad.exception.handler;

import ALTERCAST.aLterMS.apiPayLoad.code.BaseErrorCode;
import ALTERCAST.aLterMS.apiPayLoad.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
