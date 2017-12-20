package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import org.springframework.security.web.csrf.DefaultCsrfToken;

import org.springframework.http.HttpMethod;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
				.antMatchers("/", "/home", "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/accessCodeRequest").permitAll()
				.antMatchers(HttpMethod.POST, "/accessTokenRequest").permitAll()
				.antMatchers(HttpMethod.POST, "/userInfoRequest").permitAll()
				.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/logoutBack").permitAll().and()
			.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and()
			.csrf()
				.disable();
    }
	
	// .antMatchers("/**").permitAll()
	// .antMatchers("/", "/home", "/login").permitAll()
	
	@Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
	
	
}








