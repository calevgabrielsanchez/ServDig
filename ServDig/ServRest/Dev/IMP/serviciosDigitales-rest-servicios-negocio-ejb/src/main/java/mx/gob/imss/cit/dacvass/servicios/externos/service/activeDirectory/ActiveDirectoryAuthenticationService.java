package mx.gob.imss.cit.dacvass.servicios.externos.service.activeDirectory;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.ConsultaUsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.RespuestaDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserUsuarioDirectorioActivoToRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ResourceBundleConfiguration;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;

@Stateless(name = "directorioActioService", mappedName = "directorioActioService")
public class ActiveDirectoryAuthenticationService extends AbstractServiceBusiness  implements IActiveDirectoryAuthenticationServiceLocal{

	private static final Logger log = LoggerFactory
			.getLogger(ActiveDirectoryAuthenticationService.class);

	private static final String CONTEXT_FACTORY_CLASS = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String SECURITY_AUTHENTICATION = "simple";
	private static ResourceBundle propertiesSD = ResourceBundleConfiguration.getResourceServiciosDigitales();

	private static final String BASECN_METRO = propertiesSD.getString("basegc.metro.directorio.activo.imss");
	private static final String BASECN_METRO1 = propertiesSD.getString("basegc.metro1.directorio.activo.imss");
	private static final String BASECN_METRO2 = propertiesSD.getString("basegc.metro2.directorio.activo.imss");
	private static final String BASECN_NORTE = propertiesSD.getString("basegc.norte.directorio.activo.imss");
	private static final String BASECN_NORTE1 = propertiesSD.getString("basegc.norte1.directorio.activo.imss");
	private static final String BASECN_SUR = propertiesSD.getString("basegc.sur.directorio.activo.imss");
	private static final String BASECN_SUR1 = propertiesSD.getString("basegc.sur1.directorio.activo.imss");
	private static final String BASECN_CENTRO = propertiesSD.getString("basegc.centro.directorio.activo.imss");
	private static final String BASECN_CENTRO1 = propertiesSD.getString("basegc.centro1.directorio.activo.imss");
	private static final String BASECN_OCCIDENTE = propertiesSD.getString("basegc.occidente.directorio.activo.imss");
	private static final String BASECN_OCCIDENTE1 = propertiesSD.getString("basegc.occidente1.directorio.activo.imss");
	private static final String PRINCIPALS = propertiesSD.getString("user.directorio.activo.imss");
	private static final String CREDENTIALS = propertiesSD.getString("credentials.user.directorio.activo.imss");
	private static final String URL_DIRECTORIO_ACTIVIO = propertiesSD.getString("ulr.directorio.activo.imss");
	private static final String STR_REGION_METRO  = "METRO";
	private static final String STR_REGION_NORTE  = "NTE";
	private static final String STR_REGION_SUR  = "SUR";
	private static final String STR_REGION_CENTRO  = "CTO";
	private static final String STR_REGION_OCCIDENTE  = "OCC";
	private static final String ERROR_AUTENTICACION_CODE_49 = "LDAP: error code 49";


	public boolean ldapAuthentication(String userName, String password) throws AuthenticationException,
	NamingException, Exception{
		log.debug("llegue al metod de autenticación");
		String ldapURL = propertiesSD.getString("ulr.directorio.activo.imss");
		String baseDc = propertiesSD.getString("base.dc.directorio.activo.imss");
		String baseUsuario = propertiesSD.getString("userSearchFilter.directorio.activo.imss");
		ldapURL=ldapURL+"/"+baseDc;
		String principals =userName;
		log.debug("la base de consulta es " + ldapURL );

		Hashtable<String, String> environment = new  Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_CLASS);
		environment.put(Context.PROVIDER_URL, ldapURL);
		environment.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		environment.put(Context.SECURITY_PRINCIPAL, principals);
		environment.put(Context.SECURITY_CREDENTIALS, password);

