package com.jobs.layers.Services;

import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.Exceptions.EmailNotFoundException;
import com.jobs.layers.Exceptions.InvalidEncryptedIdException;
import com.jobs.layers.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.jobs.layers.Entities.employee;

import java.net.UnknownHostException;
import java.util.List;

@Component
public interface EmployeService {


    public Response<employee> save(employee empl);

   public Response<EmployeeTransferTemplate> getEmployeeByEncryptedId(String id) throws InvalidEncryptedIdException;


   public  Response<List<EmployeeTransferTemplate>> findEmployeeByQuery(String query, companies com, HttpServletRequest req) throws UnknownHostException;

}
