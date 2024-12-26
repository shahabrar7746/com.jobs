package com.jobs.layers.Controllers;

import com.jobs.helper;
import com.jobs.layers.DTOs.*;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Entities.employee;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Entities.jobs;
import com.jobs.layers.Entities.user;
import com.jobs.layers.Events.ChangePasswordEvent;
import com.jobs.layers.Events.RegistrationCompleteEvent;
import com.jobs.layers.Responses.Response;
import com.jobs.layers.Services.Authentication;
import com.jobs.layers.Services.JobsService;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class RegistrationController {
@Autowired
private helper help;



@Autowired
private Authentication auth;

@Autowired
private ApplicationEventPublisher publisher;
    @Autowired
    private  Environment env;

private  Logger logger = LoggerFactory.getLogger(this.getClass());

@GetMapping("/")
public ResponseEntity<String> isAlive(final HttpServletRequest request) throws IOException {
    logger.info("Checking if the Server is alive.... ");

    logger.info("Request address : {}", request.getRemoteAddr());



    return ResponseEntity.ok(InetAddress.getLocalHost().getHostAddress());
}



    @PostMapping("/{userType}/register")
    public ResponseEntity<String> register(@PathVariable("userType") String userType, @RequestBody userModel requestModel, final HttpServletRequest request) throws InvalidUserTypeException, EmailInUseException, UnknownHostException {

       user newUser = auth.register(userType, requestModel).data;
       newUser.type = userType;
        publisher.publishEvent(new RegistrationCompleteEvent(newUser, help.generateUrl(request)));
        return ResponseEntity.ok(help.generateHosturl(request) + "/" + userType + "/" + newUser.email + "/CompleteRegistration");
    }


    @GetMapping("/{userType}/verifyRegistration")
    public ResponseEntity<Response<String>> verifyTokken(@RequestParam("token") String token, @PathVariable("userType") String userType) throws TOKEN_EXPIRED, InvalidUserTypeException {
        return auth.verifyToken(token,userType);
    }



    @GetMapping("/resendVerificationToken")
    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String token, final  HttpServletRequest request) throws TOKEN_EXPIRED, UnknownHostException {
    publisher.publishEvent(new RegistrationCompleteEvent( auth.resendVerificationToken(token).data, help.generateUrl(request)));
          return ResponseEntity.ok("Resent sucessfull");
}

@GetMapping("/forgotPassword")
    public ResponseEntity<String>  forgotPassword(@RequestParam("email") String email, final HttpServletRequest request) throws EmailNotFoundException, UnknownHostException {

  user curUser =   auth.forgotPassword(email).data;
  String token = UUID.randomUUID().toString();
String url = generateResetPasswordUrl(request) + token;
logger.info("Requested password change for {} with Password reset token {}",curUser.email,token);
publisher.publishEvent(new ChangePasswordEvent(curUser, url, token));
    return ResponseEntity.ok(url);
}
@PostMapping(path = "/changePassword")
public ResponseEntity<Response<String>>  changeOldPassword(@RequestBody ChangePasswordBody passwordBody) throws EmailNotFoundException, InvalidPasswordException {
    return auth.changeCurrentPassword(passwordBody);
}

@PostMapping("/savePassword")
public ResponseEntity<Response<String>> changePassword(@RequestParam("token") String token, @RequestBody NewPasswordBody newPass) throws TOKEN_EXPIRED {
   return auth.changePassword(token,newPass);
}
@PostMapping("/company/{email}/CompleteRegistration")
public ResponseEntity<String> completeResgistrationForCompanies(@PathVariable("email") String email,final HttpServletRequest request, @RequestBody CompanyReceptionTemplate comp) throws EmailNotFoundException, UnAuthenticatedAccountAccessException, UnknownHostException {
    return ResponseEntity.ok(help.generateHosturl(request)+auth.completeRegistrationForComapnies(email,comp));
}

@PostMapping("/employee/{email}/CompleteRegistration")
public ResponseEntity<String> completeRegistrationForEmployee(@PathVariable("email") String email, @RequestBody EmployeeReceptionTemplate empl, final HttpServletRequest request) throws EmailNotFoundException, UnAuthenticatedAccountAccessException, UnknownHostException {
    return ResponseEntity.ok(  help.generateHosturl(request) + auth.completeRegistrationForEmployee(email,empl));
}

@GetMapping("/employee/login")
public ResponseEntity<Response<String>> loginEmployee(@RequestBody Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException{
    return auth.loginEmployee(credentials);
}
@GetMapping("/company/login")
public ResponseEntity<Response<String>> loginCompany(@RequestBody Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException{
    return auth.loginCompany(credentials);
}

private  String generateResetPasswordUrl(HttpServletRequest request) throws UnknownHostException {
    String url = "http://" + help.generateSource(request) + ":" + request.getServerPort()  + "/savePassword?token=";
    return url;
}
    
    
    



}
