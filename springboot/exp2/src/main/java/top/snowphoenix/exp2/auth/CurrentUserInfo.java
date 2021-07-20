package top.snowphoenix.exp2.auth;

import lombok.*;

@Getter
@ToString
@Builder
public class CurrentUserInfo {
    private final Role[] roles;
    private final int uid;
}
