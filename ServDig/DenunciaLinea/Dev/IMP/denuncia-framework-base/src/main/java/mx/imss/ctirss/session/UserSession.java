package mx.imss.ctirss.session;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class UserSession implements Serializable{

	private StringBuffer menu;
	/**
	 * CveidUsuario
	 */
	private Long cveIdUsuario;
	
	private String desCargo;
	/**
	 * PK Patron
	 */
	private Long cveIdPatron;
	
	
	private String procedencia;

	
	private Long cveIdPersona;
	/**
	 * Valor obenido de SegUsuarioFuncionario.cveIdUsuarioFuncionario
	 */
	private Long cveIdFuncionario;

	private String nomMaterno;

	private String nomNombre;

	private String nomPaterno;

	private String nomUsuarioSistema;
	/**
	 * Campo referente al ID de la tabla y asociado la secuencia de BD. <br>
	 * Utilizado para guardar la relaci—n a la delegaci—n a nivel BF (FK).
	 */
	private Long idDelegacion;
	/**
	 * Campo referente al ID de la tabla y asociado la secuencia de BD. <br>
	 * Utilizado para guardar la relaci—n a la subdelegaci—n a nivel BF (FK).
	 */
	private Long idSubDelegacion;
	/**
	 * Clave c—digo de la Delegaci—n (Llave de negocio).
	 */
	private String cveCodigoDelegacion;
	/**
	 * Clave c—digo de la Subdelegaci—n (Llave de negocio). <br>
	 * Campo utilizado para generar los <b> folios </b> de documentos de la
	 * subdelegaci—n.
	 * 
	 */
	private String cveCodigoSubDelegacion;
	/**
	 * Nombre de la delegaci—n.
	 */
	private String nombreDelegacion;
	/**
	 * Nombre de la subdelegaci—n.
	 */
	private String nombreSubDelegacion;
	/**
	 * Nœmero de serie del certificado del patr—n.
	 */
	private String numeroSerialCertificadoPatron;
	/**
	 * Indicador del tipo de certificado del patr—n.
	 */
	private TipoCertificado tipoCertificado;
	
	
	/**
	 * id de rol que tiene asignado el usuario 
	 */
	private long cveRol;
	
	/**
	 * Nombre o descripcion del Rol
	 */
	private String descripcionRol;
	
	/*Indica si es para tipo Funcionario*/
	private Integer idTipoUsuario;

	public UserSession() {

	}

	/**
	 * Regresa el menu del usuario.
	 * 
	 * @return
	 */
	public StringBuffer getMenu() {

		if (menu == null)
			return new StringBuffer();
		else
			return menu;

	}

	/**
	 * Asigna el menu
	 * 
	 * @param menu
	 * @param request
	 */
	public void setMenu(List<MenuVO> menu, HttpServletRequest request) {

		if (this.menu == null) {
			this.menu = new StringBuffer();
			Iterator<MenuVO> iter = menu.iterator();
			MenuVO to = null;
			while (iter.hasNext()) {
				to = iter.next();

				if (to.getCveFkMenuItem() == null
						|| to.getCveFkMenuItem().equals(""))
					this.menu.append("agregarMenuPadre('"
							+ to.getDesEtiqueta()
							+ "','"
							+ (to.getDesURL() != null
									&& !to.getDesURL().isEmpty() ? request
									.getContextPath() + to.getDesURL() : "")
							+ "'," + to.getCvePK() + ");");
				else
					this.menu.append("agregarMenuHijo('"
							+ to.getDesEtiqueta()
							+ "','"
							+ (to.getDesURL() != null
									&& !to.getDesURL().isEmpty() ? request
									.getContextPath() + to.getDesURL() : "")
							+ "'," + to.getCvePK() + ","
							+ to.getCveFkMenuItem() + ");");
			}
			// request.getSession().setAttribute(ConstantesSession.USR_SESSION,
			// this);
		}

	}

	/**
	 * Recupera el nombre completo.
	 * 
	 * @return
	 */
	public String getNombreCompleto() {
		final StringBuffer nom = new StringBuffer();

		if (this.nomNombre != null) {
			nom.append(this.nomNombre);
		}
		if (this.nomPaterno != null) {
			nom.append(" ");
			nom.append(this.nomPaterno);
		}
		if (this.nomMaterno != null) {
			nom.append(" ");
			nom.append(this.nomMaterno);
		}
		return nom.toString();
	}

	/**
	 * Valida que el certificado sea tipo SAT.
	 * 
	 * @return
	 */
	public boolean isCertificadoSAT() {
		return (this.tipoCertificado == null || (this.tipoCertificado != null && this.tipoCertificado
				.ordinal() == TipoCertificado.SAT.ordinal()));
	}

	/**
	 * @return the cveIdUsuario
	 */
	public Long getCveIdUsuario() {
		return cveIdUsuario;
	}

	/**
	 * @param cveIdUsuario
	 *            the cveIdUsuario to set
	 */
	public void setCveIdUsuario(Long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	/**
	 * @return the cveIdPersona
	 */
	public Long getCveIdPersona() {
		return cveIdPersona;
	}

	/**
	 * @param cveIdPersona
	 *            the cveIdPersona to set
	 */
	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	/**
	 * @return the nomMaterno
	 */
	public String getNomMaterno() {
		return nomMaterno;
	}

	/**
	 * @param nomMaterno
	 *            the nomMaterno to set
	 */
	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	/**
	 * @return the nomNombre
	 */
	public String getNomNombre() {
		return nomNombre;
	}

	/**
	 * @param nomNombre
	 *            the nomNombre to set
	 */
	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	/**
	 * @return the nomPaterno
	 */
	public String getNomPaterno() {
		return nomPaterno;
	}

	/**
	 * @param nomPaterno
	 *            the nomPaterno to set
	 */
	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	/**
	 * @return the nomUsuarioSistema
	 */
	public String getNomUsuarioSistema() {
		return nomUsuarioSistema;
	}

	/**
	 * @param nomUsuarioSistema
	 *            the nomUsuarioSistema to set
	 */
	public void setNomUsuarioSistema(String nomUsuarioSistema) {
		this.nomUsuarioSistema = nomUsuarioSistema;
	}

	/**
	 * @return the idDelegacion
	 */
	public Long getIdDelegacion() {
		return idDelegacion;
	}

	/**
	 * @param idDelegacion
	 *            the idDelegacion to set
	 */
	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	/**
	 * @return the idSubDelegacion
	 */
	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}

	/**
	 * @param idSubDelegacion
	 *            the idSubDelegacion to set
	 */
	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(StringBuffer menu) {
		this.menu = menu;
	}

	/**
	 * @return the nombreDelegacion
	 */
	public String getNombreDelegacion() {
		return nombreDelegacion;
	}

	/**
	 * @param nombreDelegacion
	 *            the nombreDelegacion to set
	 */
	public void setNombreDelegacion(String nombreDelegacion) {
		this.nombreDelegacion = nombreDelegacion;
	}

	/**
	 * @return the nombreSubDelegacion
	 */
	public String getNombreSubDelegacion() {
		return nombreSubDelegacion;
	}

	/**
	 * @param nombreSubDelegacion
	 *            the nombreSubDelegacion to set
	 */
	public void setNombreSubDelegacion(String nombreSubDelegacion) {
		this.nombreSubDelegacion = nombreSubDelegacion;
	}

	/**
	 * @return the cveIdFuncionario
	 */
	public Long getCveIdFuncionario() {
		return cveIdFuncionario;
	}

	/**
	 * @param cveIdFuncionario
	 *            the cveIdFuncionario to set
	 */
	public void setCveIdFuncionario(Long cveIdFuncionario) {
		this.cveIdFuncionario = cveIdFuncionario;
	}

	/**
	 * @return the patron
	 */
	public boolean isPatron() {
		return (this.cveIdPatron != null && this.cveIdPatron > 0);
	}


	/**
	 * @return the cveCodigoDelegacion
	 */
	public String getCveCodigoDelegacion() {
		return cveCodigoDelegacion;
	}

	/**
	 * @param cveCodigoDelegacion
	 *            the cveCodigoDelegacion to set
	 */
	public void setCveCodigoDelegacion(String cveCodigoDelegacion) {
		this.cveCodigoDelegacion = cveCodigoDelegacion;
	}

	/**
	 * Campo utilizado para generar los <b> folios </b> de documentos de la
	 * subdelegaci—n.
	 * 
	 * @return the cveCodigoSubDelegacion
	 */
	public String getCveCodigoSubDelegacion() {
		return cveCodigoSubDelegacion;
	}

	/**
	 * @param cveCodigoSubDelegacion
	 *            the cveCodigoSubDelegacion to set
	 */
	public void setCveCodigoSubDelegacion(String cveCodigoSubDelegacion) {
		this.cveCodigoSubDelegacion = cveCodigoSubDelegacion;
	}

	/**
	 * @return the numeroSerialCertificadoPatron
	 */
	public String getNumeroSerialCertificadoPatron() {
		return numeroSerialCertificadoPatron;
	}

	/**
	 * @param numeroSerialCertificadoPatron
	 *            the numeroSerialCertificadoPatron to set
	 */
	public void setNumeroSerialCertificadoPatron(
			String numeroSerialCertificadoPatron) {
		this.numeroSerialCertificadoPatron = numeroSerialCertificadoPatron;
	}

	/**
	 * @return the tipoCertificado
	 */
	public TipoCertificado getTipoCertificado() {
		return tipoCertificado;
	}

	/**
	 * @param tipoCertificado
	 *            the tipoCertificado to set
	 */
	public void setTipoCertificado(TipoCertificado tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

	/**
	 * @return the cveIdPatron
	 */
	public Long getCveIdPatron() {
		return cveIdPatron;
	}

	/**
	 * @param cveIdPatron the cveIdPatron to set
	 */
	public void setCveIdPatron(Long cveIdPatron) {
		this.cveIdPatron = cveIdPatron;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	/**
	 * Retorna el valor descripcionRol
	 * @return  descripcionRol
	 */
	public String getDescripcionRol() {
		return descripcionRol;
	}

	/**
	 * Asigna el valor del descripcionRol al atributo descripcionRol
	 * @param descripcionRol 
	 */
	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public String getDesCargo() {
		return desCargo;
	}

	public void setDesCargo(String desCargo) {
		this.desCargo = desCargo;
	}

	/**
	 * Retorna el valor cveRol
	 * @return  cveRol
	 */
	public long getCveRol() {
		return cveRol;
	}

	/**
	 * Asigna el valor del cveRol al atributo cveRol
	 * @param cveRol 
	 */
	public void setCveRol(long cveRol) {
		this.cveRol = cveRol;
	}

	public Integer getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(Integer idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	
	

}
