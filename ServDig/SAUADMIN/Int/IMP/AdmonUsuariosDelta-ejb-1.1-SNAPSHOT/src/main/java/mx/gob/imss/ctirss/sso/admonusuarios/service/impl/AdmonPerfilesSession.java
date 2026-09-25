package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonRolesSessionLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;
import static mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonRolesSession.generaNombreRol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Session Bean implementation class AdmonPerfilesSession
 */
@Stateless (name="admonPerfilesSession", mappedName="admonPerfilesSession")
public class AdmonPerfilesSession implements  AdmonPerfilesSessionLocal {
    /** Log de la clase */
    private static   Log log = LogFactory.getLog(AdmonPerfilesSession.class);
    /** Configuración de LDAP */
    private Properties props = new Properties();
    /** Contexto de directorio */
    private DirContext ctx;
    /** Propiedad del DN Base de roles */
    private static   String ROLES_DN_BASE = "base.dn.roles";
    /** Cadena de propiedad de dn base de usuarios */
    private static   String PERFILES_DN_BASE = "base.dn.perfiles";
    /** Cadena de propiedad de dn base de curps */
    private static   String PEOPLE_DN_BASE = "base.dn.usuarios";
    /** Cadena de propiedad de dn base de grupos */
    private static   String GROUPS_PERFILES_DN_BASE = "base.dn.groups";
    /** Administración de roles */
    @EJB
    private AdmonRolesSessionLocal admonRoles;
    /** Administración de usuarios */
    @EJB
    private AdmonUsuariosSessionLocal admonUsuarios;

    /**
     * Default constructor.
     */
    public AdmonPerfilesSession() {
    }

