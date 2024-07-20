package com.soquetes.Loquetes;


import com.soquetes.Loquetes.servicios.Usuario_servicio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {

    private final Usuario_servicio usuarioServicio;

    public SeguridadWeb(Usuario_servicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return usuarioServicio;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "img/**","/**").permitAll()
        ).formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("logincheck")
                        .usernameParameter("nombre")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
        ).logout(logout ->
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
        ).csrf(csrf -> csrf.disable());

        return http.build();
    }


}
