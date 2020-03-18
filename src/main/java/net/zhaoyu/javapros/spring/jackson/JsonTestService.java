package net.zhaoyu.javapros.spring.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonTestService {
    /**
     * 在代码中可以直接引用定义号的jackson-ObjectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;
}
