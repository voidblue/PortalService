package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthContext {
    public static ResultMessage askAuthorityAndAct(String userFromEntityModel, String token,
                                                   HttpServletResponse res, ActAfterAuthStrategy actAfterAuthStrategy) {
        ResultMessage resultMessage = null;
        if (token == null) {
            resultMessage = ResultMessageFactory.notLogined();
        } else {
            String user = null;
            try {
                Jws<Claims> claims = Jwts.parser()
                        .setSigningKey("portalServiceFinalExam")
                        .parseClaimsJws(token);
                user = (String) claims.getBody().get("id");
            }catch (MalformedJwtException e){
                resultMessage = ResultMessageFactory.notLogined();
            }
            if (userFromEntityModel.equals(user)) {
                actAfterAuthStrategy.act();
                resultMessage = ResultMessageFactory.accept();
            } else {
                resultMessage = ResultMessageFactory.noAuthority();
            }
            res.setStatus(resultMessage.getResultCode());
        }
        return resultMessage;
    }
}