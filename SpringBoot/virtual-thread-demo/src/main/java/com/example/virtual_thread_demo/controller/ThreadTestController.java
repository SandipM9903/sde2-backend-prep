package com.example.virtual_thread_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ThreadTestController {

    @GetMapping("/normal")
    public String normalThread() throws Exception {

        Thread.sleep(2000);

        return "Completed by: "
                + Thread.currentThread();
    }

    @GetMapping("/virtual")
    public String virtualThread() throws Exception {

        Thread.sleep(2000);

        return "Completed by: "
                + Thread.currentThread();
    }
}
