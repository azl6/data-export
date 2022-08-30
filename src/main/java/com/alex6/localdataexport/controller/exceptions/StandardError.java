package com.alex6.localdataexport.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardError {

    private Long timestamp;
    private Integer status;
    private String msg;

}
