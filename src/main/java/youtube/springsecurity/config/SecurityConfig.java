package youtube.springsecurity.config;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import youtube.springsecurity.model.Permission;
import youtube.springsecurity.model.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()

                // ------------------------------------------------------------
                // ===== закоменченно из за того, что мы поставили
                // ===== @PreAuthorize("hasAnyAuthority('developer:read')")
                // ===== в котнтроллере

////                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
////                .antMatchers(HttpMethod.POST, "/api/**").hasAnyRole(Role.ADMIN.name())
////                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPER_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPER_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPER_WRITE.getPermission())
                // ------------------------------------------------------------

                .anyRequest()// каждый запрос должен быть аутит ифицирован
                .authenticated()
                .and()
//                .httpBasic() // c помощью base64
                .formLogin() // вместо .httpBasic() -> отправляет form.html
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/auth/success", true) // если все ок то перенаправляет на эту стр
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login")

        ;
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
//                        .roles(Role.ADMIN.name())
                        .authorities(Role.ADMIN.getAuthorities())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
//                        .roles(Role.USER.name())
                        .authorities(Role.USER.getAuthorities())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
