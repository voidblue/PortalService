package com.voidblue.finalexam.Utils;

public class ResultMessageFactory {
    public static ResultMessage get200(){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }
}
