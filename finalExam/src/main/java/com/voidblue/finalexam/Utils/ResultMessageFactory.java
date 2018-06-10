package com.voidblue.finalexam.Utils;

public class ResultMessageFactory {
    public static ResultMessage accept(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    public static ResultMessage notLogined(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(404);
        resultMessage.setMessage("로그인이 되어있지 않습니다.");
        return resultMessage;
    }

    public static ResultMessage noAuthority(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(404);
        resultMessage.setMessage("접근 권한이 없습니다.");
        return resultMessage;
    }

    public static ResultMessage isEmpty(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(404);
        resultMessage.setMessage("요청하신 자원이 없습니다.");
        return resultMessage;
    }
}
