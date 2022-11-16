package com.TTN.BootCamp.ECommerce_App.Config;

import org.springframework.beans.factory.annotation.Autowired;

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig {//extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    BCryptPasswordEncoder passwordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private TokenStore tokenStore;
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//                .tokenStore(this.tokenStore)
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
//    }
//
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("ecommerce-app")
//                .secret(passwordEncoder.encode("supersecret"))
//                .accessTokenValiditySeconds(7 * 24 * 60)                // expire time for access token
//                .refreshTokenValiditySeconds(30 * 24 * 3600)            // expire time for refresh token
//                .scopes("app")                                          // scope related to resource server
//                .authorizedGrantTypes("password", "refresh_token")      // grant type
//                .resourceIds("e-commerce app");
//    }
//
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setTokenStore(this.tokenStore);
//        return tokenServices;
//    }
}
