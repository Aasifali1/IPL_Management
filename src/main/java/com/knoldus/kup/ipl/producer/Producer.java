package com.knoldus.kup.ipl.producer;

import com.google.gson.Gson;
import com.knoldus.kup.ipl.models.Match;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
//public class Producer {
//    private static final String TOPIC = "test_topic_kafka";
//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;
//
//    public void sendMessage(String message){
//
//        this.kafkaTemplate.send(TOPIC,message);
//    }
//
//    @Bean
//    public NewTopic createTopic(){
//
//        return new NewTopic(TOPIC,3,(short) 1);
//    }
//
//
//
//}

@Service
public class Producer {
    @Autowired
    MatchResult matchResult;
    private static final String TOPIC = "quickstart-events";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Match match){
        matchResult.setResult(match.getResult());
        matchResult.setId(match.getId());
        matchResult.setTeam1(match.getTeam1().getName());
        matchResult.setTeam1score(match.getTeam1Score());
                matchResult.setTeam2(match.getTeam2().getName());
        matchResult.setTeam2score(match.getTeam2Score());
        this.kafkaTemplate.send(TOPIC, new Gson().toJson(matchResult));
    }
}