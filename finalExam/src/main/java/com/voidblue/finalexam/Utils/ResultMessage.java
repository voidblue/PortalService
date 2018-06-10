package com.voidblue.finalexam.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode
public class ResultMessage {
    private Integer resultCode;
    private String message;

}
