package top.snowphoenix.exp1.pojo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@Data
public class Req {
    private static int count = 0;

    private int id;

    public Req() {
        id = count++;
        System.out.println("Req #" + id + " created");
    }
}
