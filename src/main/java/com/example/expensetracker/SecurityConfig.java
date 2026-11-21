package com.example.expensetracker;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //yo class ma spring ko setting haru chan
@EnableWebSecurity //login security chalcha yesle
public class SecurityConfig {

    @Bean //password hash garni BCrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // duita user banauney properties bata name ra password lincha
    public UserDetailsService userDetailsService(PasswordEncoder encoder,
                                                 @Value("${app.user1.name}") String name1,
                                                 @Value("${app.user1.password}") String pass1,
                                                 @Value("${app.user2.name}") String name2,
                                                 @Value("${app.user2.password}") String pass2) {


        UserDetails user1 = User.withUsername(name1)
                .password(encoder.encode(pass1)) //password hash garera rakhcha
                .roles("User")
                .build();

        UserDetails user2 = User.withUsername(name2)
                .password(encoder.encode(pass2)) //password hash garera rakhcha
                .roles("User")
                .build();

        return new InMemoryUserDetailsManager(user1, user2); //memory ma duita user bascha
    }

    @Bean //kun page ma login chaincha kun ma chaidaina  yesle hercha
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/*.css", "/login").permitAll() //css ra login page login nagari khulcha
                        .anyRequest().authenticated()) //baki sabai page ma log in chaincha
                .formLogin(form -> form
                        .loginPage("/login") // afno login page
                        .defaultSuccessUrl("/dashboard",true) //login pachi dashboardma
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") //logout pachi login page ma
                        .permitAll());
        return http.build();


    }

}
