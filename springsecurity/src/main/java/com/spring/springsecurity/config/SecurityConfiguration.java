package com.spring.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserPrincipalDetailsService userPrincipalDetailsService;

    @Autowired
    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
            .withUser("mostafa").password(passwordEncoder().encode("mostafa"))
            //.roles("ADMIN")
            //.authorities("Access Basic1")
            .authorities("Access Basic1","ROLE_ADMIN")
            .and()
            .withUser("ali").password(passwordEncoder().encode("ali"))
            //.roles("MANGER")
            //.authorities("Access Basic2")
            .authorities("Access Basic2","ROLE_MANGER")
            .and()
            .withUser("ahmed").password(passwordEncoder().encode("ahmed")).roles("USER");*/

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            //.anyRequest().authenticated()
            .antMatchers("/api/main").permitAll()
            .antMatchers("/api/profile").authenticated()
            //.antMatchers("/api/admin").hasRole("ADMIN")
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/manag").hasAnyRole("ADMIN","MANGER")
            //.antMatchers("/api/basic/**").authenticated()
            .antMatchers("/api/basic/mybasic").hasAuthority("Access Basic1")
            .antMatchers("/api/basic/allbasic").hasAuthority("Access Basic2")
            .and()
            //.httpBasic();
            .formLogin()
            .loginProcessingUrl("/signin")
            .loginPage("/api/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/api/main");
    }
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
