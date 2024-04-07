package tech.aomi.common.utils.json;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class JsonTest {


    @Test
    public void test() {
        var d = new A();
        d.setInstant(Instant.now());

        var s = Json.toJson(d).toString();
        System.out.println(s);

        var newD = Json.fromJson(s, A.class);
        System.out.println(newD);
    }

    @Getter
    @Setter
    public static class A {

        private Instant instant;

    }

}
