package mx.gob.imss.ctirss.delta.service.interfaces;
import mx.gob.imss.ctirss.delta.framework.base.model.Captcha;

import java.io.IOException;

/**
 * @author Alex peña
 * @since 20/01/2020
 */
public interface ICaptchaService {

    public Captcha generateCaptchaImage(String sessionId) throws IOException;

    public boolean isValidCaptcha(String captchaId, String captchaValue);

}


