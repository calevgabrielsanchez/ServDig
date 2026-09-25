package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.forgerock.opendj.ldap.Connection;
import org.forgerock.opendj.ldap.LDAPConnectionFactory;
import org.forgerock.opendj.ldap.LDAPOptions;
import org.forgerock.opendj.ldap.LdapException;
import org.forgerock.opendj.ldif.LDIFEntryWriter;

public class ServiceLdap {

	private static final Log logger = LogFactory.getLog(ServiceLdap.class);

	private LDAPConnectionFactory factory = null;
	protected Connection connection = null;
	final LDIFEntryWriter writer = new LDIFEntryWriter(System.out);
	private Properties props = new Properties();

	private String user;
	private char[] password;
	private String host;
	private int port;

	// final String user = "cn=Directory Manager";
	// final char[] password = "dirmanadmin".toCharArray();

	// private String host = "172.16.5.187";
	// private int port = 1389;

	// private String host = "saudigital-stage.imss.gob.mx";
	// private int port = 80;

	// private String host = "172.16.5.170";
	// private int port = 1389;

	protected String baseDN = "ou=people,dc=imss,dc=gob,dc=mx";
	protected String baseDNRoles = "ou=groups,dc=imss,dc=gob,dc=mx";

	public void init() {
		try {

			if (host == null) {
				props.load(AdmonUsuarios.class
						.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
				logger.debug("::::: Inicializando Objeto ServiceLDAP desde properties :::::");
				this.user = props.getProperty("userSingleton");
				this.password = props.getProperty("passSingleton").toCharArray();
				this.host = props.getProperty("hostSingleton");
				this.port = Integer.parseInt(props.getProperty("portSingleton"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void conectaLDAP() throws LdapException {

		if (factory == null) {
			logger.debug("::::: Iniciando Factory LDAP :::::");
			try {
				props.load(ServiceLdap.class
						.getResourceAsStream("/mx/gob/imss/ctirss/sso/admonusuarios/resources/ldap.properties"));
				logger.debug(props);
				user = props.getProperty("userSingleton");
				password = props.getProperty("passSingleton").toCharArray();
				host = props.getProperty("hostSingleton");
				port = Integer.parseInt(props.getProperty("portSingleton"));
				logger.debug("::::: Conectando a LDAP Balanceado PropertiesInit... " + host);

				logger.debug("::::: Init Factory...");
				LDAPOptions options = new LDAPOptions();
				logger.debug("::::: OptionsTime= " + options.getTimeout(TimeUnit.MILLISECONDS) + ", TransportDefect= "
						+ options.getTransportProvider());
				options.setTimeout(0L, TimeUnit.MILLISECONDS);
				options.setTransportProvider("Grizzly");
				logger.debug("::::: Seteando TIME OUT= " + options.getTimeout(TimeUnit.MILLISECONDS) + ", Transport= "
						+ options.getTransportProvider());
				this.factory = new LDAPConnectionFactory(this.host, this.port, options);
				logger.debug("::::: Finaliza Inti :::::");
			} catch (IOException ioe) {
				logger.error("::: Error al crear la conexion con LDAP... ", ioe);
			} catch (Exception ex) {
				logger.error("::: Error al crear contexto LDAP... ", ex);
			}

		}
		connection = factory.getConnection();
		connection.bind(user, password);
	}

	public void desconectaLDAP() {
		if (connection != null) {
			logger.debug("...Desconectado LDAP :::");
			connection.close();
			connection = null;
		}
	}

}
