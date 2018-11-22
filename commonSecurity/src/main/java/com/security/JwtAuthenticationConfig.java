package main.java.com.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 *
 * @author purnima 2018/11/18
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

//    @Value("${main.java.com.url:/login}")
    private String url="/login";

    @Value("${main.java.com.jwt.header:Authorization}")
    private String header="Authorization";

    @Value("${main.java.com.jwt.prefix:Bearer}")
    private String prefix="Bearer";

    @Value("${main.java.com.jwt.expiration:#{24*60*60}}")
    private int expiration=22; // default 24 hours

    @Value("${main.java.com.jwt.secret}")
    private String secret;
}
