package com.cs.basket.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ahuja on 5/5/2017.
 */
@Configuration
@ComponentScan("com.cs.basket")
public class BasketApplicationConfig {
    //NOTE In a typical application this class is used to declare beans to load properties, database connections, etc
}
