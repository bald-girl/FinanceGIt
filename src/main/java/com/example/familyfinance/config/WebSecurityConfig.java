package com.example.familyfinance.config;

import com.example.familyfinance.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    private DataSource dataSource;
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.authorizeRequests()
//                .antMatchers( "/addMember").hasAuthority("homeowner")
                //允许访问--主页、登录页、、、、
                .antMatchers( "/pre_login","/login","/index","/pre_register","/register","/jump").permitAll()
                .antMatchers( "/css/**","/img/**","/assets/**","/js/**").permitAll()
                .antMatchers("/pre_list").hasAnyAuthority("user","homeowner")
//                .antMatchers("/updateMember").hasAnyAuthority("homeowner")
                //访问所有资源都要拦截
                .antMatchers( "/**")
                //authenticated:如果用户不是匿名的则返回true   fullyAuthenticated:如果用户不是匿名的或记住我的则返回true
                .fullyAuthenticated()

                .and()
                //退出登录
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
//                .and()
//                //记住我
//                .rememberMe().tokenRepository(persistentTokenRepository())
//                //有效时长（秒）
//                .tokenValiditySeconds(180)


                .and()
                //允许表单登录
                .formLogin()
//                .loginPage("/pre_login")
//                .successHandler(successHandler)
                //登录成功跳转的controller层路径
                .successForwardUrl("/pre_list");



    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //配置密码编码加密算法为BCrypt
        return new BCryptPasswordEncoder();
    }


}
