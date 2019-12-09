package com;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvController {
  @RequestMapping(value = "/convert")
  public String yolo(Model model) {
    return "yolo";
  }
}
