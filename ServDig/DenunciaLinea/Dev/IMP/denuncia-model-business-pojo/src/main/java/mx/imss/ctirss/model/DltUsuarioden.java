package mx.imss.ctirss.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltUsuarioden;

import java.util.Date;


/**
 * The persistent class for the DLT_USUARIODEN database table.
 * 
 */
@Entity
@Table(name="DLT_USUARIODEN")
public class DltUsuarioden extends AbstractDltUsuarioden implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String captcha;
	
	

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	
}