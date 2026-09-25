package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.MensajeriaSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.forgerock.opendj.ldap.Entries;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.LdapException;
import org.forgerock.opendj.ldap.LinkedHashMapEntry;
import org.forgerock.opendj.ldap.TreeMapEntry;
import org.forgerock.opendj.ldap.requests.ModifyRequest;
import org.forgerock.opendj.ldap.responses.SearchResultEntry;

/**
 * Session Bean implementation class AdmonUsuariosSession
 */
@WebService
@Stateless(name = "admonUsuarios", mappedName = "admonUsuarios")

// namg
public class AdmonUsuarios extends ServiceLdap implements AdmonUsuariosSessionLocal {
	/** Logger para el servicio de administracion de usuarios */
	private static final Log logger = LogFactory.getLog(AdmonUsuarios.class);
	/** Contexto de LDAP para manipulacion de Usuarios */
	private DirContext ctx = null;
	/** Propiedades de consulta */
	private Properties props = new Properties();
	/** Estado activo */
	private static final String ACTIVO = "Active";
	/** Estado inactivo */
	private static final String INACTIVO = "Inactive";
	/** Cadena de propiedad de dn base de usuarios */
	private static final String USUARIOS_DN_BASE = "base.dn.usuarios";
	/** Servicio de administracion de perfiles */
	@EJB
	private AdmonPerfilesSessionLocal admonPerfiles;
	@EJB
	private AdmonRolesSessionLocal admonRoles;
	@EJB
	private MensajeriaSessionLocal mensajeriaService;

	
	private boolean finalizar = true;

	/**
	 * Constructor
	 */
	public AdmonUsuarios() {
		super();
	}

