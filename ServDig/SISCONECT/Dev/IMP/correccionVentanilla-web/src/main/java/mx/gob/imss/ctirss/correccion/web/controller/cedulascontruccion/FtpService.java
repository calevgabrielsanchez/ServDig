package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;

import org.springframework.core.io.ClassPathResource;

//Prueba commint revision 813

public class FtpService {

    private static final Logger logger = Logger.getLogger(FtpService.class);

    
    public boolean subirArchivo(InputStream archivoAnexo,String nombrearchivo){
    	
    	FTPSClient cliente = new FTPSClient();
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        cliente.configure(conf);
        
        String host = null;
        String user = null;
        String pass = null;
        int ambiente=determinaAmbiente();
        switch(ambiente){
        	//Desarrollo
        	case 1:
	        	host=getPropiedadDeProperties("host.ftp.desarrollo");	
	        	user=getPropiedadDeProperties("user.desarrollo");
	        	pass=getPropiedadDeProperties("password.desarrollo");
        	break;
        	//QA
        	case 2:
        		host=getPropiedadDeProperties("host.ftp.qa");	
	        	user=getPropiedadDeProperties("user.qa");
	        	pass=getPropiedadDeProperties("password.qa");
	        break;
        	case 3:
        		host=getPropiedadDeProperties("host.ftp.produccion");	
	        	user=getPropiedadDeProperties("user.produccion");
	        	pass=getPropiedadDeProperties("password.produccion");
	        break;
        	case 4:
        		host=getPropiedadDeProperties("host.ftp.stage");	
	        	user=getPropiedadDeProperties("user.stage");
	        	pass=getPropiedadDeProperties("password.stage");
	        break;
        }
        logger.info("Host FTP "+host);
        
        boolean flagEstado=false;
//        try {
//            logger.info("Conectando con servidor FTP");           
//            logger.info("Conectando "+host);
//            cliente.connect(host);
//            flagEstado = cliente.login(user,pass);
//            logger.info("Estaod de la Autenticacion :"+flagEstado);
//            boolean estadoSubida;
//            //Subimos el archivo                
//            if(flagEstado){
//            	logger.info("Autenticando Subiendo archvo "+nombrearchivo);
//            	estadoSubida=cliente.storeFile(nombrearchivo, archivoAnexo);
//                logger.info("Archivo cargado "+estadoSubida);
//            }
//            
//            cliente.logout();
//            cliente.disconnect();
//            logger.info("Desconectando");
//        } catch (IOException e) {
//            flagEstado=false;
//            e.printStackTrace();                
//        }        
        
        flagEstado=this.putFile(host, 21, user, pass, archivoAnexo, nombrearchivo);
        return flagEstado;
    }

		/**
		 * Metodo que determina la ip Del servidor host para determinar hacia que FTP debe mandar los archivos
		 * 
		 * 1 -Desarrollo
		 * 2 -QA
		 * 3 -Produccion
		 * 4 -Stage
		 * */

    
    
    public int determinaAmbiente(){
    	logger.info("Recuperando tipo de ambiente ");    	

    	int ambiente=0;
    	try {
    		InetAddress IP=InetAddress.getLocalHost();
    		String ipServidor=IP.getHostAddress();
    		logger.info("IpServidor: "+ipServidor);
    		if(ipServidor.equals(getPropiedadDeProperties("host.desarrollo"))){
    			logger.info("Ambiente Desarrollo");
    			return 1;
    		}else if(ipServidor.equals(getPropiedadDeProperties("host.qa"))){
    			logger.info("Ambiente QA");
    			return 2;
    		}else if(ipServidor.equals(getPropiedadDeProperties("host.produccion")) || ipServidor.equals(getPropiedadDeProperties("host.produccionN2"))){
    			logger.info("Ambiente Produccion");
    			return 3;
    		}else if(ipServidor.equals(getPropiedadDeProperties("host.stage")) || ipServidor.equals(getPropiedadDeProperties("host.stageN2"))){
    			logger.info("Ambiente Stage");
    			return 4;
    		}
		
    	} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	logger.info("El ambiente es "+ambiente);
    	return ambiente;
    	
    }
    
    
    
    
	  private String getPropiedadDeProperties(String propiedad) {
          String propertie = null;
          Properties properties = new Properties();         
          try {
              InputStream is = new ClassPathResource("aplicacion.properties").getInputStream();
              properties.load(is);
               is.close();
               propertie = properties.getProperty(propiedad);
          } catch (IOException e) {
                  e.printStackTrace();
                  return "";
          } catch (Exception e) {
                  e.printStackTrace();
                  return "";
          }
          return propertie;
	  }
	  
	  
	  
	  
		public boolean putFile(String host, int port, String username,
				String password, InputStream stream, String remoteFilename) {
			boolean estadoCarga=false;
			try {				
				FTPSClient ftpClient = new FTPSClient(false);
				ftpClient.connect(host, port);
				int reply = ftpClient.getReplyCode();
				if (FTPReply.isPositiveCompletion(reply)) {
					if (ftpClient.login(username, password)) {
						// Set protection buffer size
						ftpClient.execPBSZ(0);
						// Set data channel protection to private
						ftpClient.execPROT("P");
						// Modo Passivo
						ftpClient.enterLocalPassiveMode();
						logger.info("Stream "+stream);
						estadoCarga=ftpClient.storeFile(remoteFilename, stream);
						logger.info("Carga de archivo "+estadoCarga);
						if (estadoCarga) {
							stream.close();
						} else {
							logger.info("No se puede cargar el archivo "+remoteFilename);
						}
						ftpClient.logout();
					} else {
						logger.info("Fallo en autenticacion FTP");
					}
					ftpClient.disconnect();
				} else {
					logger.info("Fallo al conectar al FTP "+host);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();				
			}			
			return estadoCarga;
		}
	  
}
