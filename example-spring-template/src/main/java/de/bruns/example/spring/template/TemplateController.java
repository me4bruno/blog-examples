package de.bruns.example.spring.template;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {

   @RequestMapping("/jsp")
   public ModelAndView jsp() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("version", "2.0");
      return new ModelAndView("jsp", variables);
   }

   @RequestMapping("/velocity")
   public ModelAndView velocity() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("version", "1.7");
      return new ModelAndView("velocity", variables);
   }

   @RequestMapping("/freemarker")
   public ModelAndView freemarker() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("version", "2.3.16");
      return new ModelAndView("freemarker", variables);
   }

   @RequestMapping("/jade")
   public ModelAndView jade() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("version", "0.2.14");
      return new ModelAndView("jade", variables);
   }

}