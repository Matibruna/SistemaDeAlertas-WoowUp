package service;

import entity.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicService {

    private final List<Topic> topics;

    public TopicService() {
        topics = new ArrayList<>();
    }

    public void add(Topic topic) throws Exception {
        if(findByTopicName(topic.getName()) != null){
            throw new Exception();
        }
        topics.add(topic);
    }

    public Topic findByTopicName(String name){

        for (Topic topic : topics) {
            if(topic.getName().equals(name)){
                return topic;
            }
        }

        return null;
    }

    public boolean contains(Topic topic){
        return topics.contains(topic);
    }
}
