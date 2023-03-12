package com.nikitades.carres.entrypoint.mock.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * For some reason, modern spring supports Openid Connect resource server mode in a quite poor way.
 * So this small extension mocks the incoming JWT token in a way that we now can control which user is supposed
 * to be the author of every request.
 * 
 * We can even leverage various scenarios based on different users' JWT claims, such as roles, email verification status,
 * age or anything else.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = MockJwtSecurityContextFactory.class)
public @interface WithMockJwt {
  MockJwt value();
}
