package cn.com.nd.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
