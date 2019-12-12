package com;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class ReqBody {
  public String a;
  public String b;
  public double amount;
}

@RestController
public class ConvController {
  @Autowired
  private JdbcTemplate jdbc_template_;
  @Transactional
  @RequestMapping(value = "/convert", method = RequestMethod.POST)
  public double Convert(Model model, @RequestBody ReqBody req) {
    String QUERY = "SELECT ratio FROM Currencies.Currencies WHERE name = ?";
    double ratio_a = jdbc_template_.queryForObject(QUERY, Double.class, req.a);
    double ratio_b = jdbc_template_.queryForObject(QUERY, Double.class, req.b);
    return (req.amount * ratio_b) / ratio_a;
  } 
}
