package com.jobs.layers.Services;

import com.jobs.layers.DTOs.*;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Entities.employee;
import com.jobs.layers.Exceptions.*;
import org.hibernate.validator.constraints.Email;
import org.springframework.http.ResponseEntity;
import com.jobs.layers.Entities.user;

public interface Authentication
{
    public user register(String userType, userModel reuqestModel) throws InvalidUserTypeException, EmailInUseException;
    public void saveVerificationTokenForUser(user curUser, String token);
 public ResponseEntity<String> verifyToken(String token,String userType) throws TOKEN_EXPIRED, InvalidUserTypeException;
 public user resendVerificationToken(String token) throws TOKEN_EXPIRED;
 public user forgotPassword(String email) throws EmailNotFoundException;
 public ResponseEntity<String> changePassword(String token, NewPasswordBody newPass) throws TOKEN_EXPIRED;
public ResponseEntity<String> changeCurrentPassword(ChangePasswordBody passwordBody) throws EmailNotFoundException, InvalidPasswordException;

   public String completeRegistrationForComapnies(String email, CompanyReceptionTemplate comp)throws EmailNotFoundException,UnAuthenticatedAccountAccessException;

 public   String completeRegistrationForEmployee(String email, EmployeeReceptionTemplate empl) throws EmailNotFoundException, UnAuthenticatedAccountAccessException;

  public  ResponseEntity<String> loginEmployee(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException;

    public ResponseEntity<String> loginCompany(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException;
}
