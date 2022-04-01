package myhouse;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationFileRepository;
import myhouse.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {

    public static void main(String[] args) throws DataAccessException {

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);

        controller.mainMenu();

    }
}
