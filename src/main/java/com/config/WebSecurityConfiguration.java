package com.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests().anyRequest()
//                .permitAll()
////                .authenticated()
//        ;
//
//
//        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .successHandler(
//                        (request, response, authentication) -> {
//                            if(request.getParameter("redirect") != null) {
//                                response.sendRedirect(request.getParameter("redirect").toString());
//                            }
//                            else response.sendRedirect("/");
//                        }
//                ).and().logout().logoutUrl("/logout")
//                .and()
//                .rememberMe()
//                .key(UUID.randomUUID().toString())
//                .useSecureCookie(true);

//        http.oauth2Login()
//                .successHandler((req, res, attributes) -> {
//                    UserService userService = (UserService) userDetailsService;
//                    DefaultOAuth2User oauth2User = (DefaultOAuth2User) (attributes.getPrincipal());
//                    String email = oauth2User.getAttributes().get("email").toString();
//
//                    UserEntity original = userService.findByEmail(email);
//                    if (original == null) {//create new user
//                        original = userRepository.save(new UserEntity(null, oauth2User.getAttribute("sub"), passwordEncoder.encode(UUID.randomUUID().toString()), oauth2User.getName(), email, true, 0, 0, Collections.singletonList(authorityRepository.findByAuthority(SecurityUtils.ROLE_ADMIN))));
//                    }
//                    Authentication authentication = new UsernamePasswordAuthenticationToken(new CustomUserDetail(original), null, original.getAuthorities() == null ? null :
//                            original.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList()));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    res.sendRedirect("/");
//                })
//                .and()
//                .logout()
//                .logoutSuccessUrl("/").permitAll();
    }
}
