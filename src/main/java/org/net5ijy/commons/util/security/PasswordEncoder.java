package org.net5ijy.commons.util.security;

public interface PasswordEncoder {

	String encode(String password);

	boolean match(String password, String encryptPasswd);
}
