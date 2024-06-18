package patientrecord.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeRequests()
	            	
	                .antMatchers("/getPatientRecord/**").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
	                .antMatchers("/updatePatientRecord/**").hasAnyRole("DOCTOR", "ADMIN")
	                .antMatchers("/deletePatientRecord/**", "/createPatientRecord/**").hasRole("ADMIN")
	                .anyRequest().permitAll()
	                .and()
	            .httpBasic();
	    }

	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
