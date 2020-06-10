package cn.zjc.util;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * JWT 工具类
 */
public class JwtTokenUtil{

    private static final String CLAIM_KEY_SUBJECT = "create_token";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USERNAME = "userName";
    private static final String CLAIM_KEY_USER_ID = "userId";
    /** 过期时间 60分钟**/
    private static final long EXPIRATION_TIME = 60 * 60 * 1000;
    /** JWT密码**/
    private static final String SECRET = "JwtSecret";


    /**
     * 获取username
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USERNAME, String.class);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 获取token创建时间
     * @param token
     * @return
     */
    private static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date(claims.get(CLAIM_KEY_CREATED, Long.class));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    private static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 获取Claims对象
     * @param token
     * @return
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 设置过期时间
     * @return
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (Objects.isNull(expiration)) {
            return false;
        }
        return expiration.after(new Date());
    }

    private boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 创建token
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USER_ID, UUID.randomUUID().toString());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //过期时间
                .setExpiration(generateExpirationDate())
                //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为用户的唯一标志。
                .setSubject(CLAIM_KEY_SUBJECT)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重复攻击。
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512, generalKey())
                //iat: jwt的签发时间
                .setIssuedAt(new Date())
                .compact();
    }

    public boolean canTokenBeRefreshed(String token) {
        return isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token
     * @param token
     * @param userDetails
     * @return
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    private static SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64(SECRET);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKeySpec aes = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}