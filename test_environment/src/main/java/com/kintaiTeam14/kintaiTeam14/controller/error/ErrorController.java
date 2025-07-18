package com.kintaiTeam14.kintaiTeam14.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/403")
    public String error403() {
        return "error/403"; // templates/error/403.html を返す
    }
}