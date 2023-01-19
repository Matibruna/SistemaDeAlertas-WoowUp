package entity.alert;

import entity.Topic;
import entity.User;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AlertTest {

    @Test
    public void alert_is_expired(){

        Topic topic = Mockito.mock(Topic.class);

        AlertBody alertBody = new AlertBody("Tu suscripcion esta a punto de acabar! Renuevala en los proximos 2 dias para tener un descuento.", topic, AlertType.URGENTE, LocalDateTime.now(), true, LocalDateTime.MIN);

        Alert alert = new Alert(new User("username", "password"), alertBody, false);

        assertTrue(alert.isExpired());
    }

    @Test
    public void alert_is_not_expired(){

        Topic topic = Mockito.mock(Topic.class);

        AlertBody alertBody = new AlertBody("Tu suscripcion esta a punto de acabar! Renuevala en los proximos 2 dias para tener un descuento.", topic, AlertType.URGENTE, LocalDateTime.now(), true, LocalDateTime.MAX);

        Alert alert = new Alert(new User("username", "password"), alertBody, false);

        assertFalse(alert.isExpired());
    }
}