package com.TTN.BootCamp.ECommerce_App.Config;

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig {//extends AuthorizationServerConfigurerAdapter {
//    @Autowired
//    DataSource ds;
//
//    @Autowired
//    AuthenticationManager authMgr;
//
//    @Autowired
//    private UserDetailsService usrSvc;
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(ds);
//    }
//
//    @Bean("clientPasswordEncoder")
//    PasswordEncoder clientPasswordEncoder() {
//        return new BCryptPasswordEncoder(4);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer cfg) throws Exception {
//
//        // This will enable /oauth/check_token access
//        cfg.checkTokenAccess("permitAll");
//
//        // BCryptPasswordEncoder(4) is used for oauth_client_details.user_secret
//        cfg.passwordEncoder(clientPasswordEncoder());
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(ds);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints.tokenStore(tokenStore());
//        endpoints.authenticationManager(authMgr);
//        endpoints.userDetailsService(usrSvc);
//    }
}
