package org.net5ijy.commons.util.security.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

import org.net5ijy.commons.util.IOUtils;

public class FileDES {

	private static final int BUFFER_SIZE = 1024 * 1024 * 16;

	private Key key;

	public FileDES(String str) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(str.getBytes()));
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing FileDES: " + e);
		}
	}

	public void encrypt(File srcFile, File destFile) throws Exception {
		InputStream is = null;
		CipherInputStream cis = null;
		OutputStream out = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
			is = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			cis = new CipherInputStream(is, cipher);
			byte[] buf = new byte[BUFFER_SIZE];
			int len = cis.read(buf);
			while (len > 0) {
				out.write(buf, 0, len);
				len = cis.read(buf);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeStream(is);
			IOUtils.closeStream(cis);
			IOUtils.closeStream(out);
		}
	}

	public void decrypt(File srcFile, File destFile) throws Exception {
		InputStream is = null;
		OutputStream out = null;
		CipherOutputStream cos = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, this.key);
			is = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			cos = new CipherOutputStream(out, cipher);
			byte[] buf = new byte[BUFFER_SIZE];
			int len = is.read(buf);
			while (len >= 0) {
				cos.write(buf, 0, len);
				len = is.read(buf);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeStream(is);
			IOUtils.closeStream(out);
			IOUtils.closeStream(cos);
		}
	}
}
