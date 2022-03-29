---
title: java语言规范-线程和锁
published: true
category: java语言规范
---

jvm支持同时执行多个线程，代码操作存储在共享主内存中的值和对象，多个线程可以独立执行这些代码。多个线程可能被多个硬件处理器运行。
可能被单个处理器上的时间分片运行，也可能被多个处理器上的时间分片运行。
### Synchronization
java 提供了多种线程间通信的机制，最基本的就是Synchronization，使用monitor实现。java中的每个对象都连接一个monitor。monitor上
提供了一把锁，线程可以lock和unlock monitor上的锁。在一个时间只有一个线程持有monitor上的锁，其他想要获得锁的线程会阻塞。       
synchronized会尝试在进入一个对象的monitor后，执行lock操作获取锁，锁定成功后，synchronized的代码块才会继续执行，如果代码块执行完、
或者异常和中断。unlock动作会自动在相同的monitor执行。       
synchronized如果执行在一个实例方法上，那么会锁定和实例关联的monitor上的锁。如果是static，将会锁定Class对象的monitor上的锁。

### Wait Sets 和 Notification
每个对象除了有一个monitor外，还有一个关联的Wait Sets，存放一组线程。当一个对象被创建的时候，wait set是空的。
Wait Set中的线程的添加和移除都是原子的。只能通过Object的wait,notify和notifyAll方法操作wait sets。wait sets也会受到
线程中断状态和处理中断的线程方法的影响。

#### wait
wait动作在调用wait方法后执行，一个wait如果没有抛出InterruptedException，就会正常返回。     
假设线程t要在对象m上执行wait方法，则必须拥有对象m的monitor，如果没有，会抛出IllegalMonitorStateException异常。
* 如果调用了timed wait且ns参数不在0-999999范围内或者是负数。会抛出IllegalArgumentException异常。
* 如果t被打断（interruption状态为true）。会抛出InterruptedException异常且t的interruption状态会被设为false(清空interruption状态)。
* 其他情况下，会发生如下情况：
    1. t会被加入到对象m的等待集合中，unlock在对象m上的n次lock（未退出monitor，但是释放了锁，其他进入线程可以进入monitor并获取锁），
    禁用线程调度，处于休眠状态。
    2. t在被从等待集合中移出之前，不会执行其他任何指令。下面情况发生时，t会从等待集合中被移出，并执行后续动作。
        * 在m上的notify被执行，且t被选中从等待集合中移出。
        * 在m上的notifyAll被执行。
        * 在t上执行了一个interrupt动作。
        * 如果是timed wait，时间到了会从等待集合中移出。
    3. t被从等待集合中移出后，会在m上再次执行n次lock，并尝试获取monitor的锁。
    4. 如果t在第二步中因interrupt从等待集合中移出，将t的interruption状态设置为false，抛出InterruptedException异常。        

#### notification
notification动作在调用notify或者notifyAll后执行。
假设线程t要在对象m上执行notify相关方法。且t在m上有n次lock（还未unlock)。会发生下面几种情况：
* 如果n为0，即t在m上没有锁，会抛出IllegalMonitorStateException异常。
* 如果n大于0，并且是notify动作，并且m的等待集合不为空，那么等待集合中的某个线程u会被选中并从等待集合中移出。这时会让u从等待状态中恢复，
但是u不会立马获取执行，需要等t完全释放monitor上的锁后，u的锁定动作才会成功。
* 如果n大于0，并且是notifyAll动作，那么所有的线程会从等待结合中移出，并从wait中恢复。然而这些线程在wait恢复期间，只会有一个
线程获取monitor的锁成功。

#### Interruptions
调用Thread.interrupt，ThreadGroup.interrupt等方法执行线程的打断动作。       
如果线程t调用了u.interrupt（一些情况下t和u可能相同），那么会让u的interruption状态设置为true。

如果u在某些对象的等待集合中，那么u会从等待集合中移出，这让u从wait状态中恢复，并抛出InterruptedException。


#### wait,notify和interrupt的交互。
如果一个线程在wait的过程中，既被interrupt又被notify，那么它可能会：
1. 从wait中正常返回，但是仍然有interruption状态。
1. 从wait中抛出InterruptedException。        
也就是说，wait有可能带着打断状态正常返回。     
类似的，**notify不能因为打断而丢失**。假如对象m的等待集合中的有等待线程集s，此时其他一个线程执行了notify操作，那么：
1. s中至少一个线程会从wait中正常返回。
1. 或者s集合中的所有线程都让wait方法抛出InterruptedException。


### Sleep 和 Yield

