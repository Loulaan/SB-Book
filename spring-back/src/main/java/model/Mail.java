package model;


import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Mail {

    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, String> model;
}
