package com.ted.eBayDIT.security;


public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;//10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";
    public static final String TOKEN_SECRET ="ThisTokenShouldBeSecret";
}
