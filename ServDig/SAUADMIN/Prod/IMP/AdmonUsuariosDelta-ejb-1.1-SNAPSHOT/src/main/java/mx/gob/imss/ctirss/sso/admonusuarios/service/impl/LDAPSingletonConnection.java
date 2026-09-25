package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.io.IOException;

import org.forgerock.opendj.ldap.Entries;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.LdapException;
import org.forgerock.opendj.ldap.TreeMapEntry;
import org.forgerock.opendj.ldap.requests.ModifyRequest;
import org.forgerock.opendj.ldap.responses.SearchResultEntry;


import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

public class LDAPSingletonConnection extends ServiceLdap {

	private static LDAPSingletonConnection ldapSingletonConnection;
	
	
	public static synchronized LDAPSingletonConnection getInstance() {
	    if(ldapSingletonConnection == null) {
	    	ldapSingletonConnection = new LDAPSingletonConnection();
	    }
	    return ldapSingletonConnection;
	}
	
	
	
	
	
	
	public synchronized boolean asignaAreayGrupoUsuario(String uid,String areaygrupo) throws AdmonUsuariosException {
		
		try {
			conectaLDAP();
			System.out.println("************Conectando AreaGrupo Singletooon **********");
			System.out.println("uid="+uid+","+baseDN);
			SearchResultEntry answerU = connection.readEntry("uid="+uid+","+baseDN);
			if(answerU!=null){
				 String dnUsuario = generaNombreUsuario(uid,baseDN); 
				 System.out.println("Busqueda Roles "+"cn="+areaygrupo+","+baseDNRoles+"     dnUsuario-> "+dnUsuario);
				 SearchResultEntry answer = connection.readEntry("cn="+areaygrupo+","+baseDNRoles);
				 
				 if(answer!=null){	
					 Entry old = TreeMapEntry.deepCopyOfEntry(answer);
					 org.forgerock.opendj.ldap.Attribute members=answer.getAttribute("uniqueMember");
					 System.out.println("UniqueMember ");
					 if(members!=null){
						 System.out.println("Atributos "+members.contains(dnUsuario)+"  "+dnUsuario);
					 }
					 if (members == null || !members.contains(dnUsuario)) {
						 System.out.println("Inicio Actualizacion AreaGrupo");
						 answer.addAttribute("uniqueMember", dnUsuario);
						 ModifyRequest request = Entries.diffEntries(old, answer);
						 connection.modify(request);
						 System.out.println("Fin Actualizacion");
					 }
				 }else {
					 	System.out.println("No existe Annswer");
		                throw new AdmonUsuariosException("El areaygrupo \"" + areaygrupo + "\" no existe.");
		          }
			}else{
				System.out.println("AnswerU igual null");
			}
			return false;
		} catch (LdapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			desconectaLDAP();
			System.out.println("*************Termina AreaGrupo Singleton *************");
		}
    	return false;	
		
		
	} 
	
	
	public static String generaNombreUsuario(final String uid,
			final String baseDN) {
		final StringBuilder name = new StringBuilder("uid=");
		name.append(uid).append(',').append(baseDN);
		return name.toString();
	}
	
}
