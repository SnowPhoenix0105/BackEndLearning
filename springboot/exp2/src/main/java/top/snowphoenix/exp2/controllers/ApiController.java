package top.snowphoenix.exp2.controllers;

import com.alibaba.fastjson.JSONObject;
import lombok.var;
import org.springframework.web.bind.annotation.*;
import top.snowphoenix.exp2.aop.CurrentUser;
import top.snowphoenix.exp2.aop.RequireAuthWithRole;
import top.snowphoenix.exp2.auth.CurrentUserInfo;
import top.snowphoenix.exp2.auth.Role;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequireAuthWithRole()
    @GetMapping("/echo/{content}")
    public String echoGet(@CurrentUser CurrentUserInfo user, @PathVariable String content) {
        var json = new JSONObject();
        json.put("user", user);
        json.put("meg", content);
        return json.toJSONString();
    }

    @RequireAuthWithRole({Role.ADMIN})
    @PostMapping("/echo")
    public String echoPost(@CurrentUser CurrentUserInfo user, @RequestBody String content) {
        var json = new JSONObject();
        json.put("user", user);
        json.put("meg", content);
        return json.toJSONString();
    }
}
