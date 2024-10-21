package com.jobs;

import com.jobs.layers.Entities.tokens;
import com.jobs.layers.Repositories.token_repo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Component
@EnableScheduling
public class helper {


    @Autowired
    private ServerProperties serverProp;
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private  Environment env;


    @Autowired
    private token_repo loginTokenRepo;
private static final int TOKEN_EXIRATTION_TIME = 10;
private Logger logger = LoggerFactory.getLogger(this.getClass());
    public  String sendEmail(String toEmail, String subject, String body) {
        try {
            logger.info("preparing to send verification email to {}",toEmail);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("${spring.mail.username}");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            sender.send(message);
            logger.info("verification email sent to {}",toEmail);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("verification email transmission failed for email {}",toEmail);
        }
        return "Sent!!";
    }



    public  String generateSource(HttpServletRequest request ) throws UnknownHostException {
        String arr[] = env.getActiveProfiles();
        String activeProfile = arr[0];
        if(activeProfile.equals("dev")){
            return InetAddress.getLocalHost().getHostAddress();
        }
        return request.getServerName();

    }
    public String getNewUUID(){
        return UUID.randomUUID().toString();
    }
    public String generateHosturl(HttpServletRequest request) throws UnknownHostException {
        return "http://" + generateSource(request)+ ":" + request.getServerPort();
    }
    public String generateUrl(HttpServletRequest request) throws UnknownHostException {

        return   "http://" + generateSource(request) + ":" + request.getServerPort() + request.getContextPath();
    }





    public tokens increaseExpirationTimeOfLoginTokens(tokens loginToken){
        loginToken.expiry = calculateExpirationTime();
        return loginToken;
    }
    public boolean isTokenExpired(tokens loginToken){
        Calendar cal = Calendar.getInstance();
       return loginToken.expiry.getTime() - cal.getTime().getTime() <= 0;
    }
    private static Date calculateExpirationTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, TOKEN_EXIRATTION_TIME);
        return new Date(cal.getTime().getTime());
    }

 //   @Scheduled(fixedRate = 1000) //enable in prod or dev.
    public void deleteExpiredLoginTokens(){
    List<tokens> loginTokenList = loginTokenRepo.findAll();
        Calendar cal = Calendar.getInstance();
        logger.info("removing expired login tokens");
    for(tokens curToken : loginTokenList){
        if(curToken.expiry.getTime() - cal.getTime().getTime() <= 0){
            loginTokenRepo.delete(curToken);
        }
    }


    }

}
