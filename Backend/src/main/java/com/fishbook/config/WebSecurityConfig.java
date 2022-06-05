package com.fishbook.config;

import com.fishbook.auth.RestAuthenticationEntryPoint;
import com.fishbook.auth.TokenAuthenticationFilter;
import com.fishbook.user.service.UserService;
import com.fishbook.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final TokenUtils tokenUtils;

    @Autowired
    public WebSecurityConfig(UserService userService, RestAuthenticationEntryPoint restAuthenticationEntryPoint, TokenUtils tokenUtils){
        this.userService = userService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.tokenUtils = tokenUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService), BasicAuthenticationFilter.class);

        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/auth");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/users");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/fishingLessons");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/fishingLessons/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/boats");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/boats/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/houses");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/houses/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/files/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/users/verificationCodes");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/location/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/sellerAvailabilities");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/entityAvailabilities");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/registrationRequests");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/sellerAvailabilities");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
