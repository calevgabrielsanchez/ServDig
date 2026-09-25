package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.ivro.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.apache.commons.io.IOUtils;

public class LayoutCallOut {
	private static final int MAX_RETRY = 1000;

	public static String generateLayout(String fileName, byte[] content)
			throws IOException {
		FileOutputStream outputStream = new FileOutputStream(
				new File(fileName), true);
		FileChannel channel = outputStream.getChannel();
		FileLock lock = obtainLock(channel);
		try {
			String str = new String(content);
			str = str.replaceFirst("(?s)\n$", "");
			String strRegister = String.format("%s\r\n", new Object[] { str });
			channel.write(ByteBuffer.wrap(strRegister.getBytes()));
			return str;
		} catch (Exception e) {
			String strRegister;
			e.printStackTrace();
			return null;
		} finally {
			lock.release();
			channel.close();
			IOUtils.closeQuietly(outputStream);
		}
	}

	private static final FileLock obtainLock(FileChannel channel)
			throws OverlappingFileLockException, IOException {
		int retries = 0;
		FileLock lock = null;
		do {
			try {
				retries++;
				lock = channel.tryLock();
			} catch (OverlappingFileLockException lockException) {
			}
		} while (lock == null);
		return lock;
	}
}
