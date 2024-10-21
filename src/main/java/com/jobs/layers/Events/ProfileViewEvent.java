package com.jobs.layers.Events;

import com.jobs.layers.DTOs.CompanyTransferTemplate;
import com.jobs.layers.DTOs.JobsTransferTemplate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Data
@Getter
@Setter
public class ProfileViewEvent extends ApplicationEvent {

    public String companyUrl;
    public String email;
    public String query;
    public ProfileViewEvent( String  companyUrl, String email,String query) {
        super(query);
        this.email = email;

       this.query = query;
       this.companyUrl = companyUrl;
    }
}
