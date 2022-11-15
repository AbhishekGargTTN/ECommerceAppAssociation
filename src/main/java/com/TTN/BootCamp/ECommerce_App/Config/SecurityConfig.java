package com.TTN.BootCamp.ECommerce_App.Config;

//@Configuration
public class SecurityConfig {//extends WebSecurityConfigurerAdapter {

//
//    @Autowired
//    DataSource ds;
//
//    @Override
//    @Bean(BeanIds.USER_DETAILS_SERVICE)
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
//
//    @Override
//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean("userPasswordEncoder")
//    PasswordEncoder userPasswordEncoder() {
//        return new BCryptPasswordEncoder(4);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        // BCryptPasswordEncoder(4) is used for users.password column
//        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> cfg = auth.jdbcAuthentication()
//                .passwordEncoder(userPasswordEncoder()).dataSource(ds);
//
//        cfg.getUserDetailsService().setEnableGroups(true);
//        cfg.getUserDetailsService().setEnableAuthorities(false);
//    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
