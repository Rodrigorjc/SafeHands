package com.valencia.proyecto1evaluacion.security;

import com.valencia.proyecto1evaluacion.enums.Rol;
import com.valencia.proyecto1evaluacion.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;





    @Bean
    public SecurityFilterChain secutityFilterChain(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/usuarios/**").permitAll();
                    auth.requestMatchers("producto/crear").permitAll();
                    auth.requestMatchers(("proveedor/**")).permitAll();
                    auth.requestMatchers("ong/**").permitAll();
                    auth.requestMatchers("producto/{productoId}/vincular-acontecimiento/{acontecimientoId}").permitAll();
                    auth.requestMatchers("ong/validar/proveedor/{id}").permitAll();
                    auth.requestMatchers("acontecimiento/**").permitAll();
                    auth.requestMatchers("producto/**").permitAll();
                    auth.requestMatchers("ong/:id").permitAll();
                    auth.requestMatchers("producto/**").permitAll();
                    auth.requestMatchers("producto/buscar").permitAll();
                    auth.requestMatchers("producto/filtrar").permitAll();
                    auth.requestMatchers("producto/donaciones").permitAll();
                    auth.requestMatchers("ong/**").hasAnyAuthority(Rol.ONG.name());
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