		try {
			DirContext authContext = new InitialDirContext(environment);
			log.debug("pase  la autenticación");
			authContext.close();
			return true;
		} catch (AuthenticationException ex) {
			log.error("ocurrio un error en la autenticaicón" ,ex);
			ex.printStackTrace();
			throw ex;
		} catch (NamingException ex) {
			log.error("ocurrio un error en el seteo de las propiedades  para conexion", ex);
			ex.printStackTrace();
			throw ex;
		} catch (Exception ex) {
			log.error("ocurrio un error no identifacado en la consulta al ldap", ex);
			ex.printStackTrace();
			throw ex;
		}

	}

	

	@Override
	public UsuarioDirectorioActivo geDatostUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta)
			throws ServiciosRestException {

		log.info("Buscando a : " + consulta.getClaveUsusaio() + " en el dominio " + consulta.getDominio());
		String userName = consulta.getClaveUsusaio();
		if(userName.contains("@")) {
			String[] cuentaSinDominio =consulta.getClaveUsusaio().split("@");
			userName = cuentaSinDominio[0];
		;
		}
		
		Date tIni = new Date();
		try {
			LdapContext ctx = getLdapContext(null, null,null);
			int pageSize = 100;
			byte[] cookie = null;
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });
			int total;
			do {
				/* perform the search */
				SearchControls sc = new SearchControls();
				sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

				SearchControls constraints = new SearchControls();
				constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);


				String filtro = "(cn=" +userName+")";
				String domainName = getBaseDominioDirectorioActivo(consulta.getDominio(), false,false);
				NamingEnumeration results = null;
				results  = ctx.search(domainName, filtro, constraints);
				/**Se evalua si tiene o no resultados la busqueda para el segundo dominio metro**/
				if(!results.hasMoreElements()){
					domainName = getBaseDominioDirectorioActivo(consulta.getDominio(), false, true);
					results  = ctx.search(domainName, filtro, constraints);
					if(!results.hasMoreElements() && consulta.getDominio().equalsIgnoreCase(STR_REGION_METRO)){
						domainName = getBaseDominioDirectorioActivo(consulta.getDominio(), true, false);
						results  = ctx.search(domainName, filtro, constraints);
					}
					
				}
				while (results.hasMoreElements()) {
					SearchResult result = (SearchResult) results.nextElement();
					Attributes attributes = result.getAttributes();
					//convert to MyUser class
					RespuestaDirectorioActivo userResp = ParserUsuarioDirectorioActivoToRest.parserLdapResponseToModel(attributes);

					//Solo mete al arbol el usuario encontrado (Para optimizar el tiempo de respuesta)
					if (userName.equals(userResp.getName()))
					{
						ctx.close();
						Date tFin = new Date();
						System.out.println("Encontrado en : " + (tFin.getTime()-tIni.getTime())/1000 + "sec");
						UsuarioDirectorioActivo user = ParserUsuarioDirectorioActivoToRest.parserRespuestaDirectorioAtivoToRest(userResp);
						return user;
					}
				}


				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();

				if (controls != null) {
					for (int i = 0; i < controls.length; i++) {
						if (controls[i] instanceof PagedResultsResponseControl) {
							PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
							total = prrc.getResultSize();
							cookie = prrc.getCookie();
						}
					}
				} else {
					System.out.println("No controls were sent from the server");
				}
				// Re-activate paged results
				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
			} while (cookie != null);

			ctx.close();
		}catch(ServiciosRestException e) {
			log.error("excepcion ya manejada ", e);
			throw e;
		}catch (AuthenticationException e) {
			log.error("ocurrio un error en la autenticaicon" ,e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage(),
					"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage()));
		} catch (Exception e) {
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error desconocido en la autenticaicon con el directorio activo"+ e.getMessage(),
					"ocurrio un error desconocido en la autenticaicon con el directorio activo"+ e.getMessage()));
		
		}
			log.debug("no se encontro información del usuario" + consulta);
			return null;
	}




	@Override
	public boolean autenticaUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta)
			throws ServiciosRestException {
		log.info("llegue la metodo de consulta de usuario autenticado" +  consulta);
		LdapContext ctx = null;
		try {
			ctx =this.getLdapContext(getBaseDominioDirectorioActivo(consulta.getDominio(), false, false), 
					consulta.getClaveUsusaio(), consulta.getPassword());
			try {
				ctx.close();
			}catch (Exception e) {
				log.error("error despues de auntenticar al cerrar la conexión");
			}
			return true;
		}catch (AuthenticationException e) {
			//se hace el manejo para  evaluar la segundo arbol de metro con base al error 49
			if(e.getMessage().contains(ERROR_AUTENTICACION_CODE_49)  && consulta.getDominio().equalsIgnoreCase(STR_REGION_METRO)) {
				try {
					ctx =this.getLdapContext(getBaseDominioDirectorioActivo(consulta.getDominio(), true, false), 
							consulta.getClaveUsusaio(), consulta.getPassword());
					try {
						ctx.close();
					}catch (Exception e1) {
						log.error("error despues de auntenticar al cerrar la conexión");
					}
					return true;
				}catch (AuthenticationException ex) {
					log.error("ocurrio un error en la autenticaicon segundo intento metro" ,e);
					if(ex.getMessage().contains(ERROR_AUTENTICACION_CODE_49))
						throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
								"El usuario "+ consulta.getClaveUsusaio() + " el password es incorrecto", 
								"El usuario " + consulta.getClaveUsusaio() + " el password es incorrecto"));
					else
					throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
							"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage(),
							"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage()));

				}
			}else if(e.getMessage().contains(ERROR_AUTENTICACION_CODE_49)) {
				log.error("ocurrio un error identificado de user o contraseña" ,e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"El usuario "+ consulta.getClaveUsusaio() + "  el password es incorrecto", 
						"El usuario " + consulta.getClaveUsusaio() + " el password es incorrecto"));
			}else {
				log.error("ocurrio un error en la autenticaicon" ,e);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage(),
						"ocurrio un error en la autenticaicon con el directorio activo"+ e.getMessage()));
			}
		}
	}

	/**
	 * Metodo auxiliar para recuperar la conección al LDAP para ser usada en los servivios
	 * @return
	 * @throws AuthenticationException
	 * @throws NamingException
	 * @throws Exception
	 */
	private  LdapContext getLdapContext(String dominio, String principal, String credentials) throws AuthenticationException,ServiciosRestException{
		// Setup environment for authenticating
		Hashtable<String, String> environment = new Hashtable<String, String>();
		String ldapURLGC = URL_DIRECTORIO_ACTIVIO;
		if(dominio != null)
			ldapURLGC +="/"+dominio;
		environment.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY_CLASS);
		environment.put(Context.PROVIDER_URL, ldapURLGC);
		environment.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		if(principal != null)
			environment.put(Context.SECURITY_PRINCIPAL, principal);
		else
			environment.put(Context.SECURITY_PRINCIPAL, PRINCIPALS);	
		if(credentials != null)
			environment.put(Context.SECURITY_CREDENTIALS, credentials);
		else	
			environment.put(Context.SECURITY_CREDENTIALS, CREDENTIALS);


		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(environment, null);
			System.out.println("Connection Successful.");
		} catch (AuthenticationException e) {
			log.error("ocurrio un error en la autenticaicon" ,e);			
			throw e;
		} catch (NamingException e) {
			log.error("ocurrio un error en el seteo de las propiedades  para conexion", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el seteo de las propiedades  para conexion "+ e.getMessage(),
					"ocurrio un error en el seteo de las propiedades  para conexion "+ e.getMessage()));
		} catch (Exception e) {
			log.error("ocurrio un error no identifacado en la conexion al ldap ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error no identifacado en la conexion al ldap "+ e.getMessage(),
					"ocurrio un error no identifacado en la conexion al ldap "+ e.getMessage()));
		}
		return ctx;
	}


	private static String getBaseDominioDirectorioActivo(String dominio, boolean isSegundaBusqueda, boolean sinAsingacion) throws ServiciosRestException {
		dominio = dominio.toUpperCase();		
		if(dominio.equals(STR_REGION_METRO)) {
			if(sinAsingacion)
				return BASECN_METRO2;
			if (isSegundaBusqueda) { 
				return BASECN_METRO1;
			} else { 
				return BASECN_METRO;
			}
			
		}else if(dominio.equals(STR_REGION_NORTE)) {
			if(sinAsingacion)
				return BASECN_NORTE1;
			else
				return BASECN_NORTE;
		}else if(dominio.equals(STR_REGION_SUR)) {
			if(sinAsingacion)
				return BASECN_SUR1;
			else
				return BASECN_SUR;
		}else if(dominio.equals(STR_REGION_OCCIDENTE)) { 	
			if(sinAsingacion)
				return BASECN_OCCIDENTE1;
			else
				return BASECN_OCCIDENTE;
		}else if(dominio.equals(STR_REGION_CENTRO)) {
			if(sinAsingacion)
				return BASECN_CENTRO1;
			else
				return BASECN_CENTRO;
		}
			
		else
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"no se identifico el dominio de la cuenta de dominio: " + dominio,
					"no se identifico el dominio de la cuenta de dominio: " + dominio));
	}


}


