package com.tvstorm.leespringbootstudy.config;

import com.tvstorm.leespringbootstudy.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // Spring Security를 Config 하는 곳에 선언
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // spring security 사용을 위한 상속, configure override
    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder -> 스프링 시큐리티에서 제공하는 비번 암호화 객체
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception { // WebSecurity FilterChainProxy생성  https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html
        // resource -> static에 담긴 정적 리소스들은 스프링시큐리티가 무시
        web.ignoring().antMatchers("/css**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // HTTPSecurity docs https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
        //authorizeRequests()은 HttpServletRequest에 따라 접근을 제한
        http.authorizeRequests()
                // page 권한 설정
                .antMatchers("/admin.**").hasRole("ADMIN") // antMatchers 메소드는 특정 경로를 지정하고 Role(권한) 부여
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
                .and()

                // 로그인 설정
                // form 기반으로 인증, 로그인 정보는 기본적으로 HttpSession을 이용, /login url로 접근하면 스프링 시큐리티에서 제공하는 로그인 폼 사용
                .formLogin()
                .loginPage("/user/login") // 기본 제공 로그인 폼 말고 직접 커스터마이징, * 이 안에 선언한 url 경로와 login.html의 action 경로가 일치해야 인증처리 가능
                .defaultSuccessUrl("/user/login/result") // 로그인 성공 시 이동되는 페이지
                .permitAll()
                .and()

                // 로그아웃 설정
                // 로그아웃을 지원하며 WebSecurityConfigurerAdapter에서 자동 적용
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 기능의 기본 url(/logout)이 아닌 다른 URL로 재정의
                .logoutSuccessUrl("/user/logout/result") // 로그아웃 성공시
                .invalidateHttpSession(true) // HTTP 세션 초기화 true, deleteCookies("")을 사용하면 특정 쿠키만 제거
                .and()
                //403 예외처리 handling
                .exceptionHandling().accessDeniedPage("/user/denied"); // 예외처리 핸들링이며 여기선 접근 없으면 denied를 통해 로그인 페이지로 이동하도록 설계

//        http.csrf().disable(); // Spring Security가 csrf를 알아서 검사하기 때문에 @PostMapping이 되지 않는 이슈가 있었음... 이렇게 하지 말기
    }

    @Override
    // 스프링 시큐리티의 모든 인증은 AuthenticationManager를 통해 이루어지며 아래의 파라미터는 이걸 생성하기 위해 사용
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인증 처리를 위해서 필요한 data를 UserDetailsService를 이용하여 가져옴.
        // memberSerivce 클래스에서 UserDetailService interface의 loadUserByUsername()를 구현할 것
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder()); // 비밀번호는 암호화 해야 하기 때문에 passwordEncoder를 추가 사용
    }
}