    /**
     * Método para inicializar el contexto del directorio
     */
//    @PostConstruct
    public void init() {
        try {
            props.load(AdmonUsuariosSessionLocal.class.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
            ctx = new InitialDirContext(props);
        } catch (Exception ex) {
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
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal
     * #asignarRolAPerfil(java.lang.String, java.lang.String)
     */
    @Override
    public boolean asignarRolAPerfil(  String perfil,   String rol)
            throws AdmonUsuariosException {
    	conecta();
        try {
            boolean success = false;
              Attributes matchAttrs = new BasicAttributes(true);
              String nombreRol = generaNombreRol(rol,props.getProperty(ROLES_DN_BASE));
            matchAttrs.put(new BasicAttribute("cn", perfil));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PERFILES_DN_BASE), matchAttrs);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                  Attribute uniqueMembers = attrs.get("uniqueMember") != null ? 
                		                        attrs.get("uniqueMember") : new BasicAttribute("uniqueMember");
                if (!uniqueMembers.contains(nombreRol)) {
                    uniqueMembers.add(nombreRol);
                    ctx.modifyAttributes(result.getNameInNamespace(), new ModificationItem[] { new ModificationItem(
                                    	DirContext.REPLACE_ATTRIBUTE, uniqueMembers) });
                    //  List<UsuarioDTO> usuarios = admonUsuarios.obtenUsuariosPorPerfil(perfilTO);
                   /* for (UsuarioDTO usuario : usuarios) {
                        admonRoles.asignaRolUsuario(usuario.getUid(), rol);
                    }  */
                    success = true;
                }
            } else {
                throw new AdmonUsuariosException("El perfil no existe.");
            }
            return success;
        } catch (Exception ex) {
            log.error("Error al asignar rol a perfil.", ex);
            throw new AdmonUsuariosException("Error al asignar rol a perfil.",
                    ex);
        }finally{
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal
     * #eliminarRolDePerfil(java.lang.String, java.lang.String)
     */
    @Override
    public boolean eliminarRolDePerfil(String perfil, String rol)
            throws AdmonUsuariosException {
    	conecta();
        try {
            boolean success = false;
              Attributes matchAttrs = new BasicAttributes(true);
              String nombreRol = generaNombreRol(rol,
                    props.getProperty(ROLES_DN_BASE));
             // PerfilTO perfilTO = obtenPerfil(perfil);
            matchAttrs.put(new BasicAttribute("cn", perfil));
              NamingEnumeration<SearchResult> answer = ctx.search(
                    props.getProperty(PERFILES_DN_BASE), matchAttrs);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                  Attribute uniqueMembers = attrs.get("uniqueMember") != null ? attrs.get("uniqueMember") : new BasicAttribute("uniqueMember");
                if (uniqueMembers.contains(nombreRol)) {
                    uniqueMembers.remove(nombreRol);
                    ctx.modifyAttributes(result.getNameInNamespace(),new ModificationItem[] { 
                    	                                             new ModificationItem(DirContext.REPLACE_ATTRIBUTE, uniqueMembers) });
                     /* List<UsuarioDTO> usuarios = admonUsuarios.obtenUsuariosPorPerfil(perfilTO);
                    for (UsuarioDTO usuario : usuarios) {
                        admonRoles.revocaRolUsuario(usuario.getUid(), rol);
                    }  */
                    success = true;
                }
            } else {
                throw new AdmonUsuariosException("El perfil no existe.");
            }
            return success;
        } catch (Exception ex) {
            log.error("Error al eliminar rol del perfil.", ex);
            throw new AdmonUsuariosException("Error al eliminar rol del perfil.", ex);
        }finally{
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal
     * #existePerfil(java.lang.String)
     */
    @Override
//    @RolesAllowed("ADMINISTRADOR_USUARIOS")
    public boolean existePerfil(String nombre) throws AdmonUsuariosException {
    	conecta();
        try {
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", nombre));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PERFILES_DN_BASE), matchAttrs);
            return answer.hasMoreElements();
        } catch (Exception ex) {
            log.error("Error al consultar perfil.", ex);
            throw new AdmonUsuariosException("Error al consultar perfil.", ex);
        }finally{
        	desconecta();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #asignaPerfilAUsuario(java.lang.String, java.lang.String)
     */
    @Override 
    public boolean asignaPerfilAUsuario(String uid, String perfil) throws AdmonUsuariosException {
			try {
				/*
			    boolean success = true;
			      PerfilTO perfilTO = obtenPerfil(perfil);
			      UsuarioDTO usuario = admonUsuarios.obtenUsuario(uid);
			    if (usuario.getPerfil() != null && !usuario.getPerfil().getNombre().isEmpty()) {
			        throw new AdmonUsuariosException("El usuario ya cuenta con un perfil.");
			    } else if (perfilTO != null) {
			        admonUsuarios.actualizaPerfilUsuario(uid, perfil);
			          boolean asignacion[] = admonRoles.asignaRolesUsuario(uid, obtenNombresDeRoles(perfilTO.getRoles()));
			        for (boolean asign : asignacion) {
			            success = success && asign;
			        }
			    } else {
			        throw new AdmonUsuariosException("El perfil no existe.");
			    }
			    return success; */
			} catch (Exception ex) {
			    log.error("Error al asignar perfil a usuario.", ex);
			    throw new AdmonUsuariosException(
			            "Error al asignar perfil a usuario.", ex);
			}  
			return true;
    }
    
    public boolean asignaPerfilAUsuarioExterno (  String uid,   String perfil) throws AdmonUsuariosException {
 		try {
 		    boolean success = true;
 		   /*
 		      PuestoDTO perfilTO = obtenPerfil(perfil);
 		      UsuarioDTO usuario = admonUsuarios.obtenUsuario(uid);
 		    System.out.println("rolDTO " + perfilTO.getNombre() + " UsuarioDTO " + usuario.getCurp());
 		    if (usuario.getPerfil() != null && !usuario.getPerfil().getNombre().isEmpty()) {
 		        throw new AdmonUsuariosException("El usuario ya cuenta con un perfil.");
 		    } else if (perfilTO != null) {
 		        admonUsuarios.actualizaPerfilUsuario(uid, perfil); 		        
 		        if(perfilTO.getRoles().size() > 0){
 		        	  boolean asignacion[] = admonRoles.asignaRolesUsuario(uid, obtenNombresDeRoles(perfilTO.getRoles()));
 	 		        for (boolean asign : asignacion) {
 	 		            success = success && asign;
 	 		        }
 		        }
 		    } else {
 		        throw new AdmonUsuariosException("El perfil no existe.");
 		    }
 		    return success;  */
 		} catch (Exception ex) {
 		    log.error("Error al asignar perfil a usuario.", ex);
 		    throw new AdmonUsuariosException(
 		            "Error al asignar perfil a usuario.", ex);
 		}
 		return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #cambiaPerfilAUsuario(java.lang.String, java.lang.String)
     */
    @Override
    public boolean cambiaPerfilAUsuario(  String uid,   String perfil) throws AdmonUsuariosException {
        try {
            boolean success = revocaPerfilAUsuario(uid);
            if (success) {            	
                success = asignaPerfilAUsuario(uid, perfil);
            }
            return success;
        } catch (Exception ex) {
            log.error("Error al realizar cambio de perfil.", ex);
            throw new AdmonUsuariosException("Error al realizar cambio de perfil.", ex);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #revocaPerfilAUsuario(java.lang.String)
     */
    @Override
    public boolean revocaPerfilAUsuario(  String uid) throws AdmonUsuariosException {
        try {
            boolean success = true;
          /*   UsuarioDTO usuario = admonUsuarios.obtenUsuario(uid);
            String perfil = usuario.getPerfil().getDescripcion();       
            if (usuario.getPerfil()!=null) {         
                admonUsuarios.actualizaPerfilUsuario(uid, perfil);              
	              boolean asignacion[] = admonRoles.revocaRolesUsuario(uid, obtenNombresDeRoles(usuario.getPerfil().getRoles()));
	            for (boolean asign : asignacion) {
	                    success = success && asign;
	            }
            }   
            else {
                throw new AdmonUsuariosException("El perfil no existe.");
            }   */
            return success;
        } catch (Exception ex) {
            log.error("Error al asignar perfil a usuario.", ex);
            throw new AdmonUsuariosException(
                    "Error al asignar perfil a usuario.", ex);
        }
    }

    
 /*   @Override
    public boolean creaPerfil(PuestoDTO perfil)
            throws AdmonUsuariosException {
        try {
            boolean success = false;
            if (!existePerfil(perfil.getNombre())) {
                  Attributes perfilAttrs = new BasicAttributes();
                  Attribute objectClasses = new BasicAttribute("objectClass");
                objectClasses.add("top");
                objectClasses.add("groupOfUniqueNames");
                perfilAttrs.put(objectClasses);
                perfilAttrs.put(new BasicAttribute("cn", perfil.getNombrePuesto()));
                if (perfil.getNombrePuesto() != null && !perfil.getNombrePuesto().isEmpty()) {
                    perfilAttrs.put(new BasicAttribute("description", perfil.getDescripcion()));
                }
                if (perfil.getRoles() != null && !perfil.getRoles().isEmpty()) {
                      Attribute uniqueMembers = new BasicAttribute("uniqueMember");
                    for (PuestoDTO rol : perfil.getRoles()) {
                        uniqueMembers.add(generaNombreRol(rol.getNombrePuesto(), props.getProperty(ROLES_DN_BASE)));
                    }
                    perfilAttrs.put(uniqueMembers);
                }
                ctx.createSubcontext(generaNombrePerfil(perfil.getNombre(),
                                	 props.getProperty(PERFILES_DN_BASE)),perfilAttrs);
                success = true;
            }
            return success;
        } catch (Exception ex) {
            log.error("Error al crear perfil.", ex);
            throw new AdmonUsuariosException("Error al crear perfil.", ex);
        }
    }   */


    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #obtenPerfiles()
     */
    /*
    @Override
    public List<PerfilTO> obtenPerfiles() throws AdmonUsuariosException {
        try {
              List<PerfilTO> perfiles = new ArrayList<PerfilTO>();
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("objectClass", "groupOfUniqueNames"));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PERFILES_DN_BASE), matchAttrs);
            SearchResult result;
            Attributes attrs;
            String[] roles;
            while (answer.hasMoreElements()) {
                   result = answer.next();
                   attrs = result.getAttributes();
                   roles = obtenRoles(attrs.get("uniqueMember"));
                   perfiles.add(new PerfilTO(attrs.get("cn").get().toString(), 
                						     attrs.get("description") != null ? attrs.get("description").get().toString() : "",
                						     admonRoles.obtenRoles(roles)));               
            }
            return perfiles;
        } catch (Exception ex) {
            log.error("Error al consultar perfiles.", ex);
            throw new AdmonUsuariosException("Error al consultar perfiles.", ex);
        }
    }  */
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #obtenPerfil(java.lang.String)
     */
   /* @Override
    public PerfilTO obtenPerfil(  String nombre) throws AdmonUsuariosException {
        try {
            PerfilTO perfil = null;
              Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("cn", nombre));
            matchAttrs.put(new BasicAttribute("objectClass","groupOfUniqueNames"));
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PERFILES_DN_BASE), matchAttrs);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                  String[] roles = obtenRoles(attrs.get("uniqueMember"));
                perfil = new PerfilTO(attrs.get("cn").get().toString(), 
                		              attrs.get("description") != null ? attrs.get("description").get().toString() : "", 
                		              admonRoles.obtenRoles(roles));
            }
            return perfil;
        } catch (Exception ex) {
            log.error("Error al obtener perfil", ex);
            throw new AdmonUsuariosException("Error al obtener perfil", ex);
        }
    }  */

    /**
     * Obtiene el nombre de los roles en la lista
     * 
     * @param roles
     *            lista con los roles
     * @return nombres de los roles en la lista
     */
    private String[] obtenNombresDeRoles(List<PuestoDTO> roles) {
          String[] nombres = new String[roles.size()];
        int i = 0;
        for (PuestoDTO rol : roles){
            nombres[i++] = rol.getNombrePuesto();
        }
        return nombres;
    }

    /**
     * Obtiene los roles contenidos en un perfil
     * 
     * @param members
     *            atributo de uniqueMembers
     * @return arreglo con el nombre de los roles
     * @throws NamingException
     *             solo deja pasar la excepción
     */
    private String[] obtenRoles(  Attribute members) throws NamingException {
        String[] roles = new String[] {};
        String rol;
        if (members != null) {
            roles = new String[members.size()];
            for (int i = 0; i < members.size(); i++) {
                rol = members.get(i).toString();
                roles[i] = rol.substring(3, rol.indexOf(','));
            }
        }
        return roles;
    }

    /**
     * Método para generar el nombre distinguido de un rol
     * 
     * @param nombre
     *            del rol
     * @param baseDN
     *            donde se localizan los roles
     * @return nombre distinguido del rol
     */
    public static String generaNombrePerfil(String nombre, String baseDN) {
          StringBuilder builder = new StringBuilder("cn=");
        builder.append(nombre).append(',').append(baseDN);
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionRemote
     * #buscarPerfil(java.lang.String)
     */
   /* @Override
    public List<PerfilTO> buscarPerfil(  String nombre) throws AdmonUsuariosException {
        try {
              List<PerfilTO> encontrados = new ArrayList<PerfilTO>();
              String filter = "((cn={*0*}))";
              NamingEnumeration<SearchResult> answer = ctx.search(
                    props.getProperty(PERFILES_DN_BASE), filter,
                    new Object[] { nombre }, null);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                  String[] roles = obtenRoles(attrs.get("uniqueMember"));
                encontrados.add(new PerfilTO(attrs.get("cn").get().toString(),
                        					 attrs.get("description") != null ? 
                        					 attrs.get("description").get().toString() : "",
                                             admonRoles.obtenRoles(roles)));
            }
            return encontrados;
        } catch (Exception ex) {
            log.error("Error al obtener perfil", ex);
            throw new AdmonUsuariosException("Error al obtener perfil", ex);
        }
    }  */

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonPerfilesSessionLocal
     * #buscarPerfilesPorRol(java.lang.String)
     */
    /*
    @Override
    public List<PerfilTO> buscarPerfilesPorRol(  String rol) throws AdmonUsuariosException {
        try {
              List<PerfilTO> encontrados = new ArrayList<PerfilTO>();
              String filter = "((uniqueMember={*0*}))";
              NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PERFILES_DN_BASE), filter,
                    												  new Object[] { generaNombreRol(rol, ROLES_DN_BASE) }, null);
            if (answer.hasMoreElements()) {
                  SearchResult result = answer.next();
                  Attributes attrs = result.getAttributes();
                  String[] roles = obtenRoles(attrs.get("uniqueMember"));
                  encontrados.add(new PerfilTO(attrs.get("cn").get().toString(),
                                             attrs.get("description") != null ? 
                                             attrs.get("description").get().toString() : "",
                                             admonRoles.obtenRoles(roles)));  
            }
            return encontrados;
        } catch (Exception ex) {
            log.error("Error al obtener perfil", ex);
            throw new AdmonUsuariosException("Error al obtener perfil", ex);
          }
       }  */

     	/*JLBC perfiles Metodo que concatena los perfiles para guardar en el LDAP*/
		public List<PuestoDTO> getLstPerfiles(String perfiles) throws AdmonUsuariosException{
			List<PuestoDTO> lstPerfiles = new ArrayList<PuestoDTO>();
			String[] arrayPerfiles = perfiles.split(",\\s*");
			PuestoDTO p = null;
			try{
				for(int i= 0;  i < arrayPerfiles.length; i++){
					   p = new PuestoDTO();
					  // p = obtenPerfil(arrayPerfiles[i]);
					   lstPerfiles.add(p);
				   }
			}catch (Exception ex) {
				log.error("Error al crear lista de perfiles ", ex);
				throw new AdmonUsuariosException("Error al crear lista de perfiles ", ex);
			}
			   
			return lstPerfiles;
		}
		
		public UsuarioDTO consultaUsuarioLDAP(String curp) throws AdmonUsuariosException
	    {
			conecta();
	    	UsuarioDTO usr = new UsuarioDTO();
	    	try{
	    	Attributes matchAttrs = new BasicAttributes(true);
	        matchAttrs.put(new BasicAttribute("employeeNumber", curp));
	        NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(PEOPLE_DN_BASE), matchAttrs);
	        SearchResult result;
	        Attributes attrs;
	        usr.setCurp("");
	        while (answer.hasMoreElements()) {
	        	String val = "";
	        	result = answer.next();
	            attrs = result.getAttributes();
	            val = attrs.get("employeeNumber").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setCurp(val);
	            val = attrs.get("cn").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setNombres(val);
	            val = attrs.get("sn").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setApellidoPaterno(val);
	            val = attrs.get("givenName").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setApellidoMaterno(val);
	            val = attrs.get("mail").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setCorreoElectronico(val);
	            
	            val = attrs.get("businessCategory").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setClaveDelegacion(Integer.parseInt(val));
	            
	            val = attrs.get("departmentNumber").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setClaveSubDelegacion(Integer.parseInt(val));
	            
	            val = attrs.get("destinationIndicator").toString();
	            val = val.substring(val.indexOf(":")+2, val.length());
	            usr.setClaveUMF(Integer.parseInt(val));   
	           }
	    	}
	    	catch(Exception e){
	    		System.out.println("Existio una excepcion ");
	    		e.printStackTrace();
	    	}finally{
	    		desconecta();
	    	}
	        return usr;    
	    }
		
		public List<String> consultaRolesPerfilesLDAP(String curp) throws AdmonUsuariosException
	    {
			conecta();
	    	UsuarioDTO usuario =  new UsuarioDTO();
	    	List<String> listaPerfiles = new ArrayList<String>();
	    	try{
	    	Attributes matchAttrs = new BasicAttributes(true);
	        matchAttrs.put(new BasicAttribute("uniqueMember", "uid="+curp+",ou=people,dc=imss,dc=gob,dc=mx"));
	        NamingEnumeration<SearchResult> answer = ctx.search(props.getProperty(GROUPS_PERFILES_DN_BASE), matchAttrs);
	        SearchResult result;
	        Attributes attrs;
	        while (answer.hasMoreElements()) {
	        	result = answer.next();
	            attrs = result.getAttributes();
	            listaPerfiles.add(new String(attrs.get("cn")+""));
	           }
	    	}
	    	catch(Exception e){
	    		System.out.println("consultaRolesPerfilesLDAP");
	    		e.printStackTrace();
	    	}finally{
	    		desconecta();
	    	}
	    	return listaPerfiles;
	    }

		 public boolean asignaAreayGrupoAUsuario(final String uid, final List<String> areaygrupo) throws AdmonUsuariosException {
			 System.out.println("Iniciao registro en ldao de areas y grupos ------------------------------>>>");
				try {
				    boolean success = true;
				    final UsuarioDTO usuario = admonUsuarios.obtenUsuario(uid);			
				    final boolean asignacion[] = admonRoles.asignaAreayGrupoUsuario(uid, areaygrupo);
				        for (boolean asign : asignacion) {
				            success = success && asign;
				        
				    } 
				    System.out.println("Termina registro en ldao de areas y grupos <<<------------------------------");
				    return success;
				} catch (Exception ex) {
				    System.out.println("asignaAreayGrupoAUsuario:: Error al asignar area o grupo a usuario." +ex);
				    throw new AdmonUsuariosException(
				            "Error al asignar area o grupo a usuario.", ex);
				}
		} 
}
