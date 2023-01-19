package entity;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TopicTest {

    @Test
    public void suscribe_user_to_topic(){

        Topic topic = new Topic("Platzi talks");

        topic.suscribeUser(new User("matias", "password"));

        assertEquals(
                topic.getSuscribers(),
                List.of(new User("matias", "password"))
        );
    }

    @Test
    public void unsuscribe_user_to_topic(){

        Topic topic = new Topic("Platzi podcast");

        topic.suscribeUser(new User("matias", "password"));

        assertEquals(
                List.of(new User("matias", "password"))
                , topic.getSuscribers());

        topic.unsuscribeUser(new User("matias", "password"));

        assertEquals(List.of(), topic.getSuscribers());
    }
}