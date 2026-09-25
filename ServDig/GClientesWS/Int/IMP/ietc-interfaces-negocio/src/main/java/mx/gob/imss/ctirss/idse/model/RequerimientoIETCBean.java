package mx.gob.imss.ctirss.idse.model;

public class RequerimientoIETCBean implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 8511026938461171636L;

	private Pkcs7IETCBean pkcs7Bean;

	private RegistroPatronalIETCBean regPatronBean;

	public RequerimientoIETCBean() {
	}

	public RequerimientoIETCBean(final Pkcs7IETCBean pkcs7Bean, final RegistroPatronalIETCBean regPatronBean) {
		this.pkcs7Bean = pkcs7Bean;
		this.regPatronBean = regPatronBean;
	}

	/**
	 * Gets the pkcs7Bean value for this RequerimientoIETCBean.
	 * 
	 * @return pkcs7Bean
	 */
	public Pkcs7IETCBean getPkcs7Bean() {
		return pkcs7Bean;
	}

	/**
	 * Sets the pkcs7Bean value for this RequerimientoIETCBean.
	 * 
	 * @param pkcs7Bean
	 */
	public void setPkcs7Bean(final Pkcs7IETCBean pkcs7Bean) {
		this.pkcs7Bean = pkcs7Bean;
	}

	/**
	 * Gets the regPatronBean value for this RequerimientoIETCBean.
	 * 
	 * @return regPatronBean
	 */
	public RegistroPatronalIETCBean getRegPatronBean() {
		return regPatronBean;
	}

	/**
	 * Sets the regPatronBean value for this RequerimientoIETCBean.
	 * 
	 * @param regPatronBean
	 */
	public void setRegPatronBean(final RegistroPatronalIETCBean regPatronBean) {
		this.regPatronBean = regPatronBean;
	}

}
