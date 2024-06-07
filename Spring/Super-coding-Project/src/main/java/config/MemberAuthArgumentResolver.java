package config;

import dto.AuthInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import service.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
@RequiredArgsConstructor
public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthInfo.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("UnautorizedException");
        }
        String token = authorization.substring(7);
        Map<String, Long> decodedToken = jwtService.decode(token);
        if (decodedToken == null) {
            throw new RuntimeException("UnautorizedException");
        }
        Long memberId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_ID);
        if (memberId == null) {
            throw new RuntimeException("UnauthorizedException");
        }
        return AuthInfo.of(memberId);
    }
}
