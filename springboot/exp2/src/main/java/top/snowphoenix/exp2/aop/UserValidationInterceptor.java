package top.snowphoenix.exp2.aop;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.snowphoenix.exp2.auth.CurrentUserInfo;
import top.snowphoenix.exp2.auth.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
public class UserValidationInterceptor implements HandlerInterceptor {

    /***
     *
     * @param handlerMethod method info
     * @return null if no {@link Role} are required, empty if any {@link Role} is ok, or the required {@link Role}s.
     */
    private HashSet<Role> getRequiredRoles(HandlerMethod handlerMethod) {
        HashSet<Role> ret;
        RequireAuthWithRole methodAnnotation = handlerMethod.getMethod().getAnnotation(RequireAuthWithRole.class);
        if (methodAnnotation != null) {
            return new HashSet<Role>(Arrays.asList(methodAnnotation.value()));
        }
        RequireAuthWithRole classAnnotation = handlerMethod.getBeanType().getAnnotation(RequireAuthWithRole.class);
        if (classAnnotation == null) {
            return null;
        }
        return new HashSet<Role>(Arrays.asList(classAnnotation.value()));
    }

    private void setUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        /*
         * TODO
         * add `WWW-Authenticate` header
         * see:
         * 1. https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status/401
         * 2. https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/WWW-Authenticate
         */
    }

    private CurrentUserInfo buildCurrentUserFromToken(String token) {
        JSONObject json = JSON.parseObject(token);
        int uid = json.getInteger("uid");
        var rolesJson = json.getJSONArray("roles");
        var roles = new Role[rolesJson.size()];
        for (int i = 0; i < roles.length; i++) {
            roles[i] = Role.parse(rolesJson.getString(i));
        }
        return CurrentUserInfo.builder().uid(uid).roles(roles).build();
    }

    private boolean hasAuth(Role[] userRoles, HashSet<Role> requiredRoles) {
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }
        for (Role userRole : userRoles) {
            if (requiredRoles.contains(userRole)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        var requiredRoles = getRequiredRoles(handlerMethod);
        if (requiredRoles == null) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null) {
            setUnauthorized(response);
            return false;
        }
        CurrentUserInfo userInfo;
        try {
           userInfo = buildCurrentUserFromToken(token);
        }
        catch (Exception e) {
            setUnauthorized(response);
            log.warn("build userInfo from token \"" + token + "\" fail: ", e);
            return false;
        }
        if (!hasAuth(userInfo.getRoles(), requiredRoles)) {
            setUnauthorized(response);
            return false;
        }
        request.setAttribute(CurrentUser.class.getSimpleName(), userInfo);
        return true;
    }
}
