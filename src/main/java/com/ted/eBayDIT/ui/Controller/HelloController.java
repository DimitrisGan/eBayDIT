package com.ted.eBayDIT.ui.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping({ "/hello" })

    public String firstPage() {

        return "Hello World";

    }



}


