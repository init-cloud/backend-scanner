package scanner.prototype.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import scanner.prototype.exception.AuthEntryPoint;
import scanner.prototype.filter.TokenAuthenticationFilter;
import scanner.prototype.middleware.token.TokenProvider;
import scanner.prototype.model.enums.RoleType;
import scanner.prototype.repository.UserRepository;
import scanner.prototype.repository.UserTokenRepository;
import scanner.prototype.service.user.UserDetailService;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppConfigProperties appConfigProperties;
    private final TokenProvider tokenProvider;
    private final AccessDeniedHandler accessDeniedHandler;
    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;
    private final UserDetailService userDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) 
    throws Exception
    {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) 
    throws Exception 
    {
        http.cors()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthEntryPoint())
                .accessDeniedHandler(accessDeniedHandler)
            .and()
                .authorizeRequests()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/api/v1/**").hasAnyAuthority(RoleType.GUEST.getCode())
                .antMatchers("/api/v1/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .anyRequest().authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() 
    throws Exception
    {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
}
