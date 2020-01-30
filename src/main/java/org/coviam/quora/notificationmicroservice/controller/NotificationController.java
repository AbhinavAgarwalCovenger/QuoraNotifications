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
        notificationService.newQues(askerResponseDTO, questionDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/questionApproval")
    public ResponseEntity<HttpStatus> questionApproval(@RequestBody QuestionDTO questionDTO){
        if(questionDTO.getIsApproved()) {
            final String uri = "";
            restTemplate = new RestTemplate();
            AskerResponseDTO askerResponseDTO = restTemplate.getForObject(uri, AskerResponseDTO.class, questionDTO);
            notificationService.questionApproved(askerResponseDTO,questionDTO);
        }
        else {
            notificationService.questionRejected(questionDTO);
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/answer")
    public ResponseEntity<HttpStatus> newAnswer(@RequestBody AnswerDTO answerDTO){

        final String uri = "";
        restTemplate = new RestTemplate();
        AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri,AnswerResponseDTO.class,answerDTO);
        notificationService.newAns(answerResponseDTO, answerDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/answerApproval")
    public ResponseEntity<HttpStatus> questionApproval(@RequestBody AnswerDTO answerDTO){
        if(answerDTO.getIsApproved()) {
            final String uri = "";
            restTemplate = new RestTemplate();
            AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri, AnswerResponseDTO.class, answerDTO);
            notificationService.answerApproved(answerResponseDTO, answerDTO);
        }
        else {
            notificationService.answerRejected(answerDTO);
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<HttpStatus> newComment(@RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reaction")
    public ResponseEntity<HttpStatus> getReaction(@RequestBody ReactionDTO reactionDTO){

        notificationService.newReaction(reactionDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/levelUp")
    public ResponseEntity<HttpStatus> leveledUp(@RequestBody LevelUpDTO levelUpDTO){

        notificationService.levelUp(levelUpDTO);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/followRequest")
    public ResponseEntity<HttpStatus> followRequest(@RequestBody FollowDTO followDTO){

        notificationService.followRequested(followDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/followApproval")
    public ResponseEntity<HttpStatus> followApproval(@RequestBody FollowDTO followDTO){

        if (followDTO.getIsApproved()){
            notificationService.followRequestAccepted(followDTO);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
