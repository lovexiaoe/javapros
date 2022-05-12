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
wait不仅会让当前线程休眠，还会禁用线程调度，而sleep不会禁用线程调度。
Thread.sleep只是让当前执行的线程睡眠（临时停止执行，不占用cpu资源），线程不会释放任何monitor。
**Thread.sleep 和 Thread.yield是没有任何同步语义的**。
在调用sleep或者yield之前，不会将寄存器中的缓存写入到主内存中，在调用后，也不会从主内存中加载值到寄存器缓存。如下，假如this.done不是一个
volatile字段：
```java
while(!this.done){
    Thread.sleep(1000);
}
```
由于jvm只会加载一次this.done，并使用缓存执行，这就意味着即使有其他线程改变了done，这个循环也永远不会结束。

### 内存模型
java 内存模型的工作原理是检查执行路径上的每个读取，并核实每个读取观察到的写入是否有效。

#### 处理器和内存的交互
数据可以存放在处理器寄存器里面（目前x86处理都是基于寄存器架构的），处理器缓存里面，内存，磁盘等。为了加快程序运行速度，数据离处理
器越近越好。但是寄存器，处理器缓存都是处理器私有数据，只有内存，磁盘才是所有处理器都可以访问的全局数据（磁盘我们这里不讨论，只讨论内存）        

如果程序是多线程的，那么不同的线程可能分配到不同的处理器来执行，这些处理器需要把数据从主内存加载到处理器缓存和寄存器里面才可以执行，
数据执行完成之后，再把执行结果同步到主内存。如果这些数据是所有线程共享的，那么就会发生同步问题，因为某个线程加载数据后会将数据缓存起来，
数据修改不会立即同步到主内存。

### 内存模型
有两种内存模型：
#### 连续一致性模
这个模型定义了程序执行的顺序和代码执行的顺序是一致的。也就是说 如果两个线程，一个线程T1对共享变量A进行写操作，
另外一个线程T2对A进行读操作。如果线程T1在时间上先于T2执行，那么T2就可以看见T1修改之后的值。     
这个内存模型比较简单，也比较直观，比较符合现实世界的逻辑。但是这个模型定义比较严格，在多处理器并发执行程序的时候，会严重的影响程序的性能。
因为每次对共享变量的修改都要立刻同步会主内存，不能把变量保存到处理器寄存器里面或者处理器缓存里面。导致频繁的读写内存影响性能。
#### 先行先发模型
要理解这个模型，就需要理解为先行先发（Happens-Before），我自己理解为部分一致性，提供了一些保持一致的原则，不会全部保持一致。这提高了多核
系统的性能。      
如果有两个操作A和B存在A Happens-Before B，那么操作A对变量的修改对操作B来说是可见的。以下面的例子举例说明：
```
A，B为共享变量，r1，r2为局部变量
初始 A=B=0

Thread1   | Thread2
1: r2=A   | 3: r1=B
2: B=2    |  4: A=2
```
我们期望的执行顺序可能是1-3-2-4，结果是r1=0,r2=0，但是在先行先发模型里，因为编译器和处理器都可能对指令进行重排，导致实际的执行顺序可能
是2-4-1-3，结果为r1=2,r2=2。      
当一个线程写某个变量，另一个线程读取同一个变量。当读和写没有使用同步进行排序的时候，就会产生数据竞争（data race）。数据竞争会导致不可预期的结果。

再看一个例子，初始化为p==q且p.x==0，线程执行如下：
```
Thread1     |   Thread2
r1 = p;     |   r6 = p;
r2 = r1.x;  |   r6.x = 3;
r3 = q;     |
r4 = r3.x;  |
r5 = r1.x;  |
```
一种常见的优化是将r2赋值给r5，因为r2和r5都读取了r1.x且他们中间没有r1.x的写操作。替换后如下：
```
Thread1     |   Thread2
r1 = p;     |   r6 = p;
r2 = r1.x;  |   r6.x = 3;
r3 = q;     |
r4 = r3.x;  |
r5 = r2;  |
```
假如Thread2对r6.x的赋值发生在Thread1读取r1.x和读取r3.x之间那么r2和r5等于0，r4等于3。那么在线程1中从开发者的视角会发现p.x从0变成了3然后
再变回了0。

#### 共享变量
线程间共享的内存被称为共享内存或者堆内存。       
所有的实例字段，static字段，数组元素都存储在堆内存中。本地变量，方法参数，和异常处理参数从不在线程间共享，不受内存模型影响。

#### Happens-Before
先行先发原则约定了一些动作必定先发生于另一些动作。
* 同一个线程内的动作保持和语义上一致的先后顺序。
* 一个monitor上面的unlock动作必先于后续的lock动作。
* 对volatile变量的写必先于后续的读取动作。
* 线程的start必先于线程的第一个动作。
* 每个变量默认值（0,false,null）的写入，必先于线程的第一个动作。
* 线程T1的结束动作先于线程T2检测到T1的结束。通过T1.isAlive()或T1.join实现。
* 如果线程T1打断了线程T2,打断动作必先于T2检测到打断且抛出InterruptedException异常。
* 一个对象的constructor的结束先于finalizer的开始。
* 传递性：如果x先发于y，y先发于z，那么x先发于z。

wait有lock和unlock的动作，所以也适用先行先发原则。
   
### 字撕裂
JVM的实现中，每个字段或数组元素是独立的。对一个字段或元素的读写不会影响到另一个字段或元素的读写，对相邻数据元素之间的操作也
不会相互影响，所以不需要synchronization确保一致性。       
一些处理器不支持操作单个byte，这样的处理器上，通过读取整个字（处理器支持操作的最小长度，32或64），更新其中的某个byte，再写回整个字来
实现byte数组，在多线程环境下，会出现数据竞争。这样的问题被称为**字撕裂**。不过现代JVM的实现都处理了字撕裂，实际使用也不会出现这个问题，
这是JVM实现层面的问题，作为了解。

### double 和 long的非原子性
由于java语言内存模型的原因，long和double的非volatile写入被处理成两次写入，每次处理32位的数据，在并发下会导致一个线程从一次写入中
看到前32位，而从另一次写入中看到后32位。

* volatile的long和double的读写总是原子的。
* 引用类型的读写总是原子的。不管他们用32位还是64的值实现。


