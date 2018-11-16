package org.net5ijy.commons.util.security;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.net5ijy.commons.util.security.Md5PasswordEncoder;
import org.net5ijy.commons.util.security.PasswordEncoder;

public class Md5PasswordEncoderTest {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@Before
	public void before() {
		log.setLevel(Level.INFO);
	}

	@Test
	public void testEncode() {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encode("徐国峰");
		log.info(pass);
		assertTrue(pass != null && pass.length() > 0);
	}

	@Test
	public void testMatch() {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encode("徐国峰");
		log.info(pass);
		assertTrue(encoder.match("徐国峰", pass));
	}
}
