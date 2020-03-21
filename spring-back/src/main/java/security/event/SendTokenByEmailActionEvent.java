package security.event;

import model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class SendTokenByEmailActionEvent extends ApplicationEvent {

    private transient UriComponentsBuilder url;
    private transient User user;

    public SendTokenByEmailActionEvent(User user, UriComponentsBuilder url) {
        super(user);
        this.user = user;
        this.url = url;
    }
}
