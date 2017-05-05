package com.cs.basket.app;

import com.cs.basket.exception.UnrecognisedItemException;
import com.cs.basket.service.BasketService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * Created by ahuja on 25/03/2016.
 */
public class BasketApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: BasketApplication item [item] [item]");
            System.exit(-1);
        }

        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(BasketApplicationConfig.class);
            BasketService basketService = context.getBean(BasketService.class);
            long totalPrice = basketService.priceBasket(Arrays.asList(args));
            System.out.println(format("Total price of the basket is %s pence", totalPrice));
        } catch (UnrecognisedItemException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Internal Error!");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
