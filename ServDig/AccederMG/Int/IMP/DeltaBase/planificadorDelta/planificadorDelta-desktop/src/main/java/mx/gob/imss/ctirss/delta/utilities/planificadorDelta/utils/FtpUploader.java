package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUploader {
	protected final Log log = LogFactory.getLog(getClass());
	private static final String URL_APPLICATION_PROPERTIES = "application.properties";

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
			OutputStream outputStream = null;
			try {
				log.info("Start uploading file: " + urlRemoteFile);
				outputStream = ftpClient.storeFileStream(urlRemoteFile);
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
				if (outputStream != null) {
					IOUtils.closeQuietly(outputStream);
				}

				disconnect();
			}
		}

		return isUploaded;
	}

	private boolean checkDirectoryExists(String dirPath) throws IOException {		
		return ftpClient.changeWorkingDirectory(dirPath);
	}

	public boolean uploadDirPath(String rootPath, String dirPath) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			try {
				log.info("Creating directory: " + dirPath);

				if (!checkDirectoryExists(dirPath)) {
					boolean completed = ftpClient.makeDirectory(dirPath);

					if (completed) {
						log.info("The directory '" + dirPath + "' is uploaded successfully.");
						isUploaded = true;
					}
				} else {
					ftpClient.changeWorkingDirectory(rootPath);
					isUploaded = true;
				}

			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return isUploaded;
	}

	public boolean uploadFile(String urlLocalFile, String urlRemoteFile) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			File localFile = new File(urlLocalFile);
			OutputStream outputStream = null;
			InputStream inputStream = null;

			try {
				inputStream = new FileInputStream(localFile);

				log.info("Start uploading file: " + urlRemoteFile);
				outputStream = ftpClient.storeFileStream(urlRemoteFile);
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
				if (outputStream != null) {
					IOUtils.closeQuietly(outputStream);
				}
				if (inputStream != null) {
					IOUtils.closeQuietly(inputStream);
				}

				disconnect();
			}
		}

		return isUploaded;
	}

	public boolean downloadFile(String urlRemoteFile, String urlLocalFile) {
		boolean isDownloaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			try {
				FileOutputStream fos = new FileOutputStream(urlLocalFile);
				boolean download = ftpClient.retrieveFile(urlRemoteFile, fos);
				if (download) {
					log.info("The file " + urlRemoteFile + " is downloaded successfully.");
					isDownloaded = true;
				} else {
					log.error("Error in downloading file !");
				}
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return isDownloaded;
	}

	public boolean removeFile(String urlRemoteFile) {
		boolean isDeleted = false;
		boolean isConnected = connect();

		if (isConnected) {
			try {
				boolean deleted = ftpClient.deleteFile(urlRemoteFile);

				if (deleted) {
					log.info("The file " + urlRemoteFile + " is removed successfully.");
					isDeleted = true;
				} else {
					log.error("Error in downloading file !");
				}
			} catch (IOException e) {
				log.error("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				disconnect();
			}
		}

		return isDeleted;
	}

	public boolean simpleUploadFile(String urlLocalFile, String urlRemoteFile) {
		boolean isUploaded = false;
		boolean isConnected = connect();

		if (isConnected) {
			File localFile = new File(urlLocalFile);
			InputStream inputStream = null;

			try {
				inputStream = new FileInputStream(localFile);

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
				if (inputStream != null) {
					IOUtils.closeQuietly(inputStream);
				}

				disconnect();
			}
		}

		return isUploaded;
	}
}
