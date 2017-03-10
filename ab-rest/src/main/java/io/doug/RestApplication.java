package io.doug;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by djeremias on 1/3/17.
 */
@Import(DomainApplication.class)
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
