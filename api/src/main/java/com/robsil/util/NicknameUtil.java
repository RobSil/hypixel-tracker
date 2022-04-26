package com.robsil.util;

import org.springframework.stereotype.Service;

@Service
public class NicknameUtil {

    public String trimNickname(String nickname) {
        return nickname.replace("-", "");
    }
}
