/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.billing.common;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author duglas
 */
@Configuration
@EnableWebSecurity
@EnableCaching
public class SecurityConfiguration {

    private static final String[] NO_AUTH_LIST = {
        "/v3/api-docs",//
        "/configuration/ui", //
        "/swagger-resources", //
        "/configuration/security", //   
        "/webjars/**", //
        "/login",
        "/h2-console/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//Choose one configuration
        http.cors(withDefaults());
        // 01- Seguridad completa para solicitar usuario y contraseña antes de acceder a Swagger UI
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        return http.build();

        // 02- Configuración personalizada de seguridad, podemos excluir algunas rutas y solicitar usuario y contraseña antes de cada solicitud para acceder a Swagger UI
        //http
        //         /*- Esta propiedad está activa por defecto, necesitamos desactivarla para permitir solicitar usuario y contraseña en los métodos POST.
        //         
        //          - Cross site request forgery (CSRF o XSRF) se refiere a un ataque que hace que el usuario realice acciones no deseadas dentro de una aplicación web 
        //          que ya les ha otorgado autenticación.
        //          
        //         - La mejor opción para protegerse es usar un Token en cada solicitud, como un JWT
        //              */
        //           .csrf().disable()
        //         // Configurar restricciones personalizadas para solicitar usuario y contraseña
        //         .authorizeHttpRequests((authz) -> authz
        //             .antMatchers(NO_AUTH_LIST).permitAll()                
        //             .antMatchers(HttpMethod.POST, "/*billing*/**").authenticated()
        //             // Usando valores predeterminados, podemos definir roles en el archivo .properties que se establecerán cuando el usuario esté autenticado
        //             .antMatchers(HttpMethod.GET,"/*billing*/**").hasRole("ADMIN")
        //         )                
        //         // Usar credenciales predeterminadas en el archivo .properties
        //         .httpBasic(withDefaults())
        //         // Usar la interfaz de usuario predeterminada.
        //         .formLogin(withDefaults());
        // return http.build();
    }

    // Estos manipuladores implementan la interfaz CorsConfigurationSource para proporcionar una CorsConfiguration para cada solicitud.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
         System.out.println("CorsConfigurationSource being executed...");
        CorsConfiguration cc = new CorsConfiguration();

        cc.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Agrega tus orígenes confiables aquí

        cc.setAllowedHeaders(Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE")); // Permitir métodos HTTP OPTIONS

        cc.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);

        return source;
    }

}
