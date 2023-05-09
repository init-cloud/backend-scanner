package scanner.security.config;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import scanner.security.filter.JwtAuthenticationFilter;
import scanner.security.handler.JwtGlobalEntryPoint;
import scanner.security.provider.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ApplicationContext applicationContext;
	private final JwtGlobalEntryPoint jwtGlobalEntryPoint;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		DefaultMethodSecurityExpressionHandler defaultWebSecurityExpressionHandler = getApplicationContextBean();
		defaultWebSecurityExpressionHandler.setPermissionEvaluator(myPermissionEvaluator());

		http.csrf().disable()
			.httpBasic().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/v2/api-docs/**").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/api/v1").permitAll()
			.antMatchers("/api/v1/app/callback").permitAll()
			.antMatchers("/api/v1/user/signin").permitAll()
			.antMatchers("/api/v1/user/signup").permitAll()
			.and()
			.authorizeRequests().antMatchers("/api/v1/admin/**").hasRole("ADMIN")
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtGlobalEntryPoint)
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, properties),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PermissionEvaluator myPermissionEvaluator() {
		return new PermissionEvaluator() {
			@Override
			public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
				long targetDomainObjectCounts = authentication.getAuthorities()
					.stream()
					.filter(grantedAuthority -> grantedAuthority.getAuthority().equals(targetDomainObject))
					.count();

				return (targetDomainObjectCounts > 0);
			}

			@Override
			public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
				Object permission) {
				return false;
			}
		};
	}

	private DefaultMethodSecurityExpressionHandler getApplicationContextBean() {
		return this.applicationContext.getBean(DefaultMethodSecurityExpressionHandler.class);
	}
}
