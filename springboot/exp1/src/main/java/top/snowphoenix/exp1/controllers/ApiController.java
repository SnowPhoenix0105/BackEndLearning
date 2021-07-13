package top.snowphoenix.exp1.controllers;

import lombok.var;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.snowphoenix.exp1.pojo.Proto;
import top.snowphoenix.exp1.pojo.Req;

@RestController
@RequestMapping("/api")
//@Scope("request")
public abstract class ApiController {
    private static int count = 0;
    private final Proto proto;
//    private final Req req;

    public ApiController(Proto proto) {
        this.proto = proto;
        System.out.println("ApiController #" + count + " created");
        count++;
    }

//    public ApiController(Proto proto, Req req) {
//        this.proto = proto;
//        this.req = req;
//        System.out.println("ApiController #" + count + " created");
//        count++;
//    }

//    @GetMapping("/test")
//    public String test() {
//        var msg = "proto=" + proto + ", req=" + req;
////        var msg = "proto=" + proto;
//        System.out.println(msg);
//        return msg;
//    }

    @GetMapping("/req")
    public String test(@ModelAttribute Req req) {
        var msg = "proto=" + proto + ", req=" + req;
        System.out.println(msg);
        return msg;
    }

    @GetMapping("/lookupReq")
    public String test() {
        var req = getReq();
        var msg = "proto=" + proto + ", req=" + req;
        System.out.println(msg);
        return msg;
    }

    @Lookup
    protected abstract Req getReq();
}
