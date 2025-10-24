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
public enum Role {
    BENHNHAN(0), LETAN(1), BACSI(2), ADMIN(3);
    
    private int value;
    
    private Role(int t) {
        value = t;
    }
    
    public int getValue() {
        return value;
    }
    
    private static final Map<Integer, Role> intToTypeMap = new HashMap<Integer, Role>();

    static {
        for (Role activeState: Role.values()) {
            intToTypeMap.put(activeState.getValue(), activeState);
        }
    }

    public static Role fromInt(int i) {
    	Role type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null)
            return Role.BENHNHAN;
        return type;
    }

    public static Role fromString(String s) {
        try {
            int i = Integer.parseInt(s);
            return fromInt(i);
        } catch (NumberFormatException ex) {
            return Role.BENHNHAN;
        }
    }
}
