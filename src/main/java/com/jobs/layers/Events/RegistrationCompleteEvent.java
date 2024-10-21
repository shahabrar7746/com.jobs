package com.jobs.layers.Events;

import com.jobs.layers.Entities.user;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Data
@Getter
@Setter
public class RegistrationCompleteEvent  extends ApplicationEvent {
    public user curUser;
    public String applicationUrl;
    public String userType;
    public RegistrationCompleteEvent(user curUser, String applicationUrl) {
        super(curUser);
        this.userType = curUser.type;
        this.curUser = curUser;
        this.applicationUrl = applicationUrl;
    }
}
