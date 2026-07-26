package br.com.joaoszczypior.spring_boot_essentials.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.key}")
    private String key;

    //Gerar um Token
    public String gerarToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return buildToken(user.getUsername());
    }

    /**
     * Função para Criar um Token JWT ao usuário da minha API
     * @param username username do Usuário
     * @return Um Token JWT compactado e com as informações do Usuário
     * @Author João Szczypior
     */
    private String buildToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        /*
            Aqui para gerar um Token estou usando uma Classe utilitária do jsonwebtoken
            Para com ela conseguir pegar e guardar as informações que eu passar no Token
            Do meu Usuário
         */
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigninKey())
                .compact();
    }

    /**
     * Função Auxiliar para de fato gerar a key do usuário
     * Ela utiliza uma função auxiliar da classe Keys para gerar uma chave a partir dos
     * Bytes da key interna
     * @return Uma secretKey
     * @Author João Szczypior
     */
    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    /**
     * Função Booleanda de validação de Token válido
     * @param token Token a ser validado
     * @return True se for válido e False se for inválido
     * @Author João Szczypior
     */
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Função para pegar o user name do Token Jwt
     * @param token Token do User
     * @return O UserName
     * @Author João Szczypior
     */
    public String getUserName (String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Função auxiliar para pegar as Claims(Informações do Token JWT)
     * Que também valida se a Assinatura e a expiração do Token são válidas
     * @param token token Jwt
     * @return As Claims do Token JWT, ou seja, suas informações
     * @Author João Szczypior
     */
    private Claims getClaims(String token) {
        //Validar assinatura
        //Validar expiração
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
