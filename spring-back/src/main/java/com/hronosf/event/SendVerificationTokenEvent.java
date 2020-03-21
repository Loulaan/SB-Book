package com.hronosf.event;

import com.hronosf.model.entity.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
public class SendVerificationTokenEvent extends ApplicationEvent {

    private Person person;
    private UriComponentsBuilder url;
    private boolean isForRefresh;

    public SendVerificationTokenEvent(Person person, UriComponentsBuilder url) {
        super(person);
        this.person = person;
        this.url = url;
    }
}
