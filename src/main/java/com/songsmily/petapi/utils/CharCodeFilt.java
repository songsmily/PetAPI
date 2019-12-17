package com.songsmily.petapi.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CharCodeFilt {
    /**
     判断字符串是否含有Emoji表情
     **/
    public boolean isHasEmoji(String str) {
        Pattern pattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
