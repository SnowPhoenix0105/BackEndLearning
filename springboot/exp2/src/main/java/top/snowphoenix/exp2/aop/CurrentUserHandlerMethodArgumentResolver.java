package top.snowphoenix.exp2.aop;

import lombok.var;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.snowphoenix.exp2.auth.CurrentUserInfo;

public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        var userInfo = (CurrentUserInfo) nativeWebRequest.getAttribute(CurrentUser.class.getSimpleName(), 0);
        if (userInfo == null) {
            throw new RuntimeException(
                    "You can only get CurrentUserInfo by @" + CurrentUser.class.getName() +
                    " when the method is marked with @" + RequireAuthWithRole.class.getName());
        }
        return userInfo;
    }
}
