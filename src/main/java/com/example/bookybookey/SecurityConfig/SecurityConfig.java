package com.example.bookybookey.SecurityConfig;

import com.example.bookybookey.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                //ADMIN
                .requestMatchers("/api/v1/auth/get-all").hasRole("ADMIN")
                .requestMatchers("/api/v1/book/add","/api/v1/book/addd/{isbn}","/api/v1/book/update","/api/v1/book/delete").authenticated()


                //USER
                .requestMatchers("/api/v1/auth/update","/api/v1/auth/delete","/api/v1/book/addBookByISBN/{isbn}").authenticated()
                .requestMatchers("/api/v1/readingList/getAllMyReadingLists","/api/v1/readingList/getReadingList/{readingListId}",
                        "/api/v1/readingList/create","/api/v1/readingList/updateReadingListName/{readingListId}/{name}","/api/v1/readingList/deleteReadingList/{readingListId}",
                        "/api/v1/readingList/addBookToReadingList/{bookId}/{readingListId}","/api/v1/readingList/deleteBookFromReadingList/{bookId}/{readingListId}").authenticated()
                //ALL-Not signed
                .requestMatchers("/api/v1/auth/register","/api/v1/book/get").permitAll()



                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
