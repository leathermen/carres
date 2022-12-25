package com.nikitades.carres.entrypoint.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentDateController {

  @GetMapping("/open/date")
  public String getCurrentDate() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss");
    return simpleDateFormat.format(new Date());
  }
}
