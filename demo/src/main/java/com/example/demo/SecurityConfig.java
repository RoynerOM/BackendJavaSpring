package com.example.demo;

import com.example.demo.Configuration.JwtRequestFilter;
import com.example.demo.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private com.example.demo.Services.MyUserDetailsService MyUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(MyUserDetailsService);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.cors().and().csrf().disable()
                .authorizeRequests()
                 //.antMatchers(HttpMethod.POST,"/authenticate").permitAll()
                .antMatchers(HttpMethod.GET,"/users/{email}/{password}").permitAll()
                .antMatchers(HttpMethod.GET,"/topic").permitAll()
                .antMatchers(HttpMethod.GET,"/topic/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/topic").permitAll()
                .antMatchers(HttpMethod.PUT,"/topic").permitAll()
                .antMatchers(HttpMethod.DELETE,"/topic/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"users").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/users").permitAll()
                .antMatchers(HttpMethod.PUT,"/users").permitAll()
                .antMatchers(HttpMethod.POST,"/resource").permitAll()
                .antMatchers(HttpMethod.PUT,"/resource").permitAll()
                .antMatchers(HttpMethod.DELETE,"/resource/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/resource").permitAll()
                .antMatchers(HttpMethod.GET,"/resource{id}").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
             http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); }

}
