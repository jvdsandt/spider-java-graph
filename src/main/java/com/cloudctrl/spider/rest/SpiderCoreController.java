package com.cloudctrl.spider.rest;

import java.util.List;

import com.cloudctrl.spider.Method;
import com.cloudctrl.spider.MethodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpiderCoreController {

    @Autowired
    private MethodsRepo methodsRepo;

    @GetMapping("/method/{id}")
    public Method getMethodById(@PathVariable String id) {
        return methodsRepo.findById(Long.parseLong(id));
    }

    @GetMapping("/methods")
    public List<Method> getMethodsLike(@RequestParam(value="selector") String selector,
                                       @RequestParam(value="limit", defaultValue = "10") int limit,
                                       @RequestParam(value="offset", defaultValue = "0") int offset) {
         return methodsRepo.findAllLike(selector + "%", limit, offset);
    }
}
