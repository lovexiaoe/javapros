package com.zhaoyu.threads.ThreadPool.executorInvoke;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ValidatorTask implements Callable<String> {

    private final String systemName;
    /**
     * The name of the user
     */
    private final String user;
    /**
     * The password of the user
     */
    private final String password;

    public ValidatorTask(String systemName,String user, String password){
        this.systemName=systemName;
        this.user=user;
        this.password=password;
    }

    /**
     * 根据不同的系统,验证用户名和密码的有效性，如果成功，返回系统的名称。
     * 如果失败抛出异常。
     * @return The name of the user validation system.
     * @throws Exception An exception when the user is not validated
     */
    @Override
    public String call() throws Exception {
        if (validate(user,password)) {
            System.out.printf("%s: The user has not been found\n",systemName);
            throw new Exception("Error validating user");
        }
        System.out.printf("%s: The user has been found\n",systemName);
        return systemName;
    }

    private boolean validate(String user,String password){
        Random random=new Random();

        // Sleep the thread during a random period of time
        try {
            Long duration=(long)(Math.random()*10);
            System.out.printf("Validator %s: Validating a user during %d seconds\n",this.systemName,duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            return false;
        }
        return random.nextBoolean();
    }
}
