package com.thebeastshop.aspectlog.main;

import com.thebeastshop.aspectlog.convert.AspectLogConvert;
import com.thebeastshop.aspectlog.vo.Person;

public class CustomAspectLogConvert implements AspectLogConvert {
    @Override
    public String convert(Object[] args) {
        Person person = (Person)args[0];
        return "PERSON(" + person.getId() + ")";
    }
}
