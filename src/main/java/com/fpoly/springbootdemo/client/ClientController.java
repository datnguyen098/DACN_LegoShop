package com.fpoly.springbootdemo.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LegoShop")
public class ClientController {
    @GetMapping("")
    public String indexClient(Model model){
        model.addAttribute("content", "viewClient/indexClient.html");
        return "viewClient/BaseClient";
    }
}
