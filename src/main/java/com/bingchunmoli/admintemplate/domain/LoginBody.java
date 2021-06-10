package com.bingchunmoli.admintemplate.domain;

import lombok.Data;

/**
 * @author BingChunMoLi
 */
@Data
public class LoginBody {

    private String username;
    private String password;
    private String code;
    private String uuid;

}
