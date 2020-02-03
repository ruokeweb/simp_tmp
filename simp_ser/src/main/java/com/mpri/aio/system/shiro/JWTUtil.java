package com.mpri.aio.system.shiro;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
/**
 * JTW帮助类
 * @author Cary
 * @date 2018年8月15日
 */
public class JWTUtil  {
	
	// web有效时间3分钟
    private static final long EXPIRE_TIME = 60*60*1000L;
    
    // web逾期时间2分钟
    public static final long REFESH_TIME = 15*60*1000L;
    
    //移动有效时间7天
    private static final long APP_EXPIRE_TIME = 7*24*60*60*1000L;
    
    // 移动逾期时间3天
    public static final long APP_REFESH_TIME = 3*24*60*60*1000L;
    
    //来源-web
    public static final String FROM_WEB = "WEB";
    
    //来源-app
    public static final String FROM_APP = "APP";
    
    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username,String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .build();
            //DecodedJWT jwt = 
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
        	//exception.printStackTrace();
            return false;
        }
    }

    /**
     *  获得token中的信息无需secret解密也能获得 
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     *  获得token中的信息无需secret解密也能获得 
     * @return token中包含的用户Id
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    
    /**
     * 获得token中的过期时间
     * @return token中包含的时间
     */
    public static long getTokenTime(String token) {
        try {
        	DecodedJWT jwt = JWT.decode(token);
			long tokenTime=jwt.getExpiresAt().getTime();
			return tokenTime;
        } catch (JWTDecodeException e) {
            return 0;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String userId,String secret,String comeFrom) {
        try {
        	
        	Algorithm algorithm = Algorithm.HMAC256(secret);
        	
        	// 附带username信息
        	if(comeFrom.equals(FROM_WEB)) {
        		Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        		return JWT.create().withClaim("username", username).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        	}else if(comeFrom.equals(FROM_APP)){
        		Date date = new Date(System.currentTimeMillis()+APP_EXPIRE_TIME);	
        		return JWT.create().withClaim("username", username).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        	}else {
        		Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        		return JWT.create().withClaim("username", username).withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        	}
        } catch (Exception e) {
            return null;
        }
        
    }
    
    /**
     * 刷新并返回新Token
     * @param token
     * @return
     */
    public static String refresh(String token) {
//    	DecodedJWT jwt = JWT.decode(token);
//    	Date now=new Date();
//    	long nowTime=now.getTime();
//    	long tokenTime=jwt.getExpiresAt().getTime();
//    	String password=getPassword(token);
//    	String username=getUsername(token);
//    	if((nowTime-tokenTime)<REFESH_TIME) {
//    		return sign(username,password);
//    	}else {
    		
    		return token;
//    	}
    }
    
    
    public static void main(String args[]) {
    	
    	String token=sign("17629261881","a01dbde1f0ce4d32b3b8838b23bddf20","5ee47b66d1422d5795b8942e18a9452d","APP");
    	
    	String userId=getUserId(token);
    	String username=getUsername(token);
//    	String backtoken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDAwMjMzMjIsInVzZXJJZCI6ImNlMTIyMGIyZmJkNDRjYTQ5YWEzMTEzZThiYjkyOWMwIiwidXNlcm5hbWUiOiIxNTM4OTI4NjE5OCJ9.gy2jlPm-D1ofnvrBvlyvZQAH5pYmAEroh66-3jMXzcU";
    	String backtoken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDI4NzU5ODQsInVzZXJJZCI6ImEwMWRiZGUxZjBjZTRkMzJiM2I4ODM4YjIzYmRkZjIwIiwidXNlcm5hbWUiOiIxNzYyOTI2MTg4MSJ9.OGXrpZvvNQkSqUlJ0W-PbtELD3vLFiPtls8T91tdVbY";
    	
    	//String nowtoken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDAwMjE2NjYsInVzZXJJZCI6ImIzNTZmZWQ2ZmE4NzQ4ZGRhYjFmZGQ4YWEzYzEyNTkxIiwidXNlcm5hbWUiOiIxNTM4OTI4NjE5NiJ9.8sK5vEwtCMjD9PZHWwHm3Y9Z2Kk2AKoJpTIkDvUB4Ec";
    	
    	
    	boolean b=verify(backtoken, "17629261881", "a01dbde1f0ce4d32b3b8838b23bddf20", "5ee47b66d1422d5795b8942e18a9452d");
    	System.out.println(b);
    	System.out.println(token);
    	System.out.println(userId);
    	System.out.println(username);
    }
}

