package com.supergo.auth.config;

import com.supergo.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    AuthenticationManager authenticationManager;

    //注入
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //注入数据源
    @Autowired
    private DataSource dataSource;

    //注入tokenStore
    @Autowired
    private TokenStore tokenStore;

    //注入客户端详细信息
    @Autowired
    private ClientDetailsService clientDetailsService;

    //注入
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    //注入私钥相关配置信息
    @Bean(name = "keyProp")
    public KeyProperties keyProperties(){
        return new KeyProperties();
    }

    //注入对象
    @Resource(name = "keyProp")
    private KeyProperties keyProperties;



    /**
     * 实际上重定向地址是什么无所谓，但是必须是一个不受保护的地址，否则无法访问，且追加授权码
     * @param clients
     * @throws Exception
     */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

       /* String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
        //把用户信息放入内存进行存储
        clients.inMemory()
                .withClient("client_1")//客户端id
                .secret(finalSecret)//密码，要保密
                .redirectUris("http://www.baidu.com")
                .accessTokenValiditySeconds(60)//访问令牌有效期
                .refreshTokenValiditySeconds(60)//刷新令牌有效期
                //授权客户端请求认证服务的类型authorization_code：根据授权码生成令牌，
                // client_credentials:客户端认证，refresh_token：刷新令牌，password：密码方式认证
                .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password")
                .scopes("app");//客户端范围，名称自定义，必填*/
        //认证信息，需要去匹配数据库信息,不在是上面静态的数据
        clients.withClientDetails(clientDetailsService);
    }

    //token的存储方法
    //把存储在内存中token修改为存储在数据库
    //使用jwt令牌后，令牌就不需要存储在数据库
    @Bean
    public TokenStore tokenStore() {
       // return new InMemoryTokenStore();
        //把token存储在数据库
        //return new JdbcTokenStore(dataSource);
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    //声明客户端详细信息从数据库中进行获取
    @Bean
    public ClientDetailsService clientDetailsService(){
        //声明ClientDetailsService,通过数据库调取客户端的详情信息
        return new JdbcClientDetailsService(dataSource);
    }

    //公钥私钥对认证,生成Jwt令牌
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory
                (keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias(),keyProperties.getKeyStore().getPassword().toCharArray());
        converter.setKeyPair(keyPair);
        //配置自定义的CustomUserAuthenticationConverter
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
        return converter;
    }



    //授权服务器的安全配置
    //用来配置令牌端点(Token Endpoint)的安全约束.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //oauthServer.checkTokenAccess("isAuthenticated()");//校验token需要认证通过，可采用http basic认证
        oauthServer.allowFormAuthenticationForClients()
               // .passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    //用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
       /* //把token存储在内存，或者是redis
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);*/
       //不在把token放入内存，此时我们把它存储在数据库，因此要去数据库查询匹配
        endpoints.accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .userDetailsService(userDetailsService); //从数据库查询比对

        //DefaultTokenService作为oauth2中操作token(crud)的默认实现
        //配置tokenServices参数
       /* DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        //设置token
        defaultTokenServices.setTokenStore(endpoints.getTokenStore());
        //是否支持RefreshToken
        defaultTokenServices.setSupportRefreshToken(false);
        //设置用户详细信息
        defaultTokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        //
        defaultTokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天
        endpoints.tokenServices(defaultTokenServices);*/


    }
}

