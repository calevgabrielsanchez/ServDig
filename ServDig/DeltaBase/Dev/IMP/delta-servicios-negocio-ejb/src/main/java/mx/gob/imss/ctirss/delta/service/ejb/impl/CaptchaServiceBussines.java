package mx.gob.imss.ctirss.delta.service.ejb.impl;

import java.io.IOException;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.model.Captcha;
import mx.gob.imss.ctirss.delta.service.ejb.CaptchaServiceRemote;
import mx.gob.imss.ctirss.delta.framework.util.CaptchaUtil;

/**
 * @author Alex peña
 * @since 20/01/2020
 *
 */
@Stateless(name = "componentCaptchaService", mappedName = "componentCaptchaService")
public class CaptchaServiceBussines implements CaptchaServiceRemote {

    @Override
    public boolean isValidCaptcha(String captchaId, String captchaValue) {

        return CaptchaUtil.isValidCaptcha(captchaId, captchaValue);
    }

    @Override
    public Captcha generateCaptchaImage(String sessionId) throws IOException {

        return CaptchaUtil.generateCaptchaImage(sessionId);
    }

}

