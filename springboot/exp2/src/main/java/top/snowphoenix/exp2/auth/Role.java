package top.snowphoenix.exp2.auth;

import java.util.HashMap;

public enum Role {
    USER,
    ADMIN
    ;

    private static final HashMap<String, Role> strToRole = new HashMap<String, Role>() {{
        put("user", Role.USER);
        put("admin", Role.ADMIN);
    }};

    public static Role parse(String role) {
        return strToRole.get(role.toLowerCase());
    }
}
