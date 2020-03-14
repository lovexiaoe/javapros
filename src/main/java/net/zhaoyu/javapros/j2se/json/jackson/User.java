package net.zhaoyu.javapros.j2se.json.jackson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private String name;
    private Integer age;
    private String address;

    public User(String name, int age, String address) {
        this.name=name;
        this.age=age;
        this.address=address;
    }
}
