package com.nikitades.carres.entrypoint.controller.info;

import com.nikitades.carres.entrypoint.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

  @Operation(
    operationId = "user.profile.get",
    tags = { "Profile" },
    description = "Get user profile info.",
    responses = {
      @ApiResponse(
        responseCode = "200",
        content = { @Content(schema = @Schema(implementation = ProfileInfoDTO.class)) }
      ),
      @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = { @Content(schema = @Schema(implementation = ApiError.class)) }
      ),
    }
  )
  @GetMapping("/api/v1/info")
  public ProfileInfoDTO getInfo(JwtAuthenticationToken principal) {
    return new ProfileInfoDTO(
      principal.getToken().getClaim("name"),
      principal.getToken().getClaim("email")
    );
  }
}
