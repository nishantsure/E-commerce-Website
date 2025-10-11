package com.example.AmazonClone.config;


import com.example.AmazonClone.exceptionhandling.CustomAccessDeniedHandler;
import com.example.AmazonClone.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("!prod")
public class TestEnvSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private JwtFilter jwtFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(customizer -> customizer.disable())
//            .authorizeHttpRequests(customizer -> customizer
//                    .requestMatchers("login","register").permitAll()
//                .anyRequest().authenticated()
//            )
////                .oauth2Login(customizer -> customizer.loginPage("login"))
//            .httpBasic(Customizer.withDefaults())
//        .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }


@Bean
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
            .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
//            .requiresChannel(rcc->rcc.anyRequest().requiresInsecure())  //for http protocol
            .csrf(csrfConfig -> csrfConfig.disable())

            .authorizeHttpRequests(customizer -> customizer
                    .requestMatchers("register").permitAll()
                .anyRequest().authenticated()
            );
//            .authorizeHttpRequests((requests) -> requests
//                    .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
//                    .requestMatchers("/notices", "/contact", "/error", "/register").permitAll());
    http.formLogin(withDefaults());
    http.httpBasic(customizer -> customizer.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//    http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); //at global level


    http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

    return http.build();
}


//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
