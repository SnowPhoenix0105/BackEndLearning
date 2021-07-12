package top.snowphoenix.exp1.pojo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class Proto {
    private static int count = 0;

    private int id;

    public Proto() {
        id = count++;
        System.out.println("Proto #" + id + " created");
    }
}
