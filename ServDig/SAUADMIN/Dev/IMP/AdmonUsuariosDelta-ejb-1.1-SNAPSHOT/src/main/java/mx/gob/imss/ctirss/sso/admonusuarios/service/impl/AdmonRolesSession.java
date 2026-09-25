package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.ejb.Stateless;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import static mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuarios.generaNombreUsuario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementación del servicio de administración de roles
 * 
 * @author Alan René García Rico
 * @version 1.0
 */
@Stateless (name="admonRolesSession", mappedName="admonRolesSession")
public class AdmonRolesSession implements AdmonRolesSessionLocal {
    /** Log de la clase */
    private static   Log log = LogFactory.getLog(AdmonRolesSession.class);
    /** Contexto de LDAP para manipulación de Usuarios */
    private DirContext ctx = null;
    /** Propiedades de consulta */
    private Properties props = new Properties();
    /** Propiedad del DN Base de roles */
    private static   String ROLES_DN_BASE = "base.dn.roles";
    /** Cadena de propiedad de dn base de usuarios */
    private static   String USUARIOS_DN_BASE = "base.dn.usuarios";
    /** Servicio de administración de perfiles */
    @EJB
    private AdmonPerfilesSessionLocal perfilesService;

    /**
     * Constructor.
     */
    public AdmonRolesSession() {
    }

