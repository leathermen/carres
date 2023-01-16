package com.nikitades.carres.entrypoint.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

  @GetMapping("/api/v1/info")
  public Principal getInfo(Principal principal) {
    return principal;
  }
}
