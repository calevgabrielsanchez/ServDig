package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.ejb.Stateless;
import javax.jws.WebService;

import org.forgerock.opendj.ldap.responses.SearchResultEntry;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.services.ConsultaUsuariosLdapLocal;

@WebService(serviceName="ConsultaUsuariosLdap")
@Stateless(name = "consultaUsuariosLdapServiceImpl", mappedName = "consultaUsuariosLdapServiceImpl")
public class ConsultaUsuariosLdapServiceImpl extends ServiceLdap implements ConsultaUsuariosLdapLocal {
	
	/** Estado activo */
	private static final String ACTIVO = "Active";

	@Override
	public UsuarioDTO consultaUsuariosLdap(final String uid) {
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
				usuario.setPassword(entry.parseAttribute("carLicense").asString());
				usuario.setDescripcionCargo(entry.parseAttribute("title").asString());
				usuario.setMatricula(entry.parseAttribute("imssmatricula").asString());
				String estatus = entry.parseAttribute("inetUserStatus").asString();
				usuario.setActivo(estatus.trim().equals(ACTIVO) ? true : false);
				return usuario;
			}
		} catch (Exception ex) {
			System.out.println("Error al realizar consulta de usuario "+ ex);
			ex.printStackTrace();
			return null;
		} finally {
			desconectaLDAP();
		}
		return null;
	}

}
