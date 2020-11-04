package net.zhaoyu.javapros.j2se.threads.optional;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @Description: Car对象，引用Insurance
 * @Author: zhaoyu
 * @Date: 2020/11/4
 */
@Getter
@Setter
public class Car {
    private Optional<Insurance> insurance;
}
