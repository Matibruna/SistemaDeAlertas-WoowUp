package alert.managment;

import entity.Topic;
import entity.User;
import entity.alert.Alert;
import entity.alert.AlertBody;
import entity.alert.AlertType;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SingleUserAlertManagerTest {


    @Test
    public void should_suscribe_single_user(){
        IAlertManager alertManager = new SingleUserAlertManager();

        User user1 = new User("test", "password");
        User user2 = new User("test1", "password");
        User user3 = new User("test2", "password");

        // Se deberia enviar la notificacion unicamente a un usuario. (El ultimo suscrito)
        alertManager.suscribe(user1);
        alertManager.suscribe(user2);
        alertManager.suscribe(user3);


        assertEquals(
                alertManager.getSuscribers(),
                List.of(new User("test2", "password")));

    }

    @Test
    public void should_suscribe_and_unsuscribe_user(){

        IAlertManager alertManager = new SingleUserAlertManager();

        alertManager.suscribe(new User("matias", "password"));

        alertManager.unsuscribe(new User("matias", "password"));

        assertEquals(
                List.of(),
                alertManager.getSuscribers()
        );
    }

    @Test
    public void should_notify_single_user(){
        IAlertManager alertManager = new SingleUserAlertManager();

        Topic topic = Mockito.mock(Topic.class);
        LocalDateTime expirationDate = LocalDateTime.of(2023, 4, 20, 9, 30);
        AlertBody alertBody = new AlertBody("Esta es una alerta predeterminada, mira nuestras ofertas!", topic, AlertType.INFORMATIVA, LocalDateTime.now(), true, expirationDate);

        User user1 = new User("test", "password");
        User user2 = new User("test1", "password");

        // Se deberia enviar la notificacion unicamente a un usuario. (El ultimo suscrito)
        alertManager.suscribe(user1);
        alertManager.suscribe(user2);

        alertManager.notify(alertBody);

        assertEquals(
                user1.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList()),
                List.of() /* Se espera una lista vacia */ );

        assertEquals(
                user2.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList()),
                List.of(alertBody) /* Se espera una lista con una alerta */);
    }


}