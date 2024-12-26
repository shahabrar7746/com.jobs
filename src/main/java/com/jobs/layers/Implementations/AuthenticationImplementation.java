package com.jobs.layers.Implementations;
import com.jobs.layers.DTOs.*;
import com.jobs.helper;
import com.jobs.layers.Entities.*;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Messages.TaskCategories;
import com.jobs.layers.Messages.Tasks;
import com.jobs.layers.Repositories.*;
import com.jobs.layers.Responses.Response;
import com.jobs.layers.Services.Authentication;
import com.jobs.layers.Messages.StringMessages;
import com.jobs.layers.Services.CompanyService;
import com.jobs.layers.Services.EmployeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobs.layers.Exceptions.InvalidUserTypeException;

import java.net.http.HttpResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationImplementation implements Authentication {

@Autowired
private verification_token_repo tokenRepo;

@Autowired
private token_repo loginTokenRepo;
@Autowired
private CompanyService comService;
@Autowired
private reset_password_tokens_repo ResetPasswordRepo;
@Autowired
private helper help;
    @Autowired
    private user_repo userRepo;

    @Autowired
    private EmployeService employeeService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ApplicationEventPublisher publisher;

private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Response<com.jobs.layers.Entities.user> register(String userType, userModel requestModel) throws InvalidUserTypeException,EmailInUseException{

        if(!userType.equalsIgnoreCase("employee") && !userType.equalsIgnoreCase("company") ){
            throw new InvalidUserTypeException(HttpStatus.BAD_REQUEST,StringMessages.INVALID_USER);
        }

        user curUser = userRepo.findByEmail(requestModel.email);
        if(curUser != null){
            throw new EmailInUseException(HttpStatus.CONFLICT,StringMessages.EMAIL_IN_USE);
        }
        user newUser = new user();
        try {


            newUser.email = requestModel.email;
            newUser.first_name = requestModel.first_name;
            newUser.last_name = requestModel.last_name;
            newUser.encrypted_password = encoder.encode(requestModel.password);


            newUser.createdAt = new Date();
            userRepo.save(newUser);
            logger.info("issuing verification token for {}", newUser.email);
        }catch (Exception e){
            logger.error("An expected error occured",e);
        }
        Response<user> response = new Response<>(newUser, HttpStatus.ACCEPTED.value());
 return response;
    }

    @Override
    public void saveVerificationTokenForUser(user curUser, String token) {
        verificationTokens tokenObject = new verificationTokens(curUser,token);
        tokenRepo.save(tokenObject);


    }

    @Override
    public ResponseEntity<Response<String>> verifyToken(String token,String userType) throws TOKEN_EXPIRED, InvalidUserTypeException {
        if(!userType.equals("employee") && !userType.equals("company") ){
            throw new InvalidUserTypeException(HttpStatus.BAD_REQUEST,StringMessages.INVALID_USER);
        }
            verificationTokens tokenObject = tokenRepo.findByToken(token);
            if (tokenObject == null) {
                logger.info("invalid token verification attempt token : {}", token);
                throw new TOKEN_EXPIRED(HttpStatus.NOT_FOUND, StringMessages.TOKEN_EXPIRED);
            }
            user curUser = tokenObject.User;
            Calendar cal = Calendar.getInstance();
            if ((tokenObject.expirationTime.getTime() - cal.getTime().getTime() <= 0)) {
                logger.info("expired token verification attempt token : {}", token);
                logger.info("removing tokken {} from database.", token);
                tokenRepo.deleteById(tokenObject.id);
                throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);

            }
            curUser.status = "AUTHENTICATED";

            userRepo.save(curUser);
            tokenRepo.deleteById(tokenObject.id);
            logger.info("user {} verified through token {}", curUser.email, token);
         logger.info("removed verified token {} from database",token);
          logger.info("{} : {}",TaskCategories.COMPLETED_TASK, Tasks.COMPLETE_REGITRATION_TASK);



          Response<String> response = new Response<>("Verified", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public Response<user> resendVerificationToken(String token) throws TOKEN_EXPIRED{
        verificationTokens oldToken = tokenRepo.findByToken(token);
        if(oldToken == null){
            throw  new TOKEN_EXPIRED(HttpStatus.NOT_FOUND,StringMessages.TOKEN_EXPIRED);
        }
        user curUser = oldToken.User;

        try {
          tokenRepo.deleteById(oldToken.id);

        }catch (Exception e){
            logger.error("An unexpected error occured", e);
        }
Response<user> response = new Response<>(curUser, HttpStatus.OK.value());
        return response;
    }

    @Override
    public Response<user> forgotPassword(String email) throws EmailNotFoundException {
       user curUser = userRepo.findByEmail(email);
       if(curUser == null){
           logger.error("Requested Password Change for Invalid Email");
           throw new EmailNotFoundException(HttpStatus.NOT_FOUND, StringMessages.EMAIL_NOT_FOUND);
       }
       Response<user> response = new Response<>(curUser, HttpStatus.ACCEPTED.value());
       return response;
    }

    @Override
    public ResponseEntity<Response<String>> changePassword(String token, NewPasswordBody newPass) throws TOKEN_EXPIRED {
          PasswordResetTokens tokenObject  = ResetPasswordRepo.findByToken(token);
          if(tokenObject == null){
              logger.error("Threw an Password Token Expiration Exception for token ",token);
              throw new TOKEN_EXPIRED(HttpStatus.NOT_FOUND, StringMessages.TOKEN_EXPIRED);
          }
          Calendar cal = Calendar.getInstance();
        if ((tokenObject.expirationTime.getTime() - cal.getTime().getTime() <= 0)) {
            logger.info("expired password token verification attempt token : {}", token);
            logger.info("removing password tokken {} from database.", token);
            ResetPasswordRepo.deleteById(tokenObject.id);
            throw new TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, StringMessages.TOKEN_EXPIRED);

        }
        String enocodedPassword = encoder.encode(newPass.confirmPassword);
        user curUser =tokenObject.curUser;
        curUser.encrypted_password = enocodedPassword;
        logger.info("Changing Password for user with id {}", curUser.id);
        ResetPasswordRepo.deleteById(tokenObject.id);
        userRepo.save(curUser);
        Response<String> response = new Response<>("Changed Password Succesfully", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<String>> changeCurrentPassword(ChangePasswordBody passwordBody) throws EmailNotFoundException, InvalidPasswordException {
        user curUser = userRepo.findByEmail(passwordBody.email);
        logger.info("finding user object for Change old password query for email {}",passwordBody.email);
        if(curUser == null){
            logger.error("Could not find user object for Change Password query for email {}",passwordBody.email);
            throw new EmailNotFoundException(HttpStatus.NOT_FOUND, StringMessages.EMAIL_NOT_FOUND);
        }

        String newEncyrptedPassword = encoder.encode(passwordBody.newPassword);
        if(!encoder.matches(passwordBody.oldPassword,curUser.encrypted_password)){
           logger.error("Invalid old password provided for email {}",curUser.email);
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST,StringMessages.INVALID_PASSWORD);
        }

        curUser.encrypted_password = newEncyrptedPassword;
        userRepo.save(curUser);
        logger.info("Change password query resolved for email {}", curUser.email);
        Response<String> response = new Response<>("Password Changed", HttpStatus.OK.value());
        return ResponseEntity.ok(response);

    }

    @Override
    public Response<String> completeRegistrationForComapnies(String email, CompanyReceptionTemplate comp) throws EmailNotFoundException,UnAuthenticatedAccountAccessException {
     user curUser = userRepo.findByEmail(email);
     if(curUser == null){
         logger.error("Employee registration completion attempt using invalid email, {}",email);
         throw new EmailNotFoundException(HttpStatus.NOT_FOUND,StringMessages.EMAIL_NOT_FOUND);
     }
     companies companiesObject = new companies();
        if(curUser.status.equals("UNAUTHENTICATED")){
            logger.error("Employee registration completion attempt for Un-Authenticated account, email : {}" ,email);
            throw new UnAuthenticatedAccountAccessException(HttpStatus.BAD_GATEWAY,StringMessages.UNAUTHENTICATED_ACCOUNT_ACCESS);
        }
        companiesObject.name = comp.name;
        companiesObject.GSTIN = comp.GSTIN;
        companiesObject.address = comp.address;
     curUser.type = "company";
     curUser.status = "AUTHENTICATED";
     curUser.isRegistrationComplete = "YES";
     userRepo.save(curUser);
        companiesObject.encryptedId = UUID.randomUUID().toString();
        companiesObject.userObject = curUser;
     comService.save(companiesObject);
Response<String> response = new Response<>("/company/"+companiesObject.encryptedId, HttpStatus.OK.value());
     return response;



    }

    @Override
    public Response<String> completeRegistrationForEmployee(String email, EmployeeReceptionTemplate empl) throws EmailNotFoundException, UnAuthenticatedAccountAccessException {
        user curUser = userRepo.findByEmail(email);
        if(curUser == null){
            logger.error("Company registration completion attempt using invalid email, {}",email);

            throw new EmailNotFoundException(HttpStatus.NOT_FOUND,StringMessages.EMAIL_NOT_FOUND);
        }
        if(curUser.status.equals("UNAUTHENTICATED")){
            logger.error("Company registration completion attempt for Un-Authenticated account, email : {}" ,email);

            throw new UnAuthenticatedAccountAccessException(HttpStatus.BAD_GATEWAY,StringMessages.UNAUTHENTICATED_ACCOUNT_ACCESS);
        }
        employee employeeObject = new employee();
        employeeObject.photo_address = empl.image_address;
        employeeObject.certificate_addresses = empl.certificate_address;
        employeeObject.resume_address = empl.resume_address;
        employeeObject.skills = empl.skills;
        curUser.type = "employee";
        curUser.status = "AUTHENTICATED";
        curUser.isRegistrationComplete = "YES";
        employeeObject.encryptedId = UUID.randomUUID().toString();
        employeeObject.userId = curUser;
        userRepo.save(curUser);
        employeeService.save(employeeObject);
        Response<String> response = new Response<>("/employee/" + employeeObject.encryptedId, HttpStatus.ACCEPTED.value());
        return response;
    }

    @Override
    public ResponseEntity<Response<String>> loginEmployee(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException {
      user userObject = userRepo.findByEmail(credentials.email);
     if(!encoder.matches(credentials.password, userObject.encrypted_password)){
         throw new InvalidPasswordException(HttpStatus.FORBIDDEN, StringMessages.INVALID_PASSWORD);
     }
     if(!userObject.type.equals("employee")){
         throw new EmailNotFoundException(HttpStatus.CONFLICT, StringMessages.EMAIL_NOT_FOUND);
     }
     if(userObject.isRegistrationComplete.equals("NO")){
         throw new UnAuthenticatedAccountAccessException(HttpStatus.FORBIDDEN, StringMessages.UNAUTHENTICATED_ACCOUNT_ACCESS);
     }
         String uuid = help.getNewUUID();

     tokens tokenObj = new tokens(userObject, uuid);
     loginTokenRepo.save(tokenObj);
     Response<String> response = new Response<>(uuid, HttpStatus.ACCEPTED.value());
     return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<Response<String>> loginCompany(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException {
        String uuid = help.getNewUUID();
        user com = userRepo.findByEmail(credentials.email);
        if(com == null){
            throw new EmailNotFoundException(HttpStatus.NOT_FOUND, StringMessages.EMAIL_NOT_FOUND);
        }
        if(!encoder.matches(credentials.password, com.encrypted_password)){
            throw  new InvalidPasswordException(HttpStatus.BAD_REQUEST, StringMessages.INVALID_PASSWORD);
        }
        if(com.status.equals("UNAUTHENTICATED")){
            throw  new UnAuthenticatedAccountAccessException(HttpStatus.BAD_REQUEST, StringMessages.UNAUTHENTICATED_ACCOUNT_ACCESS);
        }
        tokens curLoginToken = new tokens(com,uuid);
        loginTokenRepo.save(curLoginToken);
        Response<String> response = new Response<>(uuid, HttpStatus.ACCEPTED.value());
        return ResponseEntity.ok(response);
    }


}
