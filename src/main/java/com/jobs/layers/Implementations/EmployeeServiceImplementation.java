package com.jobs.layers.Implementations;
import com.jobs.helper;
import com.jobs.layers.Entities.companies;
import com.jobs.layers.DTOs.EmployeeTransferTemplate;
import com.jobs.layers.Entities.employee;
import com.jobs.layers.Entities.user;
import com.jobs.layers.Events.ProfileViewEvent;
import com.jobs.layers.Exceptions.EmailNotFoundException;
import com.jobs.layers.Exceptions.InvalidEncryptedIdException;
import com.jobs.layers.Messages.StringMessages;
import com.jobs.layers.Repositories.employee_repo;
import com.jobs.layers.Services.EmployeService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeService {
    @Autowired
    private employee_repo employeRepo;

    @Autowired
    private helper help;
    @Autowired
    private ApplicationEventPublisher publisher;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public employee save(employee empl) {
            //saves employee object after registration completion.
        logger.info("Save employee method executed");
        employeRepo.save(empl);
        return empl;
    }

    @Override
    public EmployeeTransferTemplate getEmployeeByEncryptedId(String id) throws InvalidEncryptedIdException {
       employee empl = employeRepo.findByEncryptedId(id);
       if(empl == null){
           logger.error("Requested for EmployeeTransferTemplate with invalid encrypted id, {}",id);
          throw new InvalidEncryptedIdException(HttpStatus.NOT_FOUND, StringMessages.INAVLID_ENCRYPTED_ID);
       }
        EmployeeTransferTemplate employeeTemplate = new EmployeeTransferTemplate();
       user userObject = empl.userId;
       employeeTemplate.image_address = empl.photo_address;
       employeeTemplate.certificate_address = empl.certificate_addresses;
       employeeTemplate.resume_address = empl.resume_address;
       employeeTemplate.email = userObject.email;
       employeeTemplate.encrypetdId = empl.encryptedId;
       employeeTemplate.firstName = userObject.first_name;;
       employeeTemplate.lastName = userObject.last_name;
       employeeTemplate.skills = empl.skills;

       employeeTemplate.joined = userObject.createdAt;
       return employeeTemplate;
    }

    public List<EmployeeTransferTemplate> findEmployeeByQuery(String query, companies com, HttpServletRequest req) throws UnknownHostException {
        List<employee> listOfAllEmployee = employeRepo.findAll();
        List<EmployeeTransferTemplate> resolutionList = new ArrayList<EmployeeTransferTemplate>();
        for(employee curEmployee : listOfAllEmployee){
            if(curEmployee.skills.contains(query)){
                EmployeeTransferTemplate template = new EmployeeTransferTemplate(curEmployee, curEmployee.userId);
             String comUrl = help.generateHosturl(req) + "/company/" + com.encryptedId;
                ProfileViewEvent event = new ProfileViewEvent(comUrl, template.email, query);
                publisher.publishEvent(event);
                resolutionList.add(template);
            }
        }
        return resolutionList;
    }
}



