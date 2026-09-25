package mx.gob.imss.cit.dacvass.utils.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaSD implements Serializable {

    private static final long serialVersionUID = 6279783209297326920L;

    private String captchaId;

    private String captchaValue;

    private byte[] captchaImage;
    
    private HttpServletResponse response;
    
    private HttpServletRequest request;

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

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public CaptchaSD(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public CaptchaSD() {
		
	}

}

