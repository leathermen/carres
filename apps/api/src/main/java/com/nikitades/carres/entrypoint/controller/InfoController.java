package com.nikitades.carres.entrypoint.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class InfoController {

  @GetMapping("/api/v1/info")
  public Mono<Principal> getInfo(Principal principal) {
    return Mono.just(principal);
  }
}
