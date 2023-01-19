package service;

import entity.Topic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopicServiceTest {
    private TopicService topicService;
    @Before
    public void setUp(){
        topicService = new TopicService();
    }

    @Test
    public void should_register_topic(){
        try {
            topicService.add(new Topic("Typescript maniacs"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertTrue(topicService.contains(new Topic("Typescript maniacs")));
    }

    @Test(expected = Exception.class)
    public void should_not_register_topics_with_same_name(){
        try {
            topicService.add(new Topic("Scrumcast!"));
            topicService.add(new Topic("Scrumcast!"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}