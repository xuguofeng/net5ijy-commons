package org.net5ijy.commons.util.security;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.net5ijy.commons.util.security.PasswordEncoder;
import org.net5ijy.commons.util.security.SaltTimestampPasswordEncoder;

public class SaltTimestampSaltPasswordEncoderTest {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@Before
	public void before() {
		log.setLevel(Level.INFO);
	}

	@Test
	public void testEncode() {
		PasswordEncoder encoder = new SaltTimestampPasswordEncoder();
		String pass = encoder.encode("徐国峰");
		log.info(pass);
		assertTrue(pass != null && pass.length() > 0);
	}

	@Test
	public void testMatch() {
		PasswordEncoder encoder = new SaltTimestampPasswordEncoder();
		String pass = encoder.encode("徐国峰");
		log.info(pass);
		assertTrue(encoder.match("徐国峰", pass));
	}
}
