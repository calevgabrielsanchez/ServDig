package mx.gob.imss.ctirss.delta.framework.base.model;

import java.io.Serializable;

public class Captcha implements Serializable {

    private static final long serialVersionUID = 6279783209297326920L;

    private String captchaId;

    private String captchaValue;

    private byte[] captchaImage;

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }

    public byte[] getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(byte[] captchaImage) {
        this.captchaImage = captchaImage;
    }

}

