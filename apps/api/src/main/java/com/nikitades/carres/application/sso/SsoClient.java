package com.nikitades.carres.application.sso;

import com.nikitades.carres.application.sso.dto.UserDto;
import java.util.Optional;

public interface SsoClient {
  public Optional<UserDto> findUserByEmail(String email);
}
