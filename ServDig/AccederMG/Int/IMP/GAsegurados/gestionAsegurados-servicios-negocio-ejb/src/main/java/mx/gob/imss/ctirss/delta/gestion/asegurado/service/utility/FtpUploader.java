package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUploader extends AbstractService {
	private static final String URL_APPLICATION_PROPERTIES = "/application.properties";

	private FTPClient ftpClient;

	private boolean connect() {
		boolean isConnected = false;
		ftpClient = new FTPClient();

		try {
			InputStream inputStream = FtpUploader.class.getClassLoader()
					.getResourceAsStream(URL_APPLICATION_PROPERTIES);

			Properties prop = new Properties();
			prop.load(inputStream);
			String server = prop.getProperty("ftp.server.url");
			String strPort = prop.getProperty("ftp.server.port");
			String user = prop.getProperty("ftp.server.user");
			String pass = prop.getProperty("ftp.server.passwd");

			int port = Integer.valueOf(strPort);
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			isConnected = true;
		} catch (SocketException e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return isConnected;
	}
	
	public boolean connect(String server, String strPort, String user, String pass) {
		boolean isConnected = false;
		ftpClient = new FTPClient();

		try {

			int port = Integer.valueOf(strPort);
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			isConnected = true;
		} catch (SocketException e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return isConnected;
	}
	
	private void disconnect() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean uploadFileContent(String urlRemoteFile, byte[] fileStream) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			try {
				log.info("Start uploading file: " + urlRemoteFile);
				OutputStream outputStream = ftpClient.storeFileStream(urlRemoteFile);
				outputStream.write(fileStream);
				outputStream.close();

				boolean completed = ftpClient.completePendingCommand();
				if (completed) {
					log.info("The file " + urlRemoteFile + " is uploaded successfully.");
					isUploaded = true;
				}
			} catch (FileNotFoundException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return isUploaded;
	}

	public boolean uploadFile(String urlLocalFile, String urlRemoteFile) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			File localFile = new File(urlLocalFile);

			try {
				InputStream inputStream = new FileInputStream(localFile);

				log.info("Start uploading file: " + urlRemoteFile);
				OutputStream outputStream = ftpClient.storeFileStream(urlRemoteFile);
				byte[] bytesIn = new byte[4096];
				int read = 0;

				while ((read = inputStream.read(bytesIn)) != -1) {
					outputStream.write(bytesIn, 0, read);
				}
				inputStream.close();
				outputStream.close();

				boolean completed = ftpClient.completePendingCommand();
				if (completed) {
					log.info("The file " + urlRemoteFile + " is uploaded successfully.");
					isUploaded = true;
				}

			} catch (FileNotFoundException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return isUploaded;
	}

	public boolean simpleUploadFile(String urlLocalFile, String urlRemoteFile) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			File localFile = new File(urlLocalFile);

			try {
				InputStream inputStream = new FileInputStream(localFile);

				log.info("Start uploading file: " + urlRemoteFile);
				boolean done = ftpClient.storeFile(urlRemoteFile, inputStream);
				inputStream.close();

				if (done) {
					log.info("The file " + urlRemoteFile + " is uploaded successfully.");
					isUploaded = true;
				}
			} catch (FileNotFoundException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return isUploaded;
	}
	
	public boolean removeFile(String urlRemoteFile) {
		boolean completed = false;
		boolean isConnected = connect();

		if (isConnected) {
			try {
				log.info("Se va a borrar el archivo del FTP: " + urlRemoteFile);
				completed = ftpClient.deleteFile(urlRemoteFile);
				
				if (completed) {
					log.info("El archivo " + urlRemoteFile + " fue borrado exitosamente.");
				} else {
					log.info("El archivo " + urlRemoteFile + " no pudo ser borrado.");
				}
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return completed;
	}
	
	public InputStream readRemoteFile(String urlRemoteFile) throws IOException{
		return ftpClient.retrieveFileStream(urlRemoteFile);
	}
	
}
