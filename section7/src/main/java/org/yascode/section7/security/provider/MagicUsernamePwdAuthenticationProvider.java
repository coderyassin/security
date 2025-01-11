package org.yascode.section7.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.yascode.section7.security.exception.NotAMagicUserException;

import java.util.List;

@Component
@Profile("test")
@Slf4j
public class MagicUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        if(username.equals("magic")) {
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("magic"));
            return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
        }
        throw new NotAMagicUserException(username + " is not a magic user");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