    /**
     * Método para inicializar el contexto de directorio
     */
//    @PostConstruct
    public void init() {
        try {
            props.load(AdmonUsuarios.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
            ctx = new InitialDirContext(props);
        }catch(Exception ex){
            log.error("Error al crear contexto inicial.", ex);
        }
    }

    
	public void conecta() {
		try {
			props.load(AdmonUsuarios.class
					.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
			ctx = new InitialDirContext(props);
		} catch (Exception ex) {
			log.error("Error al crear contexto inicial.", ex);
		}
	}

	public void desconecta() {
		try {
			ctx.close();
			ctx = null;
		} catch (Exception ex) {
			log.error("Error al crear contexto inicial.", ex);
		}
	}
    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal#obtenRol(java.lang.String)
     */
    @Override
    public PuestoDTO obtenRol(String nombre) throws AdmonUsuariosException {
    	conecta();
        try {
            PuestoDTO rol = null;
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", nombre));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                /* rol = new PuestoDTO(attrs.get("cn").get().toString(), attrs.get("description") != null ? 
                		                                          attrs.get("description").get().toString() : "");  */
            }
            return rol;
        } catch (Exception ex) {
            log.error("Error al realizar consulta de rol", ex);
            throw new AdmonUsuariosException("Error al realizar consulta de rol", ex);
        }finally
        {
        	desconecta();
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal#asignaRolUsuario(java.lang.String, java.lang.String)
     */
    @Override
//    @RolesAllowed("USER_ADMIN_ROLE")
    public boolean asignaRolUsuario(  String uid,   String rol) throws AdmonUsuariosException {
    	conecta();
    	try {
			final Attributes matchAttrsU = new BasicAttributes(true);
			matchAttrsU.put(new BasicAttribute("uid", uid));
			final NamingEnumeration<SearchResult> answerU =	ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrsU);
			if(answerU.hasMoreElements())
			{
	            boolean success = false;
	              Attributes matchAttrs = new BasicAttributes(true);
	              Attributes matchAttrs2 = new BasicAttributes(true);
	              String dnUsuario = generaNombreUsuario(uid, props.getProperty(USUARIOS_DN_BASE));
	            matchAttrs.put(new BasicAttribute("cn", rol));
	            System.out.println(props.getProperty(ROLES_DN_BASE));
	            System.out.println(matchAttrs);
	              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
	              
	            if (answer.hasMoreElements()) {
	                  SearchResult result = answer.next();
	                  Attributes attrs = result.getAttributes();
	                Attribute members = attrs.get("uniqueMember");
	                if (members == null || !members.contains(dnUsuario)) {
	                    if (members == null) {
	                        members = new BasicAttribute("uniqueMember");
	                    }
	                    
	                    if(members.size()==1)
	                    {
	                        matchAttrs2.put(new BasicAttribute("description", rol));
	                        NamingEnumeration<SearchResult> answer2 = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs2);
	                        if (answer2.hasMoreElements()) {
	                            SearchResult result2 = answer2.next();
	                            Attributes attrs2 = result2.getAttributes();
	                            Attribute members2 = attrs2.get("uniqueMember");
	                            if(members2!=null && members2.size()>members.size())
	                            	members = members2;
	                        }

	                    }
	                    members.add(dnUsuario);
	                    ModificationItem[] mods = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, members) };
	                    ctx.modifyAttributes(result.getNameInNamespace(), mods);
	                    success = true;
	                }
	            } else {
	                throw new AdmonUsuariosException("El rol \"" + rol + "\" no existe.");
	            }
	            return success;
			}
			return false;
        } catch (Exception ex) {
            log.error("Error al realizar asignación de rol", ex);
            throw new AdmonUsuariosException("Error al realizar asignación de rol", ex);
        }
    	finally
    	{
    		desconecta();
    	}
    }

    public boolean asignaAreayGrupoUsuario(  String uid,   String areaygrupo) throws AdmonUsuariosException {
    	conecta();
    	try {
//    		areaygrupo = "USUARIO_EXTERNO";
			final Attributes matchAttrsU = new BasicAttributes(true);
			matchAttrsU.put(new BasicAttribute("uid", uid));
			final NamingEnumeration<SearchResult> answerU =	ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrsU);
			if(answerU.hasMoreElements())
			{
	        	boolean success = false;
	              Attributes matchAttrs = new BasicAttributes(true);
	              Attributes matchAttrs2 = new BasicAttributes(true);
	              String dnUsuario = generaNombreUsuario(uid, props.getProperty(USUARIOS_DN_BASE));        	
	            matchAttrs.put(new BasicAttribute("cn", areaygrupo));            
	              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);            
	            if (answer.hasMoreElements()) 
	            {
	            	String[] attrIDs = {"uniqueMember"};
	            	SearchControls ctls = new SearchControls();
	            	ctls.setReturningAttributes(attrIDs);

	                  SearchResult result = answer.next();
	                  Attributes attrs = result.getAttributes();
	                Attribute members = attrs.get("uniqueMember");
	                Attribute members3 =  new BasicAttribute("uniqueMember");
	                
	                if (members == null || !members.contains(dnUsuario)) {
	                    if (members == null) {
	                        members = new BasicAttribute("uniqueMember");
	                    }

	                    if(members.size()==1)
	                    {
	                        matchAttrs2.put(new BasicAttribute("description", areaygrupo));
	                        NamingEnumeration<SearchResult> answer2 = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs2);
	                        if (answer2.hasMoreElements()) {
	                            SearchResult result2 = answer2.next();
	                            Attributes attrs2 = result2.getAttributes();
	                            Attribute members2 = attrs2.get("uniqueMember");
	                            if(members2!=null && members2.size()>members.size())
	                            	members = members2;
	                        }

	                    }
	                    
	                    members3.add(dnUsuario);
	                      ModificationItem[] mods = new ModificationItem[] {new ModificationItem(DirContext.ADD_ATTRIBUTE, members3)};
	                    ctx.modifyAttributes(result.getNameInNamespace(), mods);
	                    success = true;
	                }
	            } else {
	                throw new AdmonUsuariosException("El areaygrupo \"" + areaygrupo + "\" no existe.");
	            }
	            return success;
			}
			return false;
        } catch (Exception ex) {
            log.error("Error al realizar asignación de rol", ex);
            throw new AdmonUsuariosException("Error al realizar asignación de rol", ex);
        }finally
        {
        	desconecta();
        }
    }
    
    public static void main(String[] args) {
    	try {
    		AdmonRolesSession s = new AdmonRolesSession();
    		s.init();
//    		s.asignaRolUsuario("CAHJ810807HDFSRR03", "VENTANILLA");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    
    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal#revocaRolUsuario(java.lang.String, java.lang.String)
     */
    @Override
//    @RolesAllowed("USER_ADMIN_ROLE")
    public boolean revocaRolUsuario(  String uid,   String rol) throws AdmonUsuariosException {
        conecta();
    	try {
			final Attributes matchAttrsU = new BasicAttributes(true);
			matchAttrsU.put(new BasicAttribute("uid", uid));
			final NamingEnumeration<SearchResult> answerU =	ctx.search(props.getProperty(USUARIOS_DN_BASE), matchAttrsU);
			if(answerU.hasMoreElements())
			{
				boolean success = false;
	              Attributes matchAttrs = new BasicAttributes(true);
	              Attributes matchAttrs2 = new BasicAttributes(true);
	              String dnUsuario = generaNombreUsuario(uid, props.getProperty(USUARIOS_DN_BASE));
	            matchAttrs.put(new BasicAttribute("cn", rol));
	              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
	            if (answer.hasMoreElements()) {
	                  SearchResult result = answer.next();
	                  Attributes attrs = result.getAttributes();
	                  Attribute members = attrs.get("uniqueMember");
	                if (members != null && members.contains(dnUsuario)) {
	                    members.remove(dnUsuario);
	                      ModificationItem[] mods = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, members)};
	                    ctx.modifyAttributes(result.getNameInNamespace(), mods);
	                    success = true;
	                }
	                else
	                {
	                    if(members!=null && members.size()==1)
	                    {
	                        matchAttrs2.put(new BasicAttribute("description", rol));
	                        NamingEnumeration<SearchResult> answer2 = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs2);
	                        if (answer2.hasMoreElements()) {
	                            SearchResult result2 = answer2.next();
	                            Attributes attrs2 = result2.getAttributes();
	                            Attribute members2 = attrs2.get("uniqueMember");
	                            if (members2 != null && members2.contains(dnUsuario)) {
	                                members2.remove(dnUsuario);
	                                ModificationItem[] mods2 = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, members2)};
	                                ctx.modifyAttributes(result2.getNameInNamespace(), mods2);
	                                success = true;
	                            }
	                        }

	                    }

	                }
	            } else {
	                throw new AdmonUsuariosException("El rol \"" + rol + "\" no existe.");
	            }
	            return success;
			}
			return false;
        } catch (Exception ex) {
            log.error("Error al realizar revocación de rol", ex);
            throw new AdmonUsuariosException("Error al realizar revocación de rol", ex);
        }finally
        {
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionRemote#obtenRoles()
     */
    @Override
//    @RolesAllowed("USER_ADMIN_ROLE")
    public List<PuestoDTO> obtenRoles() throws AdmonUsuariosException {
    	conecta();
    	try {
            List<PuestoDTO> roles = new ArrayList<PuestoDTO>();
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("objectClass", "groupofuniquenames"));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
            SearchResult rol;
            Attributes attrs;
            while (answer.hasMoreElements()) {
                rol = answer.nextElement();
                attrs = rol.getAttributes();
               /* roles.add(new PuestoDTO(attrs.get("cn").get().toString(), attrs.get("description") != null ? attrs.get(
                        "description").get().toString() : ""));  */
            }
            return roles;
        } catch (Exception ex) {
            log.error("Error al realizar consulta de roles", ex);
            throw new AdmonUsuariosException("Error al realizar consulta de roles", ex);
        }finally
        {
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionRemote#obtenRoles(java.lang.String[])
     */
    @Override
//    @RolesAllowed("USER_ADMIN_ROLE")
    public List<PuestoDTO> obtenRoles(  String[] nombres) throws AdmonUsuariosException {
        conecta();
    	try {
              List<PuestoDTO> roles = new ArrayList<PuestoDTO>();
            if (nombres.length > 0) {
                  String filter = construyeFiltroRoles(nombres.length);
                  Object[] params = new Object[nombres.length];
                for (int i = 0; i < params.length; i++) {
                    params[i] = nombres[i];
                }
                  NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), filter, params, null);
                SearchResult rol;
                Attributes attrs;
                while (answer.hasMoreElements()) {
                    rol = answer.nextElement();
                    attrs = rol.getAttributes();
                 /*   roles.add(new PuestoDTO(attrs.get("cn").get().toString(), 
                    		            attrs.get("description") != null ? 
                    		            attrs.get("description").get().toString() : ""));  */
                }
            }
            return roles;
        } catch (Exception ex) {
            log.error("Error al realizar consulta de roles", ex);
            throw new AdmonUsuariosException("Error al realizar consulta de roles", ex);
        }finally{
        	desconecta();
        }
    }

    @Override
    public List<PuestoDTO> obtenRolesUsuario(  String uid) throws AdmonUsuariosException {
    	conecta();
    	try {
              List<PuestoDTO> roles = new ArrayList<PuestoDTO>();
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("objectClass", "groupofuniquenames"));
            matchAttrs.put(new BasicAttribute("uniqueMember", generaNombreUsuario(uid,props.getProperty(USUARIOS_DN_BASE))));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
            SearchResult rol;
            Attributes attrs;
            while (answer.hasMoreElements()) {
                rol = answer.nextElement();
                attrs = rol.getAttributes();
               /* roles.add(new RolDTO(attrs.get("cn").get().toString(), 
                		            attrs.get("description") != null ? 
                		            attrs.get("description").get().toString() : ""));  */
            }
            return roles;
        } catch (Exception ex) {
            log.error("Error al realizar consulta de roles por usuario", ex);
            throw new AdmonUsuariosException("Error al realizar consulta de roles por usuario", ex);
        }finally{
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionRemote#asignaRolesUsuario(java.lang.String, java.lang.String[])
     */
    @Override
    public boolean[] asignaRolesUsuario(  String uid,   String[] roles) throws AdmonUsuariosException {
          boolean[] success = new boolean[roles.length];
        int i = 0;
        for (String rol : roles) {
            success[i++] = asignaRolUsuario(uid, rol);
        }
        return success;
    }
    
    
    public boolean[] asignaAreayGrupoUsuario(  String uid,   List<String> areagrupo) throws AdmonUsuariosException {
    	System.out.println("Inicia metodo asignaAreayGrupoUsuario --------------------------->");
          boolean[] success = new boolean[areagrupo.size()];
        int i = 0;
        for (String ag : areagrupo) {
            success[i++] = asignaAreayGrupoUsuario(uid, ag);
        }
        System.out.println("termina metodo asignaAreayGrupoUsuario <---------------------------");
        return success;
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionRemote#revocaRolesUsuario(java.lang.String, java.lang.String[])
     */
    @Override
    public boolean[] revocaRolesUsuario(  String uid,   String[] roles) throws AdmonUsuariosException {
          boolean[] success = new boolean[roles.length];
        int i = 0;
        for (String rol : roles) {
            success[i++] = revocaRolUsuario(uid, rol);
        }
        return success;
    }

    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionRemote#buscarRoles(java.lang.String)
     */
    /*
    @Override
    public List<PuestoDTO> buscarRoles(String nombre) throws AdmonUsuariosException {
        try {
              List<PuestoDTO> encontrados = new ArrayList<PuestoDTO>();
              String filter = "(cn=*{0}*)";
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), filter, new Object[] { nombre }, null);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                encontrados.add(new PuestoDTO(attrs.get("cn").get().toString(), 
                		                  attrs.get("description") != null ? 
                		                  attrs.get("description").get().toString() : ""));
            }
            return encontrados;
        } catch (Exception ex) {
            log.error("Error al obtener roles", ex);
            throw new AdmonUsuariosException("Error al obtener roles", ex);
        }
    }   */

    /**
     * Construye el filtro para obtener los diferentes roles solicitados
     * @param nombres cantidad de nombres de roles
     * @return el filtro construido
     */
    private String construyeFiltroRoles(  int nombres) {
          StringBuilder builder = new StringBuilder("(|");
        for (int i = 0; i < nombres; i++) {
            builder.append("(cn={").append(i).append("})");
        }
        builder.append(')');
        return builder.toString();
    }

    /**
     * Método para generar el nombre distinguido de un rol
     * @param nombre del rol
     * @param baseDN donde se localizan los roles
     * @return nombre distinguido del rol
     */
    public static String generaNombreRol(String nombre, String baseDN) {
          StringBuilder builder = new StringBuilder("cn=");
        builder.append(nombre).append(',').append(baseDN);
        return builder.toString();
    }
    
    public boolean existeRol(  String nombre) throws AdmonUsuariosException {
    	boolean existe = false;
        conecta();
    	try {
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", nombre));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(ROLES_DN_BASE), matchAttrs);
            if (answer.hasMoreElements()) {
            	existe = true;
            }
        } catch (Exception ex) {
            log.error("Error al realizar consulta de rol", ex);
            throw new AdmonUsuariosException("Error al realizar consulta de rol", ex);
        }finally{
        	desconecta();
        }
    	
        System.out.println("existe el rol: "+existe);
        return existe;
    }

}
