/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public enum Gender {
    UNKNOWN(0), NAM(1), NU(2);
    
    private int value;
    
    private Gender(int t) {
        value = t;
    }
    
    public int getValue() {
        return value;
    }

    private static final Map<Integer, Gender> intToTypeMap = new HashMap<Integer, Gender>();

    static {
        for (Gender activeState: Gender.values()) {
            intToTypeMap.put(activeState.getValue(), activeState);
        }
    }

    public static Gender fromInt(int i) {
        Gender type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return Gender.UNKNOWN;
        return type;
    }

    public static Gender fromString(String s) {
        try {
            int i = Integer.parseInt(s);
            return fromInt(i);
        } catch (NumberFormatException ex) {
            return Gender.UNKNOWN;
        }
    }
}
