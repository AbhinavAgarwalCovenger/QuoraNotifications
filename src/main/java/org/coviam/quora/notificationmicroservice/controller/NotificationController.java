package org.coviam.quora.notificationmicroservice.controller;

import org.coviam.quora.notificationmicroservice.dto.*;
import org.coviam.quora.notificationmicroservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    RestTemplate restTemplate;

    @GetMapping("/question")
    public ResponseEntity<HttpStatus> newQuestion(@RequestBody QuestionDTO questionDTO){
        final String uri="";
        restTemplate=new RestTemplate();
        AskerResponseDTO askerResponseDTO=restTemplate.getForObject(uri,AskerResponseDTO.class,questionDTO);
        notificationService.newQues(askerResponseDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/questionApproval")
    public ResponseEntity<HttpStatus> questionApproval(@RequestBody QuestionDTO questionDTO){
        if(questionDTO.getIsApproved()) {
            final String uri = "";
            restTemplate = new RestTemplate();
            AskerResponseDTO askerResponseDTO = restTemplate.getForObject(uri, AskerResponseDTO.class, questionDTO);
            notificationService.questionApproved(askerResponseDTO);
        }
        else {
            notificationService.questionRejected(questionDTO.getTaggedProfileName());
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/answer")
    public ResponseEntity<HttpStatus> newAnswer(@RequestBody AnswerDTO answerDTO){

        final String uri = "";
        restTemplate = new RestTemplate();
        AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri,AnswerResponseDTO.class,answerDTO);
        notificationService.newAns(answerResponseDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/answerApproval")
    public ResponseEntity<HttpStatus> questionApproval(@RequestBody AnswerDTO answerDTO){
        if(answerDTO.getIsApproved()) {
            final String uri = "";
            restTemplate = new RestTemplate();
            AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri, AnswerResponseDTO.class, answerDTO);
            notificationService.answerApproved(answerResponseDTO);
        }
        else {
            notificationService.answerRejected(answerDTO.getTaggedProfileName());
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<HttpStatus> newComment(@RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reaction")
    public ResponseEntity<HttpStatus> getReaction(@RequestBody ReactionDTO reactionDTO){

        notificationService.newreaction(reactionDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }


}
