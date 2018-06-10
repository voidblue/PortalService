package com.voidblue.finalexam.Utils;

public class ResultMessageFactory {
    public static ResultMessage get200(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    public static ResultMessage notLogined(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("로그인이 되어있지 않습니다.");
        return resultMessage;
    }
}
