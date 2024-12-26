package com.jobs.layers.Services;

import com.jobs.layers.DTOs.*;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Entities.employee;
import com.jobs.layers.Exceptions.*;
import com.jobs.layers.Responses.Response;
import org.hibernate.validator.constraints.Email;
import org.springframework.http.ResponseEntity;
import com.jobs.layers.Entities.user;

public interface Authentication
{
    public Response<user> register(String userType, userModel reuqestModel) throws InvalidUserTypeException, EmailInUseException;
    public void saveVerificationTokenForUser(user curUser, String token);
 public ResponseEntity<Response<String>> verifyToken(String token,String userType) throws TOKEN_EXPIRED, InvalidUserTypeException;
 public Response<user> resendVerificationToken(String token) throws TOKEN_EXPIRED;
 public Response<user> forgotPassword(String email) throws EmailNotFoundException;
 public ResponseEntity<Response<String>> changePassword(String token, NewPasswordBody newPass) throws TOKEN_EXPIRED;
public ResponseEntity<Response<String>> changeCurrentPassword(ChangePasswordBody passwordBody) throws EmailNotFoundException, InvalidPasswordException;

   public Response<String> completeRegistrationForComapnies(String email, CompanyReceptionTemplate comp)throws EmailNotFoundException,UnAuthenticatedAccountAccessException;

 public   Response<String> completeRegistrationForEmployee(String email, EmployeeReceptionTemplate empl) throws EmailNotFoundException, UnAuthenticatedAccountAccessException;

  public  ResponseEntity<Response<String>> loginEmployee(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException;

    public ResponseEntity<Response<String>> loginCompany(Login credentials) throws EmailNotFoundException, InvalidPasswordException, UnAuthenticatedAccountAccessException;
}
