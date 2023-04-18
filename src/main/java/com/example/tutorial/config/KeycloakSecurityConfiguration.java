package com.example.tutorial.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {
    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http
                .authorizeRequests()
//                .antMatchers("/customers*").hasRole("USER")
//                .antMatchers("/admin*").hasRole("ADMIN")
                .anyRequest().permitAll();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        logger.info("Inside configure method");
//        http.cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/*")
//                .authenticated()
//                .and()
//                .csrf()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("logout.do", "GET"));
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        /* @formatter:off */
//        web.ignoring()
//                .mvcMatchers("/js/**")
//                .and()
//                .ignoring()
//                .mvcMatchers("/css/**")
//                .and()
//                .ignoring()
//                .mvcMatchers("/images/**")
//                .and()
//                .ignoring()
//                .mvcMatchers("/html/**")
//                .and()
//                .ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .and()
//                .ignoring()
//                .antMatchers("/web");
//        /* @formatter:on */
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(
//                Arrays.asList(GET.name(), POST.name(), PUT.name(), DELETE.name()));
//        configuration.setAllowedHeaders(Arrays.asList(CORS_ALLOWED_HEADERS.split(",")));
//        configuration.setMaxAge(corsMaxAge);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}