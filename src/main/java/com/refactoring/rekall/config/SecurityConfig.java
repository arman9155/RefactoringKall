/*


package com.refactoring.rekall.config;


import com.refactoring.rekall.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // WebSecurityConfiguererAdapter를 상속받는 클래스에 선언하면 SpringSecurityFilterChain이 자동으로 포함됨
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserService userService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 비밀번호를 그대로 저장하지 않고 BCryptPasswordEncoder의 해시 함수를 이용하여 암호화 처리
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 인증을 무시하기 위한 설정
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http 요청에 대한 보안을 설정
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/**").permitAll()
                .and()
             .formLogin() //로그인설정
                .loginPage("/login")
                .successForwardUrl("/main")
                .permitAll()
                .and()
             .logout()
*/
/*                .logoutRequestMatcher(new AntPathRequestMatcher("/main"))*//*

                .logoutSuccessUrl("/main")
                .invalidateHttpSession(true) //세션 초기화
                .and()
             .rememberMe().tokenValiditySeconds(360000000).key("mySecretKey")
                .rememberMeParameter("save_ID")
                .and()
             .exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 처리를 하기 위한 AuthenticationManagerBuilder를 설정
    }
}
*/