	/**
	 * Metodo para inicializar el contexto de LDAP
	 */
	public void init() {
		try {
			props.load(AdmonUsuarios.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
//			props.load(AdmonUsuarios.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap2.properties"));
			ctx = new InitialDirContext(props);
		} catch (Exception ex) {
			logger.error("Error al crear contexto inicial." + ex);
		}
	}

	public void conecta() throws AdmonUsuariosException {
		try {
			props.load(AdmonUsuarios.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
			ctx = new InitialDirContext(props);
			
//			props.load(AdmonUsuarios.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
//			Context context = new InitialContext();
//			ctx =(DirContext) context.lookup("ldap/SAU");
		
		} catch (Exception ex) {
			logger.error("Error al crear contexto inicial." + ex);
			throw new AdmonUsuariosException("Error al crear contexto inicial");
		}
	}

	public void desconecta() {
		try {
			ctx = null;
		} catch (Exception ex) {
			logger.error("Error al crear contexto inicial.", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal
	 * #existeUsuario(java.lang.String)
	 */
	@Override
	public boolean existeUsuario(final String uid)throws AdmonUsuariosException {
		conecta();
		try {
			boolean found = false;
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("uid", uid));
			final NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			found = answer.hasMoreElements();
			return found;
		} catch (Exception ex) {
			logger.error("Error al validar existencia de usuario consulta de usuario.", ex);
			throw new AdmonUsuariosException(
					"Error al validar existencia de usuario consulta de usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal
	 * #obtenUsuariosPorPerfil(java.lang.String)
	 */
	@Override
	public List<UsuarioDTO> obtenUsuariosPorPerfil(PerfilDTO perfil)throws AdmonUsuariosException {
		conecta();
		try {
			final List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("objectClass", "person"));
			matchAttrs.put(new BasicAttribute("employeeType", perfil.getPuestoDTO().getNombrePuesto()));
			final NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			SearchResult person;
			Attributes attrs;
			while (answer.hasMoreElements()) {
				person = answer.nextElement();
				attrs = person.getAttributes();
				usuarios.add(new UsuarioDTO(
						perfil,
						attrs.get("cn") != null ? attrs.get("cn").get()
								.toString() : "",
						attrs.get("sn") != null ? attrs.get("sn").get()
								.toString() : "",
						attrs.get("givenName") != null ? attrs.get("givenName")
								.get().toString() : "",
						attrs.get("uid") != null ? attrs.get("uid").get()
								.toString() : "",
						"",
						"",
						ACTIVO.equals(attrs.get("inetUserStatus") != null ? attrs
								.get("inteUserStatus").get().toString()
								: null), attrs.get("mail") != null ? attrs
								.get("mail").get(0).toString() : "", attrs
								.get("businessCategory") != null ? Integer
								.valueOf(attrs.get("businessCategory").get(0)
										.toString()) : null, attrs
								.get("departmentNumber") != null ? Integer
								.valueOf(attrs.get("departmentNumber").get(0)
										.toString()) : null, attrs
								.get("destinationIndicator") != null ? Integer
								.valueOf(attrs.get("destinationIndicator")
										.get(0).toString()) : null, attrs
								.get("employeeNumber") != null ? attrs
								.get("employeeNumber").get(0).toString() : "",
						attrs.get("initials") != null ? attrs.get("initials")
								.get(0).toString() : "", attrs
								.get("carLicense") != null ? attrs
								.get("carLicense").get(0).toString() : ""));
			}
			return usuarios;
		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuarios.", ex);
			throw new AdmonUsuariosException(
					"Error al realizar consulta de usuarios", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal
	 * #obtenUsuario(java.lang.String)
	 */
//	@Override
//	public UsuarioDTO obtenUsuario(final String uid)throws AdmonUsuariosException {
//		System.out.println("Inicia metodo de busqueda de usuario en ldap ----------------------->");
//		if(ctx==null)
//		{
//			conecta();
//			finalizar = true;
//		}
//		else
//			finalizar = false;
//		try {
//			if (uid != null) {
//				UsuarioDTO usuario = null;
//				final Attributes matchAttrs = new BasicAttributes(true);
//				matchAttrs.put(new BasicAttribute("uid", uid));
//				final NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrs);
//				if (answer.hasMoreElements()) {
//					SearchResult result = answer.nextElement();
//					Attributes attrs = result.getAttributes();
//					PerfilDTO perfil = new PerfilDTO(); // attrs.get("employeeType")
//														// != null ?
//														// admonPerfiles.obtenPerfil(attrs.get("employeeType").get().toString())
//														// :
//					usuario = new UsuarioDTO(
//							perfil,
//							attrs.get("cn") != null ? attrs.get("cn").get()
//									.toString() : "",
//							attrs.get("sn") != null ? attrs.get("sn").get()
//									.toString() : "",
//							attrs.get("givenName") != null ? attrs
//									.get("givenName").get().toString() : "",
//							attrs.get("uid") != null ? attrs.get("uid").get()
//									.toString() : "",
//							"",
//							"",
//							ACTIVO.equals(attrs.get("inetUserStatus") != null ? attrs
//									.get("inetUserStatus").get().toString()
//									: ""), attrs.get("mail") != null ? attrs
//									.get("mail").get(0).toString() : "",
//							attrs.get("businessCategory") != null ? Integer
//									.valueOf(attrs.get("businessCategory")
//											.get(0).toString()) : null,
//							attrs.get("departmentNumber") != null ? Integer
//									.valueOf(attrs.get("departmentNumber")
//											.get(0).toString()) : null,
//							attrs.get("destinationIndicator") != null ? Integer
//									.valueOf(attrs.get("destinationIndicator")
//											.get(0).toString()) : null,
//							attrs.get("employeeNumber") != null ? attrs
//									.get("employeeNumber").get(0).toString()
//									: "", attrs.get("initials") != null ? attrs
//									.get("initials").get(0).toString() : "",
//							attrs.get("carLicense") != null ? attrs
//									.get("carLicense").get(0).toString() : "");
//					usuario.setModulos(attrs.get("imsssistemas") != null ? attrs
//							.get("imsssistemas").get(0).toString()
//							: "");
//					usuario.setPerfiles(attrs.get("imssperfiles") != null ? attrs
//							.get("imssperfiles").get(0).toString()
//							: "");
//					usuario.setPassword(attrs.get("carLicense") != null ? attrs
//							.get("carLicense").get(0).toString() : "");
//					usuario.setDescripcionCargo(attrs.get("title") != null ? attrs
//							.get("title").get(0).toString()
//							: "");
//					String estatus = attrs.get("inetUserStatus").get(0)
//							.toString()
//							+ "";
//					usuario.setActivo(estatus.trim().equals(ACTIVO) ? true
//							: false);
//				}
//				return usuario;
//			}
//			return null;
//		} catch (Exception ex) {
//			System.out.println("Error al realizar consulta de usuario "+ ex);
//			throw new AdmonUsuariosException(
//					"Error al realizar consulta de usuario", ex);
//		} finally {
//			if(finalizar)
//				desconecta();
//			System.out.println("Termina metodo de busqueda de usuario en ldap <-----------------------");
//		}
//	}

	
	@Override
	public UsuarioDTO obtenUsuario(final String uid)throws AdmonUsuariosException {
		try {
			if (uid != null) {
				UsuarioDTO usuario = null;

				conectaLDAP();
				
				SearchResultEntry entry = connection.readEntry("uid="+uid+","+baseDN);

				PerfilDTO perfil = new PerfilDTO();
				usuario = new UsuarioDTO(perfil,
						entry.parseAttribute("cn").asString(),
						entry.parseAttribute("sn").asString(),
						entry.parseAttribute("givenName").asString(),
						entry.parseAttribute("uid").asString(),
						"",
						"",
						ACTIVO.equals(entry.parseAttribute("inetUserStatus").asString()),
						entry.parseAttribute("mail").asString(),
						entry.parseAttribute("businessCategory").asInteger(),
						entry.parseAttribute("departmentNumber").asInteger(),
						entry.parseAttribute("destinationIndicator").asInteger(),
						entry.parseAttribute("employeeNumber").asString(),
						entry.parseAttribute("initials").asString(),
						entry.parseAttribute("carLicense").asString());

				usuario.setModulos(entry.parseAttribute("imsssistemas").asString());
				usuario.setPerfiles(entry.parseAttribute("imssperfiles").asString());
//				usuario.setPassword(entry.parseAttribute("carLicense").asString());
				usuario.setDescripcionCargo(entry.parseAttribute("title").asString());
				usuario.setMatricula(entry.parseAttribute("imssmatricula").asString());
				String estatus = entry.parseAttribute("inetUserStatus").asString();
				usuario.setActivo(estatus.trim().equals(ACTIVO) ? true : false);
				return usuario;
			}
		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuario." + ex.getMessage());
			logger.error("  ", ex);
			return null;
		} finally {
			desconectaLDAP();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal
	 * #obtenUsuario(java.lang.String)
	 */
	@Override
	public UsuarioDTO obtenContrasena(final String uid, String correoEletronico)throws AdmonUsuariosException {
		conecta();
		try {
			UsuarioDTO usuario = null;
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("uid", uid));
			final NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			if (answer.hasMoreElements()) {
				SearchResult result = answer.nextElement();
				Attributes attrs = result.getAttributes();
				PerfilDTO perfil = new PerfilDTO(); // attrs.get("employeeType")
													// != null ?
													// admonPerfiles.obtenPerfil(attrs.get("employeeType").get().toString())
													// :
				usuario = new UsuarioDTO(perfil,
						attrs.get("cn") != null ? attrs.get("cn").get()
								.toString() : "",
						attrs.get("sn") != null ? attrs.get("sn").get()
								.toString() : "",
						attrs.get("givenName") != null ? attrs.get("givenName")
								.get().toString() : "",
						attrs.get("uid") != null ? attrs.get("uid").get()
								.toString() : "", "", "", ACTIVO.equals(attrs
								.get("inetUserStatus") != null ? attrs
								.get("inetUserStatus").get().toString() : ""),
						attrs.get("mail") != null ? attrs.get("mail").get(0)
								.toString() : "",
						attrs.get("businessCategory") != null ? Integer
								.valueOf(attrs.get("businessCategory").get(0)
										.toString()) : null,
						attrs.get("departmentNumber") != null ? Integer
								.valueOf(attrs.get("departmentNumber").get(0)
										.toString()) : null,
						attrs.get("destinationIndicator") != null ? Integer
								.valueOf(attrs.get("destinationIndicator")
										.get(0).toString()) : null,
						attrs.get("employeeNumber") != null ? attrs
								.get("employeeNumber").get(0).toString() : "",
						attrs.get("initials") != null ? attrs.get("initials")
								.get(0).toString() : "",
						attrs.get("carLicense") != null ? attrs
								.get("carLicense").get(0).toString() : "");
				usuario.setModulos(attrs.get("imsssistemas") != null ? attrs
						.get("imsssistemas").get(0).toString() : "");
				usuario.setPerfiles(attrs.get("imssperfiles") != null ? attrs
						.get("imssperfiles").get(0).toString() : "");
				usuario.setPassword(attrs.get("carLicense") != null ? attrs
						.get("carLicense").get(0).toString() : "");
				usuario.setCorreoElectronico(attrs.get("mail") != null ? attrs
						.get("mail").get(0).toString() : "");
				String estatus = attrs.get("inetUserStatus").get(0).toString()
						+ "";
				usuario.setActivo(estatus.trim().equals(ACTIVO) ? true : false);
				if (usuario.getCorreoElectronico().equals(correoEletronico)) {
					boolean envio = mensajeriaService.enviarCorreoContrasena(
							usuario.getCorreoElectronico(),
							usuario.getCorreoElectronico(), usuario,
							usuario.getPassword());
					if (envio) {
						return usuario;
					} else {
						return null;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuario.", ex);
			throw new AdmonUsuariosException(
					"Error al realizar consulta de usuario", ex);
		} finally {
			desconecta();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #agregarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public String agregarUsuario(final UsuarioDTO usuario)throws AdmonUsuariosException { // Agrega el usuario al LDAP
		conecta();
		try {
			final Attributes attrs = new BasicAttributes();
			final Attribute objectClasses = new BasicAttribute("objectClass");
			final String perfil = "";
			
			// OpenAM 10
//			objectClasses.add("person");
//			objectClasses.add("organizationalPerson");
//			objectClasses.add("inetOrgPerson");
//			objectClasses.add("iplanet-am-auth-configuration-service");
//			objectClasses.add("sunIdentityServerLibertyPPService");
//			objectClasses.add("sunAMAuthAccountLockout");
//			objectClasses.add("sunFederationManagerDataStore");
//			objectClasses.add("iplanet-am-managed-person");
//			objectClasses.add("iPlanetPreferences");
//			objectClasses.add("sunFMSAML2NameIdentifier");
//			objectClasses.add("inetuser");
//			objectClasses.add("iplanet-am-user-service");
//			objectClasses.add("top");
			
			// OpenAM 13
			// AM 6
			objectClasses.add("kbaInfoContainer");
			objectClasses.add("iplanet-am-managed-person");
			objectClasses.add("inetuser");
			objectClasses.add("inetOrgPerson");
			objectClasses.add("sunFMSAML2NameIdentifier");
			objectClasses.add("devicePrintProfilesContainer");
			objectClasses.add("sunIdentityServerLibertyPPService");
			objectClasses.add("iplanet-am-user-service");
			objectClasses.add("forgerock-am-dashboard-service");
			objectClasses.add("sunFederationManagerDataStore");
			objectClasses.add("oathDeviceProfilesContainer");
			objectClasses.add("sunAMAuthAccountLockout");
			objectClasses.add("organizationalPerson");
			objectClasses.add("top");
			objectClasses.add("person");
			objectClasses.add("iplanet-am-auth-configuration-service");
			objectClasses.add("iPlanetPreferences");
			
			
			attrs.put(objectClasses);

			attrs.put(new BasicAttribute("sn", usuario.getApellidoPaterno()));
			attrs.put(new BasicAttribute("cn", usuario.getNombres()));
			attrs.put(new BasicAttribute("givenName", usuario.getApellidoMaterno()));
			attrs.put(new BasicAttribute("inetUserStatus", ACTIVO));
			attrs.put(new BasicAttribute("userPassword", usuario.getPassword()));
			attrs.put(new BasicAttribute("carLicense", usuario.getPassword()));
			usuario.setUid(usuario.getCurp()); // generaUID(usuario)
			attrs.put(new BasicAttribute("uid", usuario.getCurp()));
			attrs.put(new BasicAttribute("mail", usuario.getCorreoElectronico()));

			if (usuario.getCurp() != null)
				attrs.put(new BasicAttribute("employeeNumber", usuario.getCurp()));
			if (usuario.getIdBdtu() != null)
				attrs.put(new BasicAttribute("initials", usuario.getIdBdtu()));
			if (usuario.getNss() != null)
				attrs.put(new BasicAttribute("displayName", usuario.getNss()));
			if (usuario.getClaveDelegacion() != null)
				attrs.put(new BasicAttribute("businessCategory", usuario
						.getClaveDelegacion().toString()));
			if (usuario.getClaveSubDelegacion() != null)
				attrs.put(new BasicAttribute("departmentNumber", usuario
						.getClaveSubDelegacion().toString()));
			if (usuario.getClaveUMF() != null)
				attrs.put(new BasicAttribute("destinationIndicator", usuario
						.getClaveUMF().toString()));
			ctx.createSubcontext(
					generaNombreUsuario(usuario.getCurp(),
							props.getProperty(USUARIOS_DN_BASE)), attrs);
			// if (!perfil.isEmpty()){
			// admonPerfiles.asignaPerfilAUsuario(usuario.getUid(), perfil);
			List<String> areaGrupo = new ArrayList<String>();
			// for(GrupoTO cad: usuario.getLstGrupos()){
			// areaGrupo.add(cad.getNombre());
			// System.out.println(" GRUPO " +cad.getNombre() );
			// }
			// areaGrupo.add(perfil);

			for (PuestoDTO rolint : usuario.getRolesData()) {
				String rol = rolint.getNombrePuesto();
				areaGrupo.add(rol);
			}
			/*
			 * for(ModulosTO modint : usuario.getModulosData()){
			 * areaGrupo.add(modint.getDesModulo()); }
			 */

			admonPerfiles.asignaAreayGrupoAUsuario(usuario.getUid(), areaGrupo);

			// }
			// Falta agregar los roles y modulos y eliminar perfiles y grupos
			return usuario.getUid();
		} catch (Exception ex) {
			logger.error("Error al realizar alta de usuario." + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException("Error al realizar alta de usuario", ex);
		} finally {
			desconecta();
		}
	}

//	public static void main(String[] args) {
//		AdmonUsuarios u = new AdmonUsuarios();
//		try {
//			UsuarioDTO user = u.obtenUsuario("AAAA620512HDFLVD08");
//			if(user!=null)
//			{
//				BeanUtils ut = new BeanUtils();
//				Map m = ut.describe(user);
//				Iterator i = m.keySet().iterator();
//				System.out.println("Valores de entidad");
//				while(i.hasNext())
//				{
//					String key = (String)i.next();
//					System.out.println("ATRIBUTO :" +key+" VALOR: "+m.get(key));
//				}
//			}
//		} catch (AdmonUsuariosException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void describeBean(UsuarioDTO user) {
		AdmonUsuarios u = new AdmonUsuarios();
		try {
			if(user!=null)
			{
				logger.debug("************Se describira los valores del usuario recibido**************");
				BeanUtils ut = new BeanUtils();
				Map m = ut.describe(user);
				Iterator i = m.keySet().iterator();
				logger.debug("Valores de entidad");
				while(i.hasNext())
				{
					String key = (String)i.next();
					logger.debug("ATRIBUTO: " +key+" VALOR: "+m.get(key));
				}
				logger.debug("************Terminan atributos del usuario recibido**************");
			}
		} catch (IllegalAccessException iex) {
			logger.error("::Error::describeBean... " + iex.getMessage());
			logger.error("  ", iex);
		} catch (InvocationTargetException ex) {
			logger.error("::Error::describeBean... " + ex.getMessage());
			logger.error("  ", ex);
		} catch (NoSuchMethodException ne) {
			logger.error("::Error::describeBean... " + ne.getMessage());
			logger.error(ne);
		}
	}
	
	 
	
	@Override
	public String agregarUsuarioConPerfiles(final UsuarioDTO usuario) throws AdmonUsuariosException { // Agrega el usuario al LDAP
		try {
			logger.info("########## INICIANDO AGREGAR USUARIO CON PERFILES ##########");
			
			logger.info("########## UsuarioDTO_recibido=" + usuario + " ##########");
			
			logger.debug("########## usuario.matricula[" + usuario.getMatricula() + "] ##########");
			logger.debug("########## usuario.apellidoPaterno[" + usuario.getApellidoPaterno() + "] ##########");
			logger.debug("########## usuario.apellidoMaterno[" + usuario.getApellidoMaterno() + "] ##########");
			logger.debug("########## usuario.nombres[" + usuario.getNombres() + "] ##########");
			logger.debug("########## usuario.passwd[" + usuario.getPassword() + "] ##########");
			logger.debug("########## usuario.curp[" + usuario.getCurp() + "] ##########");
			logger.debug("########## usuario.correoElectronico[" + usuario.getCorreoElectronico() + "] ##########");
			logger.debug("########## usuario.telefono[" + usuario.getTelefono() + "] ##########");
			logger.debug("########## usuario.departamento[" + usuario.getDescripcionArea() + "] ##########");
			logger.debug("########## usuario.puesto[" + usuario.getDescripcionCargo() + "] ##########");
			logger.debug("########## usuario.idbdtu[" + usuario.getIdBdtu() + "] ##########");
			logger.debug("########## usuario.nss[" + usuario.getNss() + "] ##########");
			logger.debug("########## usuario.delegacion[" + usuario.getClaveDelegacion() + "] ##########");
			logger.debug("########## usuario.subdelegacion[" + usuario.getClaveSubDelegacion() + "] ##########");
			logger.debug("########## usuario.umf[" + usuario.getClaveUMF() + "] ##########");
			logger.debug("########## usuario.roles[" + usuario.getRolesData() != null ? usuario.getRolesData() : "SIN LISTA DE ROLES" + "] ##########");
			
			conectaLDAP(); 
			//describeBean(usuario);
			String entryDN = "uid=" + usuario.getCurp() + "," + baseDN;
			logger.info(":::::::::: uid=" + entryDN + " ::::::::::");
			
			Entry in = new LinkedHashMapEntry(entryDN)
				// OpenAm 10
//				.addAttribute("objectClass", "person")
//				.addAttribute("objectClass", "organizationalPerson")
//				.addAttribute("objectClass", "inetOrgPerson")
//				.addAttribute("objectClass", "iplanet-am-auth-configuration-service")
//				.addAttribute("objectClass", "sunIdentityServerLibertyPPService")
//				.addAttribute("objectClass", "sunAMAuthAccountLockout")
//				.addAttribute("objectClass", "iplanet-am-managed-person")
//				.addAttribute("objectClass", "iPlanetPreferences")
//				.addAttribute("objectClass", "sunFMSAML2NameIdentifier")
//				.addAttribute("objectClass", "inetuser")
//				.addAttribute("objectClass", "iplanet-am-user-service")
				
				// OpenAm 13
				// AM 6
				.addAttribute("objectClass", "kbaInfoContainer")
				.addAttribute("objectClass", "iplanet-am-managed-person")
				.addAttribute("objectClass", "inetuser")
				.addAttribute("objectClass", "inetOrgPerson")
				.addAttribute("objectClass", "sunFMSAML2NameIdentifier")
				.addAttribute("objectClass", "devicePrintProfilesContainer")
				.addAttribute("objectClass", "sunIdentityServerLibertyPPService")
				.addAttribute("objectClass", "iplanet-am-user-service")
				.addAttribute("objectClass", "forgerock-am-dashboard-service")
				.addAttribute("objectClass", "sunFederationManagerDataStore")
				.addAttribute("objectClass", "oathDeviceProfilesContainer")
				.addAttribute("objectClass", "sunAMAuthAccountLockout")
				.addAttribute("objectClass", "organizationalPerson")
				.addAttribute("objectClass", "top")
				.addAttribute("objectClass", "person")
				.addAttribute("objectClass", "iplanet-am-auth-configuration-service")
				.addAttribute("objectClass", "iPlanetPreferences")
				
				.addAttribute("sn", usuario.getApellidoPaterno())
				.addAttribute("cn", usuario.getNombres())
				.addAttribute("inetUserStatus", ACTIVO)
				.addAttribute("userPassword", usuario.getPassword())
				.addAttribute("carLicense", usuario.getPassword())
				.addAttribute("uid", usuario.getCurp())
				.addAttribute("mail", usuario.getCorreoElectronico());
			
			if (usuario.getTelefono() != null && !usuario.getTelefono().equals("")) {
				in.addAttribute("mobile", usuario.getTelefono());
			}
			if (usuario.getApellidoMaterno() != null && usuario.getApellidoMaterno().length()>0) {
				in.addAttribute("givenName", usuario.getApellidoMaterno());
			}
			if (usuario.getDescripcionArea() != null && !usuario.getDescripcionArea().equals("")) {
				in.addAttribute("imssareas", usuario.getDescripcionArea());
			}
			if (usuario.getCurp() != null) {
				in.addAttribute("employeeNumber", usuario.getCurp());
			}
			if (usuario.getDescripcionCargo() != null && !usuario.getDescripcionCargo().equals("")) {
				in.addAttribute("employeeType", usuario.getDescripcionCargo());
				in.addAttribute("title", usuario.getDescripcionCargo());
			}
			if (usuario.getMatricula() != null && !usuario.getMatricula().equals("")) {
				in.addAttribute("imssmatricula", usuario.getMatricula());
			} else {
				in.addAttribute("imssmatricula", "SIN MATRICULA");
			}
			if (usuario.getIdBdtu() != null) {
				in.addAttribute("initials", usuario.getIdBdtu());
			}
			if (usuario.getNss() != null) {
				in.addAttribute("displayName", usuario.getNss());
			}
			if (usuario.getClaveDelegacion() != null && usuario.getClaveDelegacion()>0) {
				in.addAttribute("businessCategory", usuario.getClaveDelegacion().toString());
			}
			if (usuario.getClaveSubDelegacion() != null && usuario.getClaveSubDelegacion() >0) {
				in.addAttribute("departmentNumber", usuario.getClaveSubDelegacion().toString());
			}
			if (usuario.getClaveUMF() != null && usuario.getClaveUMF()>0) {
				in.addAttribute("destinationIndicator", usuario.getClaveUMF().toString());
			}
			String perfilesUsuario = "";
			for (PuestoDTO rolint : usuario.getRolesData()) {
				perfilesUsuario = perfilesUsuario + rolint.getNombrePuesto() + ",";
			}

			if (perfilesUsuario.indexOf(",") > 0)
				perfilesUsuario = perfilesUsuario.substring(0,perfilesUsuario.length() - 1);

			in.addAttribute("imssperfiles", perfilesUsuario);

			if (usuario.getModulosData() != null && usuario.getModulosData().size() > 0) {
				// Sistemas
				String sistemasUsuario = "";
				for (ModuloDTO modint : usuario.getModulosData()) {
					sistemasUsuario = sistemasUsuario + modint.getDesModulo()+ ",";
				}
				if (sistemasUsuario.indexOf(",") > 0) {
					sistemasUsuario = sistemasUsuario.substring(0,sistemasUsuario.length() - 1);
				}
				in.addAttribute("imsssistemas", sistemasUsuario);
			}

		    logger.info("::: Creating an entry...");
		    connection.add(in);
		    logger.info("...done.");
			
		    desconectaLDAP();
		    for (PuestoDTO rolint : usuario.getRolesData()) {
				String rol = rolint.getNombrePuesto();
//				asignaAreayGrupoUsuario(usuario.getCurp(), rol);
				logger.info("::: Actualizando Grupo=" + usuario.getCurp() + ", ROL=" + rol);
				LDAPSingletonConnection.getInstance().asignaAreayGrupoUsuario(usuario.getCurp(), rol);
			}

		    
			return usuario.getUid();
		} catch (Exception ex) {
			logger.error("Error al realizar alta de usuario..." + ex.getMessage());
			logger.error("  ", ex);
			throw new AdmonUsuariosException("Error al realizar alta de usuario", ex);
		} finally {
			desconectaLDAP();
		}
	}

	public  boolean asignaAreayGrupoUsuario(String uid,String areaygrupo) throws AdmonUsuariosException {
    	try {
			logger.debug("************Conectando AreaGrupo 8:17 **********");
			SearchResultEntry answerU = connection.readEntry("uid="+uid+","+baseDN);
			if(answerU!=null){
				 String dnUsuario = generaNombreUsuario(uid,baseDN); 
				 SearchResultEntry answer = connection.readEntry("cn="+areaygrupo+","+baseDNRoles);
				 Entry old = TreeMapEntry.deepCopyOfEntry(answer);
				 if (answer != null) {					 
					 org.forgerock.opendj.ldap.Attribute members=answer.getAttribute("uniqueMember");
					 if (members == null || !members.contains(dnUsuario)) {
						 logger.info("Actualizando AreaGrupo");
						 answer.addAttribute("uniqueMember", dnUsuario);
						 ModifyRequest request = Entries.diffEntries(old, answer);
						 connection.modify(request);					
					 }
				 } else {
		                throw new AdmonUsuariosException("El areaygrupo \"" + areaygrupo + "\" no existe.");
		         }
			}
			return false;
		} catch (LdapException le) {
			logger.error("Error al invocar el LDAP..." + le.getMessage());
			logger.error("  ", le);
		} catch (IOException ioe) {
			logger.error("Error al asigna area y grupo al usuario..." + ioe.getMessage());
			logger.error("  ", ioe);
		} finally {
			logger.debug("*************Desconectando AreaGrupo*************");
		}
    	return false;	
	}
	
	
	
	
//	public  boolean asignaAreayGrupoUssuario(String uid,String areaygrupo) throws AdmonUsuariosException {
//    	conecta();
//    	try {
////    		areaygrupo = "USUARIO_EXTERNO";
//			final Attributes matchAttrsU = new BasicAttributes(true);
//			matchAttrsU.put(new BasicAttribute("uid", uid));
//			final NamingEnumeration<SearchResult> answerU =	ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrsU);
//			if(answerU.hasMoreElements())
//			{
//	        	boolean success = false;
//	              
//	              Attributes matchAttrs2 = new BasicAttributes(true);
//	              String dnUsuario = generaNombreUsuario(uid, props.getProperty(USUARIOS_DN_BASE))+",dc=imss,dc=gob,dc=mx";        	
//	              
//	              Attributes matchAttrs = new BasicAttributes(true);
//	              matchAttrs.put(new BasicAttribute("cn", areaygrupo));            
//	              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);            
//	            if (answer.hasMoreElements()) 
//	            {
//
//
//	                SearchResult result = answer.next();
//	                Attributes attrs = result.getAttributes();
//	                Attribute members = attrs.get("uniqueMember");
//	                Attribute members3 =  new BasicAttribute("uniqueMember");
//	                
//	                if (members == null || !members.contains(dnUsuario)) {
//	                    if (members == null) {
//	                        members = new BasicAttribute("uniqueMember");
//	                    }
//
//	                    if(members.size()==1)
//	                    {
//	                        matchAttrs2.put(new BasicAttribute("description", areaygrupo));
//	                        NamingEnumeration<SearchResult> answer2 = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs2);
//	                        if (answer2.hasMoreElements()) {
//	                            SearchResult result2 = answer2.next();
//	                            Attributes attrs2 = result2.getAttributes();
//	                            Attribute members2 = attrs2.get("uniqueMember");
//	                            if(members2!=null && members2.size()>members.size())
//	                            	members = members2;
//	                        }
//
//	                    }
//	                    
//	                    members3.add(dnUsuario);
//	                      ModificationItem[] mods = new ModificationItem[] {new ModificationItem(DirContext.ADD_ATTRIBUTE, members3)};
//	                    ctx.modifyAttributes(result.getName()+","+props.getProperty(ROLES_DN_BASE), mods);
//	                    success = true;
//	                }
//	            } else {
//	                throw new AdmonUsuariosException("El areaygrupo \"" + areaygrupo + "\" no existe.");
//	            }
//	            return success;
//			}
//			return false;
//        } catch (Exception ex) {
//            log.error("Error al realizar asignacion de rol", ex);
//            throw new AdmonUsuariosException("Error al realizar asignacion de rol", ex);
//        }finally
//        {
//        	desconecta();
//        }
//    }

	
		
//	public static void main(String[] args) {
//		AdmonUsuarios ad = new AdmonUsuarios();
//		try {
//			String info = "47365592,PAMJ780326HJCLRN09@@141184048,RUFF780103HASBRL04@@141184191,CAEM470722MMCRSG02@@141183311,TOGG620120HTCRND09@@52031612,PAMC850603MVZLCL08@@141185478,MACG430912MGTRMD05@@141182221,LASJ830630HHGRNH02@@141184306,GAGS410604MDFLLL01@@13144540,SOPB790221MDFLDR08@@141260330,MOGS930421HJCRDR03@@141258391,GOMJ670622MCLMJN00@@141261985,MAST770107MCLCLL04@@141192856,OAOA670614MMSCCN02@@141187302,LACJ760516HVZNRR03@@141191103,GOPC781004MJCLDR09@@141192636,SARP721006MCSNCT03@@141195313,RIGG731205MDFVRB06@@141264382,CARS650115MDGHMS03@@141185458,JICA620813HCHMBL06@@141261625,CUHE700126HDFRRM07@@141263309,CAHS910516MHGHRG03@@141259358,HEXR660213MTCRXS00@@26886660,OIAJ660513HMNRRR01@@141192378,CAVE631020MCSMLL14@@44150873,HEHR701217HGTRRL06@@141195446,LOHG710115HASPRL03@@141187516,GOCA730901HDFNRN07@@141192783,AOCC800422HYNRVR08@@141186101,IUJO701111MMSTML05@@61243247,HERJ620423HVZRSR06@@141238174,PAOF860224MGRTZL06@@141261611,LORR590902HMNPNF00@@141260260,SEJG430930MDFRML04@@8117450,RULO780922HTSZNS00@@141182248,SARR480325HDGCDD08@@19842324,DOMO740413HCSMRR03@@141186402,TOSH450916HNLRNM01@@141185983,MESS521106MDFJLL05@@141184835,VEMV761230MMCNNR01@@141183656,FOBM691202HDFLRG04@@8740657,VEVO471116HVZRRC01@@37280229,OOTG870105HNLRVM09@@141184777,CAGL731222HSLRRS00@@44325741,MAPH741127MASCSL08@@19822598,EIND771214HSRNBV08@@53132504,OIMM800102HVZRTR01@@141195489,RAPI860310MVZMLR03@@141260439,AEGG831108MJCLRB09@@31729993,MASG690812HCHDNR00@@141194430,GOCJ720628HZSNRV03@@141260938,AOGH700913HTSLNC07@@141191196,RATI670615MPLMRS07@@32919431,OEMK760405MZSRRR07@@141192818,TOSR530515HZSRNB05@@141193492,MASJ770227HNERRN09@@141192950,CARR520810HJCRSY00@@48539443,GACL790916MCLRRN00@@141187735,CAMH820304HMCBNR07@@141192991,ROGA630910MMNSZN07@@141191301,OEJR461007HJCRRS09@@63104561,FOLF731108HVZLGR03@@141186966,VIDA650420HGRDRL01@@141191928,GUHA930302HDFTRN09@@51230006,MAMP670314HVZCND01@@141191808,TEFL540619HDFRNP01@@141187449,FOGI690617HVZLNS09@@141262749,SACR600209HJCNRC13@@141261508,BAMC690531MASRRL00@@14569629,ZUGV511108HGRRTC09@@141259565,EAPL940918HOCSRS01@@141183922,CAGL620228HGTBMB05@@1750123,MARB490329HSPRVR07@@141183403,SILR871018HTSLZC04@@45701178,VACM540404HCMZRR02@@141184325,OIOC441104HGTRLL02@@3363835,JIOR781102HJCMCC04@@141187025,CAFA950511MCHHLN09@@5890652,HURJ470723HVZRVN00@@141184775,MALA840705HDFNRN09@@141260458,SOLA700408MSRRNL01@@141260443,DERD720415HCCLLV05@@141260648,GACC891025HSRRTR11@@141245972,MAMF650920HMNLNL01@@141192306,OEEA550516MQTLSS01@@46320382,JURM740706MMNRMR02@@141186845,OOGR780809HNERRM09@@141186621,CAHG851029HMNRRR08@@141186629,SALD880127HDGLPR01@@141187363,PEOT590423MOCXRR07@@21890179,PAVD820728HSLNZN05@@8377174,AIPT570531HMCRSM09@@23267745,CACF640321MSLSSL00@@9213195,BELJ680621HDFRGN00@@141187548,EAAT490307HGRVBM03@@23117053,RALC740630MTSMPR06@@141187535,MAGE650611MCMCTV14@@141193336,SEZA680430HMCGPL03@@141194915,TECS771020HOCRBR03@@16109479,WEFE640826MDFNRL03@@141186347,CAER580901HHGRSC05@@17369193,SARE630803HDFNZD07@@141230332,BANA950106HBCRXR03@@141187602,CEPF910517HSPDRR02@@141190811,SAMH560209HNLLCM08@@35605992,ZUNL830918MSPXYR01@@141192749,AAZR820812MCMRPC08@@141191958,BOCR670829MGTNRM00@@46102523,LAEF740810HMNRNR01@@7449529,VEGS741118HCMLRL08@@141260674,PEEP610128MJCRST03@@141180903,EARE840117MASSDL02@@141182470,GUMJ891019HDFZRS00@@141183540,PAVA821121HDGZGL18@@19080608,PESM660310MSLRRR04@@141184349,PEAM621226HGRRVR08@@141184130,IITA880419MGTRVN04@@141184574,AAVA831017HJCNNR00@@141262996,EIOE910215MNLSLL01@@52727860,NORM680909HVZLLG02@@43175963,GOGG630920MOCNRL06@@141186743,PAHJ830820HCLZRN09@@42878214,ROGL620722HTSJRS01@@141192256,COGA870911HOCNNR08@@60951090,CUPC821101MYNHCR05@@46152289,GAMJ650104HDFRRM04@@141222791,MELJ860509MPLLZL03@@141260298,SAZA490717MTSNML03@@141187386,SARE800511MNTNMS07@@141184726,PIGP911008HNTMNB05@@54434159,PEAD701230HOCTGV04@@141192688,BOTJ600615HMNLRN03@@141192918,LEBR660623HZSNXF05@@141199259,SIDH901204HDGFLC05@@141185158,MACM600726HDFRLG08@@38754564,RITR690304HNLXRF00@@141188865,LOZS650320MJCPML02@@141199234,LOXM760514HNEPXR05@@32403918,LOPG740811HCHPYR08@@43296338,ROGM730823HTSJRR02@@141218246,SAVL710119MSRVSY00@@141187182,CAGG621003HDFHRR01@@141192607,MEMI820914HNEDDG05@@141188206,CEXR680823HSPDXD04@@141191471,EARI530429HDFSMS09@@20585282,MEVD730122HSLNGN00@@141195371,PAGF860218MDFDNL00@@40903394,GAVR750918HMCRLB07@@141258001,RERC820729MMCYVR05@@141261836,HEGC860605HJCRTH07@@44398664,LARK790427MDFZSR05@@141181159,RORG710413HVZDVS05@@141183062,HEHA710703MTSRRL04@@28541568,MACF830514HCLNRR08@@141184268,LUGS680107MCHJMN08@@44181551,AIAS711010MASVCL05@@141185034,ROGG800516HCHDRS16@@141182585,SACE570927HSPZDN07@@141184312,FOMJ490908MPLLRN04@@43159985,AEAM720929HTSLGG01@@141185889,CAFC861216HGTMLS07@@141182990,MEAL750511MDGRMR09@@88040217,AATA770925HDFLYN06@@141258051,OISP690707HBCLNB04@@141245974,RITM890228MMNCRR03@@141186954,AUAL710205HDFGVS08@@44154925,MAAL690330HASRLS04@@52208426,BEVJ841129HVZLZS07@@141192703,GOAS770726HVZNNN00@@44115738,MESG600807HZSJLN07@@47944164,VAEF711025HJCRSR06@@141185182,CAMR551224HVZMNF04@@141187665,HEAL750121HDFRGS00@@46623750,SAAE550214HSRNGV03@@141185447,FOST610425MCHLLR00@@141191498,TOAA240529HJCRRL03@@141191920,AAAR850111HNELVB06@@25388606,ROVA710605MSRBLL08@@26520439,GOGA790317MDFNNL03@@141186924,RODH650304HGTDLC07@@141196293,VELF530205HMNNRR00@@141267901,GOPE731021MMSMLR01@@9808351,ZASC630421HGTVNR08@@53296595,FOVO800918HDFLLS02@@141267744,VIGC831218HJCLNS00@@43897547,ROML900311MDFDRS05@@141181586,GURJ680409HDFTMN05@@141185290,OOMA910920MPLSNL01@@141183873,LOHP680605HPLPRS03@@141184410,GORJ580222HCLNDR03@@141186292,HEEI710129MDFRSS09@@141181058,EUSJ670221HZSSNS00@@139180305,LOCJ821214MCHYHL04@@25849787,ROBF780402HJCCBR07@@15046235,ZAVA660424HHGPRL02@@141192271,ROCG660428MGTDLL01@@141187629,CANC740827HJCSXR09@@44176180,CACM700215MCCSRR04@@141262906,DIMJ480830HSLZNS01@@141258177,RORC870505HDFXDR07@@141195617,CARM360828MCSHDL02@@141261817,VARA830719HPLLSL09@@141260789,OIRC631101HCSRMN00@@141238924,GOAL851009HDFNLS09@@141251516,ROCO740729HSPCRS02@@141261572,CAAF710622HMNSVR09@@141193119,FOFJ661103HZSLLS00@@25730016,VACM701225MBCZLY01@@43775190,MORD880526HSPNMV07@@141242121,IAEJ931019HSLBLV09@@141187175,PEON430727MNERRN02@@141191332,VAFA720827HJCLGR05@@141260852,PEZG670512HVZRPM04@@52546570,BEAC620727HOCLQR04@@141184259,CABO640603MMCRCL01@@141184406,ROAR650610HGTDLY04@@141182090,LARA461202MDFLGL09@@141183303,GOLA710616HCHMZR00@@141186589,PEMR701121MJCRXM09@@33007512,OEML800721MZSRRL00@@88042855,LADZ971202MGTLZX06@@141183960,AAAS710125MDFNNM08@@58899126,MAFV721108HQRRNC04@@5047715,ROPJ670828MJCDLL02@@4962849,HEMM550323HJCDLG01@@37595410,CUGV800822HNLRRC02@@141182360,ROEA641222HCSDSD05@@141183477,PAGA690616MMSDLL09@@141187057,MACL570807HMNGLS14@@141193206,CAAP610629HDFSRD07@@141245971,RAML730914MSRMRZ00@@141260796,ZECR740830MJCPHS08@@141185164,OEBC901128MCHRNL08@@141186849,PECA600810HGRXRN03@@141187371,TEGE670620MSLRMR08@@60861578,PECF731108HYNRSD01@@141186612,SIOB810701HJCRLL08@@51429864,RORL730327MVZSDT02@@44336372,FOAH710924HZSLVC03@@141218244,VERM910213HSRGMG07@@141186439,MORM860507HDFRDC08@@141189722,EILB941209MBSMNT09@@141192172,MEGE720714HHGNTR03@@141190183,FEGH561018HDFRRR06@@141190360,KAMJ720507HBSCDS05@@141190983,GAMS370708HCCRJL02@@141264402,LOHM690530HASPRG09@@141258450,CUAC641021MDFRRL08@@141263146,AOPA310828HGRLRG03@@34044889,CALC750304MCMHCN02@@141182526,LOLG371101MASPPR08@@44710775,DEVJ630214HJCLLV04@@141183135,RERJ850919HYNYVR02@@141182910,VAEM531125MSLLSN08@@141182221,LASJ830630HHGRNH02@@141184306,GAGS410604MDFLLL01@@47365592,PAMJ780326HJCLRN09@@141184048,RUFF780103HASBRL04@@141184191,CAEM470722MMCRSG02@@141183311,TOGG620120HTCRND09@@141187302,LACJ760516HVZNRR03@@141185458,JICA620813HCHMBL06@@52031612,PAMC850603MVZLCL08@@13144540,SOPB790221MDFLDR08@@141185478,MACG430912MGTRMD05@@141192378,CAVE631020MCSMLL14@@141187516,GOCA730901HDFNRN07@@141260330,MOGS930421HJCRDR03@@141258391,GOMJ670622MCLMJN00@@141192856,OAOA670614MMSCCN02@@141192636,SARP721006MCSNCT03@@141195313,RIGG731205MDFVRB06@@26886660,OIAJ660513HMNRRR01@@44150873,HEHR701217HGTRRL06@@141195446,LOHG710115HASPRL03@@141264382,CARS650115MDGHMS03@@141192783,AOCC800422HYNRVR08@@141260260,SEJG430930MDFRML04@@141191103,GOPC781004MJCLDR09@@141261625,CUHE700126HDFRRM07@@141263309,CAHS910516MHGHRG03@@141259358,HEXR660213MTCRXS00@@141186101,IUJO701111MMSTML05@@141261985,MAST770107MCLCLL04@@141261611,LORR590902HMNPNF00@@141268351,GOGH830923HNENNM05@@141271266,AUSR780719HNERNG08@@61243247,HERJ620423HVZRSR06@@141238174,PAOF860224MGRTZL06@@141268003,GASA521006HDFRLR01@@141270497,COMI821108HMNRXG02@@141182248,SARR480325HDGCDD08@@8117450,RULO780922HTSZNS00@@8740657,VEVO471116HVZRRC01@@19822598,EIND771214HSRNBV08@@141186402,TOSH450916HNLRNM01@@141183656,FOBM691202HDFLRG04@@141185983,MESS521106MDFJLL05@@141184835,VEMV761230MMCNNR01@@141184777,CAGL731222HSLRRS00@@44325741,MAPH741127MASCSL08@@31729993,MASG690812HCHDNR00@@141192818,TOSR530515HZSRNB05@@141194430,GOCJ720628HZSNRV03@@141186966,VIDA650420HGRDRL01@@19842324,DOMO740413HCSMRR03@@37280229,OOTG870105HNLRVM09@@141260439,AEGG831108MJCLRB09@@141195489,RAPI860310MVZMLR03@@32919431,OEMK760405MZSRRR07@@141193492,MASJ770227HNERRN09@@141192950,CARR520810HJCRSY00@@141187735,CAMH820304HMCBNR07@@141192991,ROGA630910MMNSZN07@@63104561,FOLF731108HVZLGR03@@141187449,FOGI690617HVZLNS09@@141191196,RATI670615MPLMRS07@@141261508,BAMC690531MASRRL00@@141260938,AOGH700913HTSLNC07@@141191928,GUHA930302HDFTRN09@@51230006,MAMP670314HVZCND01@@141191808,TEFL540619HDFRNP01@@11592725,VESL700904HGTRNS07@@10713745,HUVG360310HGTCZD01@@53132504,OIMM800102HVZRTR01@@141259565,EAPL940918HOCSRS01@@141191301,OEJR461007HJCRRS09@@141268708,MEHF920510HSPNRR06@@141271171,SAMR800124HMCNRM02@@141268901,PETY601203MVZRPL01@@14569629,ZUGV511108HGRRTC09@@48539443,GACL790916MCLRRN00@@141262749,SACR600209HJCNRC13@@141268768,AAGA740617HDFLRB08@@141271033,FIRC780715HCHRMR16@@141270631,DICM850329HYNZRR02@@141269803,AERA791001HOCVZN02@@141183922,CAGL620228HGTBMB05@@1750123,MARB490329HSPRVR07@@141183403,SILR871018HTSLZC04@@141184325,OIOC441104HGTRLL02@@45701178,VACM540404HCMZRR02@@3363835,JIOR781102HJCMCC04@@141187025,CAFA950511MCHHLN09@@5890652,HURJ470723HVZRVN00@@141184775,MALA840705HDFNRN09@@46320382,JURM740706MMNRMR02@@141186845,OOGR780809HNERRM09@@23267745,CACF640321MSLSSL00@@141192306,OEEA550516MQTLSS01@@23117053,RALC740630MTSMPR06@@141187602,CEPF910517HSPDRR02@@141260674,PEEP610128MJCRST03@@141260458,SOLA700408MSRRNL01@@141260443,DERD720415HCCLLV05@@141260648,GACC891025HSRRTR11@@141187363,PEOT590423MOCXRR07@@141245972,MAMF650920HMNLNL01@@7449529,VEGS741118HCMLRL08@@141187535,MAGE650611MCMCTV14@@141186621,CAHG851029HMNRRR08@@141186629,SALD880127HDGLPR01@@21890179,PAVD820728HSLNZN05@@8377174,AIPT570531HMCRSM09@@9213195,BELJ680621HDFRGN00@@141187548,EAAT490307HGRVBM03@@141193336,SEZA680430HMCGPL03@@141194915,TECS771020HOCRBR03@@141186347,CAER580901HHGRSC05@@141190811,SAMH560209HNLLCM08@@141191958,BOCR670829MGTNRM00@@17369193,SARE630803HDFNZD07@@35605992,ZUNL830918MSPXYR01@@141192749,AAZR820812MCMRPC08@@141270705,SEND501117HTSRXM09@@141271494,SAZC610812MHGNXL04@@141271888,NAMA770927HCHVXN14@@141269806,BECW860812HMNTSL04@@16109479,WEFE640826MDFNRL03@@141230332,BANA950106HBCRXR03@@46102523,LAEF740810HMNRNR01@@141268008,JIBJ830615HDFMXL11@@141271222,ROMF700322HMNDCR00@@61814982,EIEL700529MDFSSL08@@141270407,MOOJ640131HQTRCN06@@15143837,CECA720203MDFRMN07@@13443485,PAUG870129HTLRRB04@@141183540,PAVA821121HDGZGL18@@141184130,IITA880419MGTRVN04@@141180903,EARE840117MASSDL02@@141184349,PEAM621226HGRRVR08@@141182470,GUMJ891019HDFZRS00@@141184726,PIGP911008HNTMNB05@@54434159,PEAD701230HOCTGV04@@19080608,PESM660310MSLRRR04@@141184574,AAVA831017HJCNNR00@@52727860,NORM680909HVZLLG02@@141222791,MELJ860509MPLLZL03@@141192918,LEBR660623HZSNXF05@@141260298,SAZA490717MTSNML03@@141187386,SARE800511MNTNMS07@@40903394,GAVR750918HMCRLB07@@141192256,COGA870911HOCNNR08@@43175963,GOGG630920MOCNRL06@@141186743,PAHJ830820HCLZRN09@@42878214,ROGL620722HTSJRS01@@141192688,BOTJ600615HMNLRN03@@141258001,RERC820729MMCYVR05@@60951090,CUPC821101MYNHCR05@@46152289,GAMJ650104HDFRRM04@@141185158,MACM600726HDFRLG08@@141261836,HEGC860605HJCRTH07@@141262996,EIOE910215MNLSLL01@@141199234,LOXM760514HNEPXR05@@38754564,RITR690304HNLXRF00@@141188865,LOZS650320MJCPML02@@32403918,LOPG740811HCHPYR08@@141187182,CAGG621003HDFHRR01@@141199259,SIDH901204HDGFLC05@@141218246,SAVL710119MSRVSY00@@43296338,ROGM730823HTSJRR02@@141192607,MEMI820914HNEDDG05@@141191471,EARI530429HDFSMS09@@141195371,PAGF860218MDFDNL00@@141268331,LEDJ850818HMCNRN06@@141188206,CEXR680823HSPDXD04@@141270231,UEAA610628HJCRNR02@@141271692,AAPO890506HDFMRS05@@64237328,MAVP810812MMCRVR07@@20585282,MEVD730122HSLNGN00@@141271272,MOPR710830HMCNLS06@@141272272,ROGJ660611HMCJMR04@@141269007,PIME910928HMCCRD01@@141184268,LUGS680107MCHJMN08@@141182990,MEAL750511MDGRMR09@@141182585,SACE570927HSPZDN07@@141184312,FOMJ490908MPLLRN04@@44398664,LARK790427MDFZSR05@@141181159,RORG710413HVZDVS05@@141183062,HEHA710703MTSRRL04@@28541568,MACF830514HCLNRR08@@44181551,AIAS711010MASVCL05@@141185034,ROGG800516HCHDRS16@@141185889,CAFC861216HGTMLS07@@43159985,AEAM720929HTSLGG01@@141186954,AUAL710205HDFGVS08@@52208426,BEVJ841129HVZLZS07@@88040217,AATA770925HDFLYN06@@141187665,HEAL750121HDFRGS00@@141185447,FOST610425MCHLLR00@@141186924,RODH650304HGTDLC07@@141258051,OISP690707HBCLNB04@@141192703,GOAS770726HVZNNN00@@141245974,RITM890228MMNCRR03@@9808351,ZASC630421HGTVNR08@@141185182,CAMR551224HVZMNF04@@44154925,MAAL690330HASRLS04@@141191498,TOAA240529HJCRRL03@@141267744,VIGC831218HJCLNS00@@26520439,GOGA790317MDFNNL03@@141191920,AAAR850111HNELVB06@@47944164,VAEF711025HJCRSR06@@141269656,AAEF431002MSLLSL07@@39052981,MOHA710802MVZRRN03@@53296595,FOVO800918HDFLLS02@@46623750,SAAE550214HSRNGV03@@25388606,ROVA710605MSRBLL08@@141271540,CUOJ510927MVZRVN04@@141271203,VIAM890307HNTLLN08@@44115738,MESG600807HZSJLN07@@141196293,VELF530205HMNNRR00@@141271721,AECJ690103HJCCRR09@@141271069,UXAB730427MYNCLT02@@141269837,SADM700430HNTNLN03@@141267901,GOPE731021MMSMLR01@@43897547,ROML900311MDFDRS05@@141181586,GURJ680409HDFTMN05@@139180305,LOCJ821214MCHYHL04@@141183873,LOHP680605HPLPRS03@@15046235,ZAVA660424HHGPRL02@@141187629,CANC740827HJCSXR09@@141186292,HEEI710129MDFRSS09@@141181058,EUSJ670221HZSSNS00@@25849787,ROBF780402HJCCBR07@@141184410,GORJ580222HCLNDR03@@141251516,ROCO740729HSPCRS02@@141261817,VARA830719HPLLSL09@@141185290,OOMA910920MPLSNL01@@141258177,RORC870505HDFXDR07@@141260789,OIRC631101HCSRMN00@@141192271,ROCG660428MGTDLL01@@44176180,CACM700215MCCSRR04@@141195617,CARM360828MCSHDL02@@141238924,GOAL851009HDFNLS09@@141262906,DIMJ480830HSLZNS01@@141261572,CAAF710622HMNSVR09@@141193119,FOFJ661103HZSLLS00@@25730016,VACM701225MBCZLY01@@141242121,IAEJ931019HSLBLV09@@141191332,VAFA720827HJCLGR05@@141271867,GOCM580525MDFMMR02@@141260852,PEZG670512HVZRPM04@@43775190,MORD880526HSPNMV07@@141187175,PEON430727MNERRN02@@141270779,HEGM490314MCSRZT00@@141271014,PELM871020MJCRMR00@@141268616,GAXF540707HHGRXR00@@141270477,RIMI570120MCSNDN06@@52546570,BEAC620727HOCLQR04@@88042855,LADZ971202MGTLZX06@@5047715,ROPJ670828MJCDLL02@@141182360,ROEA641222HCSDSD05@@141183477,PAGA690616MMSDLL09@@141183960,AAAS710125MDFNNM08@@141184259,CABO640603MMCRCL01@@141184406,ROAR650610HGTDLY04@@141182090,LARA461202MDFLGL09@@141183303,GOLA710616HCHMZR00@@141186589,PEMR701121MJCRXM09@@33007512,OEML800721MZSRRL00@@58899126,MAFV721108HQRRNC04@@4962849,HEMM550323HJCDLG01@@37595410,CUGV800822HNLRRC02@@141187057,MACL570807HMNGLS14@@141260796,ZECR740830MJCPHS08@@141185164,OEBC901128MCHRNL08@@141186612,SIOB810701HJCRLL08@@141186439,MORM860507HDFRDC08@@141193206,CAAP610629HDFSRD07@@141245971,RAML730914MSRMRZ00@@141186849,PECA600810HGRXRN03@@141187371,TEGE670620MSLRMR08@@60861578,PECF731108HYNRSD01@@51429864,RORL730327MVZSDT02@@44336372,FOAH710924HZSLVC03@@141192172,MEGE720714HHGNTR03@@33187684,OIML711116HCHLNS03@@141190183,FEGH561018HDFRRR06@@141218244,VERM910213HSRGMG07@@141189722,EILB941209MBSMNT09@@141258450,CUAC641021MDFRRL08@@141190360,KAMJ720507HBSCDS05@@141190983,GAMS370708HCCRJL02@@141270984,ROCG761201HTSDGL08@@141263146,AOPA310828HGRLRG03@@141269570,ROAG530625HCHBRL09@@141270306,HEXA760417MOCRXL07@@11581567,FETA750312HGTRRN09@@141264402,LOHM690530HASPRG09@@141272312,JIRL810209MSRMYR00@@34044889,CALC750304MCMHCN02@@44710775,DEVJ630214HJCLLV04@@141183213,PEGL770323HCLRNS09@@141183971,ROCM510912HASBLR06@@141182526,LOLG371101MASPPR08@@141183135,RERJ850919HYNYVR02@@141182910,VAEM531125MSLLSN08@@35683584,RURK890130MCHZSR09@@3624382,PEZF770420HJCRNR03@@141182642,LOLJ650917HASPNM07@@141185887,VAAC760207HNERGR04@@47749331,BERM690303HNTRYR04@@141190728,SORG491212MDFRVD06@@141251533,RECC700906HDFVVR06@@44873939,LICM660606HCMMHR08@@141193498,COAL730430HMCNNS07@@141186530,SESR640224MVZGRB01@@141185271,EOHA850111HDGSRL01@@63707262,GUEJ860116HDFZSS01@@43401785,IACL761225HTSBRS00@@21113817,RAMM700325HSLMRG03@@26222151,COSN720927MDFRNR01@@58163340,REHA841019HQRYDL06@@38763530,LATJ641204HTSRRN00@@141189826,AACJ770103HMCLNS00@@141267803,CACJ821116HMCRRS01@@141187246,AIBR700207HMCVLC03@@141184990,LOHA730517HTLBRL08@@141268408,GUDG540218MDFTMD07@@141187886,RERE490223MVZYDL02@@57671814,BERJ820314HCCRMN05@@141191938,CASB730607MDFSLL00@@141193350,GADA571030HZSLZN01@@141186086,CAHT610528HSLBRR02@@46815428,VEVD530130HCMLRN01@@141262866,CAOE781226HSPSLD01@@141262938,MODD651222HTCRSV05@@141191173,AUVL620512MJCBLR09@@27064130,GOOA670701MZSMTN05@@141187834,MAGC650206MBCRRR02@@141268733,BOVK740503MNLRLR02@@141271138,RALA690805HCSMPB05@@141271238,MOPJ661016HMNSGL00@@141262846,MAMR421011HNLRRF09@@47466822,SEGD850806HNTPXN08@@141270434,GOHT520227MMNNRR05@@141269198,HEPJ680101HJCRXS03@@141270466,GOMC670113MMNMRL09";
//			String[] users = info.split("@@");
//			for(int i = 0 ; i < users.length ; i ++)
//			{
//				System.out.println("-----------------------------------------------------------------------------");
//				String[] u = ((String)users[i]).split(",");
//				String idPerson = u[0];
//				String curp = u[1];
//				System.out.println("Se modificara el usuario :"+curp.trim()+" con el id de persona:"+idPerson.trim());
//				UsuarioDTO user = ad.obtenUsuario(curp.trim());
//				if(user!=null)
//				{
//					user.setIdBdtu(idPerson.trim());
//					if(ad.modificarUsuario(user))
//						System.out.println("Se modifico correctamente el usuario:"+curp.trim());
//				}
//				else
//					System.out.println("El usuario no se encontro en el ldap");
//				System.out.println("-----------------------------------------------------------------------------");
//			}
//			
//		} catch (AdmonUsuariosException e) {
//			e.printStackTrace();
//		}
//	}

	
	public static void main(String[] args) {
	AdmonUsuarios ad = new AdmonUsuarios();
	try {

		File f = new File("E:\\logPasswors.txt"); 

		FileWriter w = new FileWriter(f); 
		BufferedWriter bw = new BufferedWriter(w); 
		PrintWriter wr = new PrintWriter(bw);    

		String info = "MARC870611HBCCDR07,00001000000303288206@@MIGE540901MCSRND07,00001000000303355861@@MXME701229HDFRDN03,00001000000303690433@@RORG840829HSPDSR00,00001000000303770481@@MIRK820214MTCRMR04,00001000000303849113@@LUCI571101MMNCRR06,00001000000303903959@@AURA751202HVZGZN01,00001000000303951443@@VACL721113MDFRRR00,00001000000303974384@@CUCD851207HMCRRV06,00001000000303929375@@AAFG580819MDFNLR07,00001000000304046666@@VIRS620828HCLLVR02,00001000000304045272@@AIVH851224HMCRLC08,00001000000304220412@@CADD720106HVZRLN03,00001000000304216015@@PEGJ901002HVZXTN02,00001000000304187320@@MASR730714MDFRLT09,00001000000304278612@@ROCA481201HMCMRL09,00001000000304242900@@MASR730714MDFRLT09,00001000000304269277@@SAFC860616HDGLLH05,00001000000304237834@@VIRS620828HCLLVR02,00001000000304327357@@MEMR860311HMNZLD01,00001000000304396059@@SEMJ650713HTLRRL03,00001000000304423468@@MOMM761203MPLRLY07,00001000000304294068@@BAMJ890225HMNRRQ08,00001000000304486351@@NALM761225MCLVPR01,00001000000304485849@@VIGO760603MSLLLL01,00001000000304495843@@TAHS800127MMCPRN04,00001000000303942736@@BIGE701216HMSRRS01,00001000000303892109@@ROMC860607MQRDRY13,00001000000304408442@@VIRS620828HCLLVR02,00001000000304693457@@FOBD750313MVZLRN09,00001000000304557019@@BARI831213HJCRZN08,00001000000304254455@@SAGC860323HJCNRR00,00001000000304706877@@AOVJ750708HJCCLN02,00001000000304628783@@AUMD840806MSRGDN02,00001000000304774936@@LODJ600205HJCMZV02,00001000000304705250@@TOOE740904HJCRRD08,00001000000304496311@@AOGS811005HCHCRR04,00001000000304464257@@SAGC331122MDFNMC04,00001000000304766109@@MEMR860311HMNZLD01,00001000000304834446@@TEGB480313HTSRNL06,00001000000304847640@@ROUJ541214HDFDGM06,00001000000304826036@@FIAA580404HNTRRN02,00001000000304629800@@VIGO760603MSLLLL01,00001000000304884597@@GOCY771214MSRNSN04,00001000000304883005@@JAML650310MDFCDL07,00001000000304894516@@TAHL620613MDFMGR05,00001000000304864007@@RERG680406MCHSMB06,00001000000304867789@@LOMP850621MJCPRL07,00001000000304964302@@RUHI650824HBCZRS00,00001000000304910955@@GUZP870416HVZTVS07,00001000000304896285@@LOAN581003MPLPRT03,00001000000304857981@@MEFN871021MHGJLR03,00001000000304938169@@PEGJ901002HVZXTN02,00001000000304952242@@AUMD840806MSRGDN02,00001000000305002049@@FEAH531208MNLRGL02,00001000000305013024@@FOSA611120HNLLNN02,00001000000304931992@@EICN731125MCHSPY04,00001000000304994156@@AUMD840806MSRGDN02,00001000000305011609@@SAVA730130HCCNRB06,00001000000305023417@@LAHR720116HVZRRF08,00001000000304665174@@PAAR660529MGTDLS00,00001000000305035503@@RORE690706MSRDNL04,00001000000304948728@@CUDA771102MDFRZL05,00001000000305050344@@PIQH611201HMCCNL02,00001000000304682394@@GOLK761101MYNNPR01,00001000000304929538@@FEAH531208MNLRGL02,00001000000305078466@@RAFB360402HOCMLL09,00001000000304993981@@FEAH531208MNLRGL02,00001000000305040645@@ROBW780218HCHMTL09,00001000000305070842@@AATA770925HDFLYN06,00001000000305026742@@MXAS800822MCHNVL05,00001000000305108941@@GAOC510714HJCRLR07,00001000000304952218@@VAHF631120HTLSRL09,00001000000305060169@@LOHG731110HDFZRR02,00001000000305115542@@GUWA770730MZSNLN01,00001000000304970908@@POCS520119HJCNSR06,00001000000305154680@@MAAM680119HDFYRR06,00001000000304977463@@ROAD721010HDFJRV00,00001000000305179184@@ROSA640802MNLSNN06,00001000000304780544@@GACJ711030HMNRHV00,00001000000305148360@@NOTL740912MDFRZR04,00001000000305167072@@GOOS550411HSPNRR08,00001000000305194347@@LADZ971202MGTLZX06,00001000000305223132@@AATA770925HDFLYN06,00001000000305241117@@WEFE640826MDFNRL03,00001000000305012734@@PEMA940304MMCRJD08,00001000000304499752@@RALC740630MTSMPR06,00001000000305080333@@PEAD701230HOCTGV04,00001000000305154193@@AATA770925HDFLYN06,00001000000305278364@@SIMC951020HSLLRH04,00001000000304692655@@ZASJ790428HPLVNN07,00001000000305202009@@HEMV740728HDFRRC00,00001000000305071659@@ROAI740512HVZDGS06,00001000000305240559@@GOBA600626HPLNND07,00001000000305239899@@WAPC541208MTSLRN06,00001000000305275070@@ROCC590907HDFDRR02,00001000000305248518@@CACJ811014HYNNPS09,00001000000305261252@@PEZK920612MJCRRR05,00001000000305286947@@MEGG821211HVZNRR02,00001000000305300930@@HEME500303HTLRRM04,00001000000305285018@@RERG680406MCHSMB06,00001000000305330129@@AACE730310HNTRNF04,00001000000305161715@@VALM860529HDFLNN09,00001000000305307270@@MELE711210HSRRRN07,00001000000305349154@@HEMV740728HDFRRC00,00001000000305361505@@RIML730923HSPCNN04,00001000000305248903@@CAIM690929HPLRBG09,00001000000304312725@@VIMJ821209HDFLNN05,00001000000305214212@@TUDL751111HNERMS09,00001000000305175332@@HERF890904HBCRSR04,00001000000305359519@@MAJN850817MSPRRR06,00001000000305404717@@MEVO730402MGTDNF00,00001000000305151394@@ROAA830726HMCJLD09,00001000000305008768@@ROLE860421HTSJGD04,00001000000305389185@@SADM810307MVZNLR00,00001000000305001205@@MOTF570111MJCNRL02,00001000000305382314@@CAFJ660729HDFHRR01,00001000000304874927@@CAEN791203HNTHSX09,00001000000305149646@@FOMJ550609HMCLRV01,00001000000305326927@@MOPE770403MSLRLL01,00001000000305053627@@BATG560903HOCRRL00,00001000000305431399@@ROCE841001MDFDHL01,00001000000304775278@@MOEN830923MCSRSR09,00001000000305434953@@EORM871022MCSSNR07,00001000000305432801@@VIRS620828HCLLVR02,00001000000305444106@@BEMS660610MGRLRC00,00001000000305420042@@LATL830212MTSRRZ06,00001000000305332705@@GAOC510714HJCRLR07,00001000000305442339@@PATM970115HMCZPG05,00001000000305468883@@MOVJ841008HSPRNN08,00001000000305470382@@DIRL780715MDFZMZ00,00001000000305369709@@SOBM691102HPLTTR07,00001000000305468101@@MARJ750730HDFSMS04,00001000000305410172@@MAMI600102HDFYRG07,00001000000305345531@@BUMA720314HJCNRR08,00001000000305416245@@WEFE640826MDFNRL03,00001000000305445399@@VIRA730824HDFXBR04,00001000000305043032@@LOFD401109HVZMLN03,00001000000305482349@@EUOS651116MBCSRL05,00001000000305431730@@OEAM791011HPLLYG09,00001000000305296094@@GOHA000718MVZNRNA4,00001000000305519081@@VIGT800718HCMLZL07,00001000000305494984@@BADS800815HDFRZH02,00001000000305452011@@GOLA760818HNLNPN04,00001000000305305039@@HEME540603MVZRNV05,00001000000305141668@@TAHS800127MMCPRN04,00001000000304974277@@MANR891217HTSRRL05,00001000000305574473@@MOSL960517MSLLTN09,00001000000305376600@@MEGS600928HMNZLL05,00001000000305512406@@AOSE901010MTCBSV08,00001000000305280642@@VEEJ710908HDFLSM01,00001000000304894980@@GOHF020123HVZNRRA9,00001000000305518987@@CATD741203HYNRCV05,00001000000305353811@@MEFN871021MHGJLR03,00001000000305266487@@NASM780719HJCRLR03,00001000000305461083@@MOSA741123MPLRNL06,00001000000305561763@@CAMJ591124HVZRRN04,00001000000305569517@@GAGG560428HCLRMR04,00001000000305268110@@GAGG560428HCLRMR04,00001000000305585209@@BEMS660610MGRLRC00,00001000000305559306@@ROCS611107MDFSRL04,00001000000305350229@@DAGE770623HTSVRB03,00001000000305609834@@GUSL950816MMNLGR02,00001000000305552483@@SALL450609MDFNPS06,00001000000305615501@@SAMJ701115HDFLRN02,00001000000305522329@@EIPL931023HMCSNS01,00001000000305604300@@CACV721008MVZRSR08,00001000000305592766@@VAHF631120HTLSRL09,00001000000305632412@@ROOA690217HDFJSD08,00001000000304668765@@CAER780831HMCSSM02,00001000000305645585@@GOLB800622MDFNCR06,00001000000305556409@@SIBM520501MNEMKR01,00001000000305644583@@FOBD750313MVZLRN09,00001000000305535249@@GUBS850713MCMTRS02,00001000000305632275@@EAMO741020HSRSRM17,00001000000305607298@@RIMD871121MQTCRN02,00001000000305693712@@CUMS790819HHGRNN12,00001000000305364460@@GOGJ690124HMCNRN00,00001000000305533980@@LOOA611212MGTPLR02,00001000000305558677@@ROSF630801HCLDLL04,00001000000305638653@@SAGC601026HDFNNR04,00001000000305713151@@NAMF951113HJCVRR01,00001000000305675665@@TOAJ660124HCLRGR07,00001000000305522024@@OECF521127HCMLBR08,00001000000305672445@@VAHF631120HTLSRL09,00001000000305632412@@VAHF631120HTLSRL09,00001000000305632412@@MEMO620429HSPZRD08,00001000000305499741@@BARI831213HJCRZN08,00001000000305759318@@GOMA571128HDFNDN06,00001000000305752564@@BARI831213HJCRZN08,00001000000305759326@@CAFJ460620HDFMRN01,00001000000305715313@@GACA401105HCLRGR02,00001000000305752313@@LAFL460605HVZLRS02,00001000000305794232@@EAAD590726HGRSRV06,00001000000305741122@@RAGA850205HDFMRL04,00001000000305761094@@CUHD751023HSPVRV05,00001000000304978959@@AAAL680121HHGLVS06,00001000000304619120@@JIHG720625HDFMRL03,00001000000305426639@@CATY760321HCSSDN01,00001000000305620574@@RILB760508MASSPT07,00001000000305658743@@TOOH851211HJCRRC01,00001000000305520109@@GURL680803MPLZYD06,00001000000305651927@@RIZB580729MVZVRT09,00001000000305731915@@JAZR470228HGRMGG03,00001000000305396796@@HUNA830120MJCRVN09,00001000000305616972@@PODG670616MYNTRL00,00001000000305688601@@MASA760620HMCRNN05,00001000000305105949@@MEMO620429HSPZRD08,00001000000305499741@@EIRS491220HVZNML00,00001000000305104050@@LAGM840718HDFRRG06,00001000000305796957@@VEJA780806MPLLNN08,00001000000305653617@@NEQV750203HDFRZL00,00001000000305807507@@HESR700722HSPRNL09,00001000000305818519@@GOJN821228MMSNMM01,00001000000305848622@@TOMA600320HJCRRR09,00001000000305827769@@ROSA930221HNLDCD06,00001000000305817733@@ROGJ630626HOCDZS08,00001000000305843081@@TAGC651017HDFPSR03,00001000000304569014@@SOGD760929HNLTRV05,00001000000305269009@@RERR600422HJCTMF00,00001000000305796945@@PAVS750803HGTLRL09,00001000000305867528@@SAZS881115MMCNRR00,00001000000305707016@@VERJ630622MMCGMS09,00001000000305595270@@MENN881116MDFNRT06,00001000000305933167@@PEZK920612MJCRRR05,00001000000305286947@@PEZK920612MJCRRR05,00001000000305286947@@LAHR720116HVZRRF08,00001000000305839466@@GARA710223MMNRDN05,00001000000305895778@@SAAJ511227HPLNGS03,00001000000305622638@@MOSO800521HDFNLM02,00001000000305822833@@SAMA611118HCSNRN04,00001000000305706333@@RIBM551018HMNVRR01,00001000000305378167@@RORJ780127HJCSNN05,00001000000305920895@@RIRR630714HOCVBL06,00001000000305942511@@HEHH630211HMCRRC05,00001000000305795238@@GOJO681010HDFMSL10,00001000000305705233@@PESD900108HGRXNN09,00001000000305967385@@MEMO620429HSPZRD08,00001000000305499741@@FORL610311HJCLMS10,00001000000305877662@@HERM630809MQTRDR05,00001000000305998958@@IICE720206HBCRRN02,00001000000305896775@@ROLL560508MMCJPD05,00001000000305974027@@AIRR710219HDFVZL08,00001000000305998097@@AUZA830318MDFGVN15,00001000000305989416@@CIFR660208HPLDLF04,00001000000305952984@@TAMR621022HYNMNN08,00001000000304246303@@LOMJ510331MGRPRN08,00001000000305974932@@MOBP720628HSPNRD00,00001000000306000705@@SAAJ511227HPLNGS03,00001000000305622638@@SAAJ511227HPLNGS03,00001000000305622638@@MALD570908HHGRNR04,00001000000306000803@@PEGM630225HTSRRR02,00001000000306014850@@PEGM630225HTSRRR02,00001000000306014850@@CATG630320MYNHJL05,00001000000305661069@@PEGM630225HTSRRR02,00001000000306014850@@BIGJ730304HSPRNL09,00001000000305898280@@MOBP720628HSPNRD00,00001000000306000705@@HIAR440108HCHNRL05,00001000000305959824@@FUFM961212MBCNLC07,00001000000305871602@@NUFJ730430HCHXLN08,00001000000305765210@@REVN040918MSLNLLB9,00001000000306011001@@PAIO641129HDFLBS06,00001000000306029878@@FUGG711108HDFYTR05,00001000000305927164@@BAGA780801HPLLRL03,00001000000306060639@@VEHE831223HSPNDN01,00001000000306013084@@VAMV500917HVZZNC06,00001000000305346700@@OOCM581205HZSRRN07,00001000000306036374@@MEHS740512HCLNRR09,00001000000305848950@@GASJ770505HDFRLN04,00001000000305761110@@BAGD830402HMCNMN04,00001000000305220563@@DICE750205MNTZRD00,00001000000305337031@@DEGR630119MDFLRC07,00001000000306081760@@FOIA551028MDFLBR08,00001000000305778659@@SAQJ710822HSLNNS05,00001000000306021023@@CARK850202MSLSLR01,00001000000306100241@@ROGA671108MBCMNM02,00001000000305170170@@GOPM790726HTSNRS01,00001000000306115787@@LASA711216HGRGNL08,00001000000306083579@@LASA711216HGRGNL08,00001000000306083579@@BEDI600615MZSRVS04,00001000000306107739@@EILF810120HSRNLB06,00001000000306095498@@HEAR810730HCHRNL07,00001000000305433558@@LUDT820213MDFGRN02,00001000000306083287@@CAPM820504HCMHMR00,00001000000306116708@@CADM810929HYNNZG05,00001000000306108990@@MOCF450518HCHRSR02,00001000000305919836@@FAVJ851120MBCCLS07,00001000000306132019@@AERL780209HBCRCS04,00001000000306065328@@ROCE841001MDFDHL01,00001000000305830088@@OORA910508MCSSML01,00001000000305400468@@MACE631212HPLRSL04,00001000000306023155@@MEPF731007HSRNRR05,00001000000305654918@@DIMI700219HDFZNS01,00001000000306159879@@HECJ760417HDFRRN08,00001000000306040022@@AAVR700221MCHRLT03,00001000000306059931@@MEPA820422HDGJDR06,00001000000306171236@@RIMM761114HDFVYR02,00001000000306168370@@GOHS460915MDFDRL05,00001000000306093928@@CAAE700215MDFHLL00,00001000000305657681@@CARA770928HDFSML03,00001000000305694684@@SAEF970124MCLNLR06,00001000000305707127@@ROMA771013HGTSRN07,00001000000306018769@@IASV901004MNLBNN09,00001000000306188940@@AEVJ570703HJCCLS08,00001000000306208371@@HEDL840216HOCRZS09,00001000000306149541@@MICF580120HDFTNR03,00001000000305767087@@ROPC750911MJCDRL03,00001000000306063751@@CAGU701204HMSRNR05,00001000000306150703@@PAFD880202HJCLRV07,00001000000306082228@@CIHE511201HOCRRD06,00001000000305979062@@HELJ790413HGTRZN08,00001000000306106755@@BANR800712HJCRXC04,00001000000306318431@@VABF691005HDFLLR05,00001000000306300799@@PEMD771120MCSXRM03,00001000000306178946@@UIRR750315HDFRDY03,00001000000306173031@@RACS660927MCSMSN06,00001000000305478559@@SAVA790530HHGDLD07,00001000000306233289@@SEHP440417MSPRRL03,00001000000305778699@@ROTF830509HJCCVR06,00001000000306213250@@DUMM920117HCLRRR09,00001000000306403643@@MEPB780215MCLDRT03,00001000000305964713@@CAQE660108HNLNRZ09,00001000000306051745@@GOME850619MCSRNL02,00001000000306415552@@HAAY720623MDFRYR01,00001000000306300910@@GASF700306HNLRNR09,00001000000304490027@@RURL841110HOCZZM06,00001000000305198897@@BIEM451206MDFRSR06,00001000000306199040@@VEGM640722HVZLNR02,00001000000305997811@@VIGH791028HCLLRC09,00001000000306418618@@EAAH820822HSPSRG09,00001000000305819136@@COMM530707HNTBRN05,00001000000305859416@@LOPA740220HMCPRL02,00001000000305820412@@GAGE750106HDFLRD05,00001000000306052563@@RUGO690710HBCZLM05,00001000000306132787@@CAEL700402HPLSSS03,00001000000306456040@@BERC650127MDFCDR00,00001000000306308978@@PEFB500727HMNXRN00,00001000000306458973@@AAGM781010HVZLRG09,00001000000306423855@@LOAP550223HOCPLD07,00001000000306368047@@VEVE870228HNLGND03,00001000000305884798@@CAME710705HDFHLD04,00001000000306501528@@LOAP550223HOCPLD07,00001000000306503436";
		String[] users = info.split("@@");
		for(int i = 0 ; i < users.length ; i ++)
		{
			wr.write("-----------------------------------------------------------------------------"+"\n");
			String[] u = ((String)users[i]).split(",");
			String curp = u[0];
			String pass = u[1];
			wr.write("Se modificara el usuario :"+curp.trim()+" con el password:"+pass+"\n");
			UsuarioDTO user = ad.obtenUsuario(curp.trim());
			if (user != null) {
				wr.write("La contrasenia actual es: " + user.getPassword() + "\n");
				if (ad.regeneraPassword(curp, pass)) {
					wr.write("Se modifico correctamente el usuario: " + curp.trim() + "\n");
				}
			}
			else {
				wr.write("El usuario no se encontro en el ldap." + "\n");
			}
			wr.write("-----------------------------------------------------------------------------"+"\n");
		}

		wr.close(); 
		bw.close(); 

	} catch (AdmonUsuariosException ex) {
		logger.error("Error al invocar el LDAP..." + ex.getMessage());
		logger.error("  ", ex);
	} catch (IOException ioe) {
		logger.error("Error al invocar el LDAP..." + ioe.getMessage());
		logger.error("  ", ioe);
	}
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #modificarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean modificarMatriculaUsuario(String curp, String cveMatricula)throws AdmonUsuariosException {
		conecta();
		try {
			logger.info("########## CURP A BUSCAR PARA MODIFICAR MATRICULA ["+curp+"] ##########");
			logger.info("########## MATRICULA A MODIFICAR ["+cveMatricula+"] ##########");
			
			final String name = generaNombreUsuario(curp,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO original = obtenUsuario(curp);
			if (original != null) {
				boolean success = false;
				final List<ModificationItem> modificaciones = new ArrayList<ModificationItem>();
				
				if (cveMatricula != null && !cveMatricula.equals("")) {
					modificaciones
							.add(new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("imssmatricula", cveMatricula)));
				} else {
					modificaciones
					.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE,
							new BasicAttribute("imssmatricula", "SIN MATRICULA")));
				}
				
				logger.info("########## INICIA ACTUALIZACION DE MATRICULA ########## ");
				
				if (!modificaciones.isEmpty()) {
					logger.info("MODIFICACIONES "
							+ modificaciones.size());
					ctx.modifyAttributes(
							name,
							modificaciones
									.toArray(new ModificationItem[modificaciones
											.size()]));
					success = true;
				}
				return success;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
		} catch (Exception ex) {
			logger.error("Error al realizar modificacion de matricula del usuario", ex);
			throw new AdmonUsuariosException(
					"Error al realizar modificacion de matricula del usuario", ex);
		} finally {
			desconecta();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #modificarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean modificarUsuario(final UsuarioDTO usuario)throws AdmonUsuariosException {
		conecta();
		try {
			final String name = generaNombreUsuario(usuario.getCurp(),
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO original = obtenUsuario(usuario.getCurp());
			if (original != null) {
				boolean success = false;
				final List<ModificationItem> modificaciones = new ArrayList<ModificationItem>();
				
				if (usuario.getApellidoMaterno()!=null) { 
					if(!usuario.getApellidoMaterno().equals(original.getApellidoMaterno())) {
						modificaciones.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("givenName", usuario.getApellidoMaterno())));
					}
				}
				if (!usuario.getApellidoPaterno().equals(original.getApellidoPaterno())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"sn", usuario.getApellidoPaterno())));
				}
				if (!usuario.getNombres().equals(original.getNombres())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"cn", usuario.getNombres())));
				}
				// if (usuario.getNewPassword() != null) {
				// modificaciones.add(new
				// ModificationItem(DirContext.REPLACE_ATTRIBUTE,
				// new BasicAttribute("userPassword",
				// usuario.getNewPassword())));
				// }
				if (usuario.getCorreoElectronico() != null
						&& !usuario.getCorreoElectronico().equals(
								original.getCorreoElectronico())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"mail", usuario.getCorreoElectronico())));
				}
				if (usuario.getSerial() != null) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"carLicense", usuario.getSerial())));
				}

				if (usuario.getClaveDelegacion() != null && usuario.getClaveDelegacion() > 0) {
					modificaciones.add(new ModificationItem( DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("businessCategory", usuario.getClaveDelegacion().toString())));
				} else if (usuario.getClaveSubDelegacion() == null || usuario.getClaveSubDelegacion() < 1) {
					if (original.getClaveDelegacion() != null && original.getClaveDelegacion() > 0) {
						modificaciones.add(new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("businessCategory")));
					}
				}
				if (usuario.getClaveSubDelegacion() != null && usuario.getClaveSubDelegacion() > 0) {
					modificaciones.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("departmentNumber", usuario.getClaveSubDelegacion().toString())));
				} else if (usuario.getClaveSubDelegacion() == null || usuario.getClaveSubDelegacion() < 1) {
					if (original.getClaveSubDelegacion() != null && original.getClaveSubDelegacion() > 0) {
						modificaciones.add(new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("departmentNumber")));
					}
				}
				if (usuario.getClaveUMF() != null && usuario.getClaveUMF() > 0) {
					modificaciones.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("destinationIndicator", usuario.getClaveUMF().toString())));
				} else if (usuario.getClaveUMF() == null || usuario.getClaveUMF() < 1) {
					if (original.getClaveUMF() != null && original.getClaveUMF() > 0) {
						modificaciones.add(new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("destinationIndicator")));
					}
				}

				if (usuario.getCurp() != null
						&& !usuario.getCurp().equals(original.getCurp())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"employeeNumber", usuario.getCurp())));
				}
				if (usuario.getIdBdtu() != null
						&& !usuario.getIdBdtu().equals(original.getIdBdtu())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"initials", usuario.getIdBdtu())));
				}
				if (usuario.getDescripcionArea() != null
						&& !usuario.getDescripcionArea().equals(
								original.getDescripcionArea())) {
					modificaciones
							.add(new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("imssareas", usuario
											.getDescripcionArea())));
				}

				if (usuario.getDescripcionCargo() != null
						&& !usuario.getDescripcionCargo().equals(
								original.getDescripcionCargo())) {
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"title", usuario.getDescripcionCargo())));
					modificaciones.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
									"employeeType", usuario
											.getDescripcionCargo())));
				}
				
				if (usuario.getMatricula() != null
						&& !usuario.getMatricula().equals(
								original.getMatricula())) {
					modificaciones
							.add(new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("imssmatricula", usuario
											.getMatricula() != null
											&& !usuario.getMatricula().equals("") ? usuario
											.getMatricula() : "SIN MATRICULA")));
				}

				final String perfilOriginal = ""; // original.getPerfil() !=
													// null ?
													// original.getPerfil().getNombre()
													// :
				final String perfilNuevo = ""; // usuario.getPerfil() != null ?
												// usuario.getPerfil().getNombre()
												// :
				if (!perfilNuevo.equals(perfilOriginal)) {
					if (perfilNuevo.equals("")) {
						throw new AdmonUsuariosException(
								"Debe asignar un perfil al usuario.");
					} else {
						admonPerfiles.cambiaPerfilAUsuario(usuario.getCurp(),
								""); // usuario.getPerfil().getNombre()
						modificaciones.add(new ModificationItem(
								DirContext.REPLACE_ATTRIBUTE,
								new BasicAttribute("employeeType", ""))); // usuario.getPerfil().getNombre()
					}
				}

				if (!modificaciones.isEmpty()) {
					logger.info("MODIFICACIONES "
							+ modificaciones.size());
					ctx.modifyAttributes(
							name,
							modificaciones
									.toArray(new ModificationItem[modificaciones
											.size()]));
					success = true;
				}
				return success;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
		} catch (Exception ex) {
			logger.error("Error al realizar modificacion de usuario", ex);
			throw new AdmonUsuariosException(
					"Error al realizar modificacion de usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #desactivarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	
	@Override
	public boolean desactivarUsuario(final String uid)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "
						+ usuario.getNombres());
				if (usuario.isActivo()) {
					ctx.modifyAttributes(name,
							new ModificationItem[] { new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("inetUserStatus",
											INACTIVO)) });
					success = true;
				}
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #desactivarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean regeneraPassword(final String uid,String newPass) throws AdmonUsuariosException{
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "	+ usuario.getNombres());
				final List<ModificationItem> modificaciones = new ArrayList<ModificationItem>();
				modificaciones.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("carLicense", newPass)));				
				modificaciones.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("userPassword", newPass)));				

				ctx.modifyAttributes(name,modificaciones.toArray(new ModificationItem[modificaciones.size()]));					
				success = true;
			}
			else
			{
				return true;
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario"+ ex);
			return false;
		} finally {
			desconectaLDAP();
		}
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #desactivarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean agregarModuloAprobador(final String uid, String mod)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "
						+ usuario.getNombres());
				String modulos = usuario.getModulos();
				if (modulos != null && modulos.trim().length() > 0)
					modulos = modulos + "," + mod;
				else
					modulos = mod;
				ctx.modifyAttributes(name,
						new ModificationItem[] { new ModificationItem(
								DirContext.REPLACE_ATTRIBUTE,
								new BasicAttribute("imsssistemas", modulos)) });
				success = true;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	@Override
	public boolean agregaPerfilAprobador(final String uid, String per)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es " + usuario.getNombres());
				String perfiles = usuario.getPerfiles();
				if (perfiles != null && perfiles.trim().length() > 0)
					perfiles = perfiles + "," + per;
				else
					perfiles = per;
				ctx.modifyAttributes(name,	new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE,	new BasicAttribute("imssperfiles", perfiles)) });
				success = true;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #desactivarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean modificaModuloAprobador(final String uid, String mods)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "
						+ usuario.getNombres());
				ctx.modifyAttributes(name,
						new ModificationItem[] { new ModificationItem(
								DirContext.REPLACE_ATTRIBUTE,
								new BasicAttribute("imsssistemas", mods)) });
				success = true;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #desactivarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean modificaPerfilAprobador(final String uid, String perfs)throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es " + usuario.getNombres());
				ctx.modifyAttributes(name,new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("imssperfiles", perfs)) });
				success = true;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	@Override
	public boolean borrarModuloAprobador(final String uid, String mod)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "
						+ usuario.getNombres());
				String modulos = usuario.getModulos();
				if (modulos.length() > 0) {
					if (modulos.indexOf(",") > 0)
						modulos = modulos.replaceAll(mod, "");
					if (modulos.indexOf(",,") > 0)
						modulos = modulos.replaceAll(",,", ",");
					else
						modulos = modulos.replaceAll(mod, "");

					if (modulos.length() > 0) {
						if (modulos.indexOf(",") == 0)
							modulos = modulos.substring(1, modulos.length());
						if (modulos.charAt(modulos.length() - 1) == ',')
							modulos = modulos
									.substring(0, modulos.length() - 1);
					}
					if (modulos != null && modulos.equals(""))
						modulos = " ";
					ctx.modifyAttributes(
							name,
							new ModificationItem[] { new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("imsssistemas", modulos)) });
					success = true;
				}
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	@Override
	public boolean borrarPerfilAprobador(final String uid, String per)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				logger.info("el employee number es "
						+ usuario.getNombres());
				String perfiles = usuario.getPerfiles();
				if (perfiles.length() > 0) {
					if (perfiles.indexOf(",") > 0)
						perfiles = perfiles.replaceAll(per, "");
					if (perfiles.indexOf(",,") > 0)
						perfiles = perfiles.replaceAll(",,", ",");
					if (perfiles.charAt(perfiles.length() - 1) == ',')
						perfiles = perfiles.substring(0, perfiles.length() - 1);
					else
						perfiles = perfiles.replaceAll(per, "");

					if (perfiles.length() > 0) {
						if (perfiles.indexOf(",") == 0)
							perfiles = perfiles.substring(1, perfiles.length());
						if (perfiles.charAt(perfiles.length() - 1) == ',')
							perfiles = perfiles.substring(0,
									perfiles.length() - 1);
					}
					if (perfiles != null && perfiles.equals(""))
						perfiles = " ";

					ctx.modifyAttributes(
							name,
							new ModificationItem[] { new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("imssperfiles", perfiles)) });
					success = true;
				}
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #activarUsuario(mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO)
	 */
	@Override
	public boolean activarUsuario(final String uid)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				if (!usuario.isActivo()) {
					ctx.modifyAttributes(
							name,
							new ModificationItem[] { new ModificationItem(
									DirContext.REPLACE_ATTRIBUTE,
									new BasicAttribute("inetUserStatus", ACTIVO)) });
					success = true;
				}
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al desactivar usuario", ex);
			throw new AdmonUsuariosException("Error al desactivar usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionRemote
	 * #eliminarUsuario(java.lang.String)
	 */
	@Override
	public boolean eliminarUsuario(String uid) throws AdmonUsuariosException {
		conecta();
		try {
			boolean success = false;
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO usuario = obtenUsuario(uid);
			if (usuario != null) {
				if ("" != null && !"".isEmpty()) {// if (usuario.getPerfil() !=
													// null &&
													// !usuario.getPerfil().getNombre().isEmpty())
													// {
					admonPerfiles.revocaPerfilAUsuario(uid);
				}
				ctx.destroySubcontext(name);
				success = true;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
			return success;
		} catch (Exception ex) {
			logger.error("Error al eliminar usuario", ex);
			throw new AdmonUsuariosException("Error al eliminar usuario", ex);
		} finally {
			desconecta();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal
	 * #actualizaPerfilUsuario(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean actualizaPerfilUsuario(final String uid, final String nombre)
			throws AdmonUsuariosException {
		conecta();
		try {
			final String name = generaNombreUsuario(uid,
					props.getProperty(USUARIOS_DN_BASE));
			final UsuarioDTO original = obtenUsuario(uid);
			if (original != null) {
				boolean success = false;
				if (!"".equals(nombre)) { // original.getPerfil().getNombre()
					if (!nombre.isEmpty()) {
						ctx.modifyAttributes(name,
								new ModificationItem[] { new ModificationItem(
										DirContext.REPLACE_ATTRIBUTE,
										new BasicAttribute("employeeType",
												nombre)) });
					} else {
						ctx.modifyAttributes(name,
								new ModificationItem[] { new ModificationItem(
										DirContext.REMOVE_ATTRIBUTE,
										new BasicAttribute("employeeType")) });
					}
					success = true;
				}
				return success;
			} else {
				throw new AdmonUsuariosException("El usuario no existe.");
			}
		} catch (Exception ex) {
			logger.error("Error al realizar modificacion de usuario", ex);
			throw new AdmonUsuariosException(
					"Error al realizar modificacion de usuario", ex);
		} finally {
			desconecta();
		}
	}

	/**
	 * Metodo para generar identificadores de usuario
	 * 
	 * @param usuario
	 *            objeto con la informacion del usuario
	 * @return identificador del usuario
	 */
	private String generaUID(final UsuarioDTO usuario)
			throws AdmonUsuariosException {
		final StringBuilder uid = new StringBuilder();
		uid.append(usuario.getNombres().substring(0, 1));
		uid.append('.');
		uid.append(usuario.getApellidoPaterno());
		uid.append(usuario.getApellidoMaterno().substring(0, 1));
		if (existeUsuario(uid.toString())) {
			int contador = 1;
			for (contador = 1; existeUsuario(uid.toString() + contador); ++contador)
				;
			uid.append(contador);
		}
		return uid.toString();
	}

	/**
	 * Metodo para generar el nombre distinguido del usuario dentro del
	 * directorio LDAP.
	 * 
	 * @param uid
	 *            identificador del usuario
	 * @return nombre distinguido
	 */
	public static String generaNombreUsuario(final String uid,
			final String baseDN) {
		final StringBuilder name = new StringBuilder("uid=");
		name.append(uid).append(',').append(baseDN);
		return name.toString();
	}
	
	
	/**
	 * Metodo para generar el nombre distinguido del usuario dentro del
	 * directorio LDAP.
	 * 
	 * @param uid
	 *            identificador del usuario
	 * @return nombre distinguido
	 */
	public static String generaNombreUsuarioDesc(final String uid,
			final String baseDN) {
		final StringBuilder name = new StringBuilder("uid=");
		name.append(uid).append(',').append(baseDN).append(",dc=imss,dc=gob,dc=mx");
		return name.toString();
	}

	public UsuarioDTO buscaUsuario(final String cn, final String sn,
			final String employeeNumber, final String mail,
			final String employeeType) throws AdmonUsuariosException {
		conecta();
		UsuarioDTO usuario = null;

		try {
			logger.debug("entra buscar usuarios" + "cn:" + cn + ",sn:" + sn
					+ ",employeeNumber:" + employeeNumber + ",mail:" + mail
					+ ",employeeType:" + employeeType);
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("cn", cn));
			matchAttrs.put(new BasicAttribute("sn", sn));
			matchAttrs
					.put(new BasicAttribute("employeeNumber", employeeNumber));
			matchAttrs.put(new BasicAttribute("mail", mail));
			// matchAttrs.put(new BasicAttribute("employeeType", employeeType));

			final NamingEnumeration<SearchResult> answer = ctx.search(
					props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			if (answer.hasMoreElements()) {
				SearchResult result = answer.nextElement();
				Attributes attrs = result.getAttributes();

				usuario = new UsuarioDTO(
						attrs.get("cn") != null ? attrs.get("cn").get()
								.toString() : "",
						attrs.get("sn") != null ? attrs.get("sn").get()
								.toString() : "",
						attrs.get("givenName") != null ? attrs.get("givenName")
								.get().toString() : "",
						attrs.get("uid") != null ? attrs.get("uid").get()
								.toString() : "",
						attrs.get("userPassword") != null ? attrs
								.get("userPassword").get().toString() : "",
						"",
						ACTIVO.equals(attrs.get("inetUserStatus") != null ? attrs
								.get("inetUserStatus").get().toString()
								: ""), attrs.get("mail") != null ? attrs
								.get("mail").get(0).toString() : "",
						attrs.get("businessCategory") != null ? Integer
								.valueOf(attrs.get("businessCategory").get(0)
										.toString()) : null,
						attrs.get("departmentNumber") != null ? Integer
								.valueOf(attrs.get("departmentNumber").get(0)
										.toString()) : null,
						attrs.get("destinationIndicator") != null ? Integer
								.valueOf(attrs.get("destinationIndicator")
										.get(0).toString()) : null,
						attrs.get("employeeNumber") != null ? attrs
								.get("employeeNumber").get(0).toString() : "",
						attrs.get("initials") != null ? attrs.get("initials")
								.get(0).toString() : "");
			}

		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuario", ex);
			throw new AdmonUsuariosException(
					"Error al realizar consulta de usuario", ex);
		} finally {
			desconecta();
		}
		return usuario;
	}

	/* JLBC perfiles Metodo que concatena los perfiles para guardar en el LDAP */
	public String concatenaPerfiles(List<PerfilDTO> lstPerfiles) {
		String perfiles = "";
		if (lstPerfiles.size() > 0) {
			for (PerfilDTO perfil : lstPerfiles) {
				perfiles += perfil.getPuestoDTO().getNombrePuesto() + ",";
			}
			if (perfiles.lastIndexOf(",") == perfiles.length() - 1)
				perfiles = perfiles.substring(0, perfiles.length() - 1);
		}
		logger.info("Concatenando Perfil --> " + perfiles);
		return perfiles;
	}

	public String eliminaUsuario(final String curp)
			throws AdmonUsuariosException {
		conecta();
		try {
			ctx.destroySubcontext(generaNombreUsuario(curp,
					props.getProperty(USUARIOS_DN_BASE)));
			return curp;
		} catch (Exception ex) {
			logger.error("Error al realizar eliminacion de usuario", ex);
			throw new AdmonUsuariosException(
					"Error al realizar eliminacion de usuario", ex);
		} finally {
			desconecta();
		}
	}

	public String agregarUsuarioExterno(final UsuarioDTO usuario)
			throws AdmonUsuariosException {
		conecta();
		try {
			logger.info("###### AGREGAR USUARIO EXTERNO ######");
			final Attributes attrs = new BasicAttributes();
			final Attribute objectClasses = new BasicAttribute("objectClass");
			final String perfil = ""; // usuario.getPerfil() != null ?
										// usuario.getPerfil().getNombre() : "";
			final String perfilGenerico = "USUARIO_EXTERNO";
			// OpenAm 10
//			objectClasses.add("person");
//			objectClasses.add("organizationalPerson");
//			objectClasses.add("inetOrgPerson");
//			objectClasses.add("iplanet-am-auth-configuration-service");
//			objectClasses.add("sunIdentityServerLibertyPPService");
//			objectClasses.add("sunAMAuthAccountLockout");
//			objectClasses.add("sunFederationManagerDataStore");
//			objectClasses.add("iplanet-am-managed-person");
//			objectClasses.add("iPlanetPreferences");
//			objectClasses.add("sunFMSAML2NameIdentifier");
//			objectClasses.add("inetuser");
//			objectClasses.add("iplanet-am-user-service");
//			objectClasses.add("top");
			
			// OpenAm 13
			// AM 6
			objectClasses.add("kbaInfoContainer");
			objectClasses.add("iplanet-am-managed-person");
			objectClasses.add("inetuser");
			objectClasses.add("inetOrgPerson");
			objectClasses.add("sunFMSAML2NameIdentifier");
			objectClasses.add("devicePrintProfilesContainer");
			objectClasses.add("sunIdentityServerLibertyPPService");
			objectClasses.add("iplanet-am-user-service");
			objectClasses.add("forgerock-am-dashboard-service");
			objectClasses.add("sunFederationManagerDataStore");
			objectClasses.add("oathDeviceProfilesContainer");
			objectClasses.add("sunAMAuthAccountLockout");
			objectClasses.add("organizationalPerson");
			objectClasses.add("top");
			objectClasses.add("person");
			objectClasses.add("iplanet-am-auth-configuration-service");
			objectClasses.add("iPlanetPreferences");
			
			
			attrs.put(objectClasses);

			attrs.put(new BasicAttribute("cn", usuario.getNombres()));
			attrs.put(new BasicAttribute("inetUserStatus", ACTIVO));
			attrs.put(new BasicAttribute("userPassword", usuario.getSerial()));
			attrs.put(new BasicAttribute("uid", usuario.getCurp()));
			attrs.put(new BasicAttribute("mail", usuario.getCorreoElectronico()));

			if (usuario.getApellidoMaterno() != null) {
				attrs.put(new BasicAttribute("givenName", usuario.getApellidoMaterno()));
			}
			if (usuario.getApellidoPaterno() != null) {
				attrs.put(new BasicAttribute("sn", usuario.getApellidoPaterno()));
			}
			if (usuario.getSerial() != null) {
				attrs.put(new BasicAttribute("carLicense", usuario.getSerial()));
			}
			if (usuario.getCurp() != null) {
				attrs.put(new BasicAttribute("employeeNumber", usuario.getCurp()));
			}
			if (usuario.getIdBdtu() != null) {
				attrs.put(new BasicAttribute("initials", usuario.getIdBdtu()));
			}
			if (usuario.getNss() != null) {
				attrs.put(new BasicAttribute("displayName", usuario.getNss()));
			}
			if (usuario.getClaveDelegacion() != null) {
				attrs.put(new BasicAttribute("businessCategory", usuario
						.getClaveDelegacion().toString()));
			}
			if (usuario.getClaveSubDelegacion() != null) {
				attrs.put(new BasicAttribute("departmentNumber", usuario
						.getClaveSubDelegacion().toString()));
			}
			if (usuario.getClaveUMF() != null) {
				attrs.put(new BasicAttribute("destinationIndicator", usuario
						.getClaveUMF().toString()));
			}

			ctx.createSubcontext(generaNombreUsuario(usuario.getCurp(),
							props.getProperty(USUARIOS_DN_BASE)), attrs);

			if (!perfil.isEmpty()) {
				admonPerfiles.asignaPerfilAUsuarioExterno(usuario.getCurp(),
						perfil);
			} else {
				admonPerfiles.asignaPerfilAUsuarioExterno(usuario.getCurp(),
						perfilGenerico);
			}
			return usuario.getCurp();
		} catch (Exception ex) {
			logger.error("###### Error al realizar el alta de usuario ######", ex);
			throw new AdmonUsuariosException(
					"Error al realizar alta de usuario", ex);
		} finally {
			desconecta();
		}
	}

	public boolean validaUsuario(final String uid, final String password,
			final String curp) throws AdmonUsuariosException {
		conecta();
		try {
			boolean found = false;
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("uid", uid));
			matchAttrs.put(new BasicAttribute("employeeNumber", curp));
			matchAttrs.put(new BasicAttribute("carLicense", password));
			final NamingEnumeration<SearchResult> answer = ctx.search(
					props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			found = answer.hasMoreElements();
			return found;

		} catch (Exception ex) {
			logger.error("###### EL USUARIO TIENE UN ERROR EN EL UID, PASSWORD O CURP ",
					ex);
			throw new AdmonUsuariosException(
					"Valida si el usuario uid password y curp", ex);
		} finally {
			desconecta();
		}
	}

	public boolean validaUsuarioEstatus(final String uid,
			final String password, final String curp)
			throws AdmonUsuariosException {
		conecta();
		try {
			boolean found = false;
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("uid", uid));
			matchAttrs.put(new BasicAttribute("employeeNumber", curp));
			matchAttrs.put(new BasicAttribute("carLicense", password));
			matchAttrs.put(new BasicAttribute("inetUserStatus", ACTIVO));
			final NamingEnumeration<SearchResult> answer = ctx.search(
					props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			found = answer.hasMoreElements();
			return found;

		} catch (Exception ex) {
			logger.error("EL USUARIO TIENE UN ERROR EN EL UID, PASSWORD O CURP ",
					ex);
			throw new AdmonUsuariosException(
					"Valida si el usuario uid password y curp", ex);
		} finally {
			desconecta();
		}
	}

	@Override
	public List<UsuarioDTO> listaUsuarios(int delegacion, int subdelegacion,
			int umf) throws AdmonUsuariosException {
		conecta();
		try {
			List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
			logger.info("::: delegacion " + delegacion);
			int businessCategory = -99;
			int departmentNumber = -99;
			int destinationIndicator = -99;
			int contador = 0;
			final Attributes matchAttrs = new BasicAttributes(true);
			boolean flagagregar = false;
			matchAttrs.put(new BasicAttribute("objectClass", "person"));
			final NamingEnumeration<SearchResult> answer = ctx.search(
					props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			SearchResult person;
			Attributes attrs;
			PerfilDTO perfil;
			while (answer.hasMoreElements()) {
				flagagregar = false;
				person = answer.nextElement();
				attrs = person.getAttributes();

				try {
					if (attrs.get("businessCategory") != null)
						businessCategory = Integer.valueOf(attrs
								.get("businessCategory").get(0).toString());
				} catch (Exception e) {
					businessCategory = -99;
				}
				try {
					if (attrs.get("departmentNumber") != null)
						departmentNumber = Integer.valueOf(attrs
								.get("departmentNumber").get(0).toString());
				} catch (Exception e) {
					departmentNumber = -99;
				}
				try {
					if (attrs.get("destinationIndicator") != null)
						destinationIndicator = Integer.valueOf(attrs
								.get("destinationIndicator").get(0).toString());
				} catch (Exception e) {
					destinationIndicator = -99;
				}

				if (umf != -99) {
					if (destinationIndicator == umf)
						flagagregar = true;
				}

				if (subdelegacion != -99) {
					if (departmentNumber == subdelegacion)
						flagagregar = true;
				}

				if (delegacion != -99) {
					if (businessCategory == delegacion)
						flagagregar = true;
				}

				if (flagagregar) {
					++contador;
					usuarios.add(new UsuarioDTO(
							attrs.get("cn") != null ? attrs.get("cn").get()
									.toString() : "",
							attrs.get("sn") != null ? attrs.get("sn").get()
									.toString() : "",
							attrs.get("givenName") != null ? attrs
									.get("givenName").get().toString() : "",
							attrs.get("uid") != null ? attrs.get("uid").get()
									.toString() : "",
							"",
							"",
							ACTIVO.equals(attrs.get("inetUserStatus") != null ? attrs
									.get("inetUserStatus").get().toString()
									: ""), attrs.get("mail") != null ? attrs
									.get("mail").get(0).toString() : "",
							businessCategory, departmentNumber,
							destinationIndicator,
							attrs.get("employeeNumber") != null ? attrs
									.get("employeeNumber").get(0).toString()
									: "", attrs.get("initials") != null ? attrs
									.get("initials").get(0).toString() : ""));
				}
			}
			logger.info("::: contador " + contador);
			return usuarios;
		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuarios", ex);
			throw new AdmonUsuariosException(
					"Error al realizar consulta de usuarios", ex);
		} finally {
			desconecta();
		}
	}

	@Override
	public List<UsuarioDTO> listaUsuario() throws AdmonUsuariosException {
		conecta();
		try {
			List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
			int businessCategory = -99;
			int departmentNumber = -99;
			int destinationIndicator = -99;
			final Attributes matchAttrs = new BasicAttributes(true);
			matchAttrs.put(new BasicAttribute("objectClass", "person"));
			final NamingEnumeration<SearchResult> answer = ctx.search(
					props.getProperty(USUARIOS_DN_BASE), matchAttrs);
			SearchResult person;
			Attributes attrs;
			PerfilDTO perfil;
			int contador = 0;
			while (answer.hasMoreElements()) {
				person = answer.nextElement();
				attrs = person.getAttributes();

				try {
					if (attrs.get("businessCategory") != null)
						businessCategory = Integer.valueOf(attrs
								.get("businessCategory").get(0).toString());
				} catch (Exception e) {
					businessCategory = -99;
				}
				try {
					if (attrs.get("departmentNumber") != null)
						departmentNumber = Integer.valueOf(attrs
								.get("departmentNumber").get(0).toString());
				} catch (Exception e) {
					departmentNumber = -99;
				}
				try {
					if (attrs.get("destinationIndicator") != null)
						destinationIndicator = Integer.valueOf(attrs
								.get("destinationIndicator").get(0).toString());
				} catch (Exception e) {
					destinationIndicator = -99;
				}

				usuarios.add(new UsuarioDTO(attrs.get("cn") != null ? attrs
						.get("cn").get().toString() : "",
						attrs.get("sn") != null ? attrs.get("sn").get()
								.toString() : "",
						attrs.get("givenName") != null ? attrs.get("givenName")
								.get().toString() : "",
						attrs.get("uid") != null ? attrs.get("uid").get()
								.toString() : "", "", "", ACTIVO.equals(attrs
								.get("inetUserStatus") != null ? attrs
								.get("inetUserStatus").get().toString() : ""),
						attrs.get("mail") != null ? attrs.get("mail").get(0)
								.toString() : "", businessCategory,
						departmentNumber, destinationIndicator, attrs
								.get("employeeNumber") != null ? attrs
								.get("employeeNumber").get(0).toString() : "",
						attrs.get("initials") != null ? attrs.get("initials")
								.get(0).toString() : ""));
			}
			return usuarios;
		} catch (Exception ex) {
			logger.error("Error al realizar consulta de usuarios", ex);
			throw new AdmonUsuariosException(
					"Error al realizar consulta de usuarios", ex);
		} finally {
			desconecta();
		}
	}
	
}