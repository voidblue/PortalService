package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class AuthContext {
    public static ResultMessage askAuthorityAndAct(String userFromEntityModel, String token, ActAfterAuthStrategy actAfterAuthStrategy) {
        ResultMessage resultMessage = null;
        if (token == null) {
            resultMessage = ResultMessageFactory.notLogined();
        } else {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey("portalServiceFinalExam")
                    .parseClaimsJws(token);
            String user = (String) claims.getBody().get("id");
            if (userFromEntityModel.equals(user)) {
                actAfterAuthStrategy.act();
                resultMessage = ResultMessageFactory.accept();
            } else {
                resultMessage = ResultMessageFactory.noAuthority();
            }
        }
        return resultMessage;
    }
}