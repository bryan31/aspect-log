package com.yomahub.aspectlog.main;

import com.yomahub.aspectlog.convert.AspectLogConvert;
import com.yomahub.aspectlog.vo.Person;

public class CustomAspectLogConvert implements AspectLogConvert {
    @Override
    public String convert(Object[] args) {
        Person person = (Person)args[0];
        return "PERSON(" + person.getId() + ")";
    }
}
