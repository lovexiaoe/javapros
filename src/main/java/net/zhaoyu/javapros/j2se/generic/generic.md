### 泛型
Java 泛型（generics）是 JDK 5 中引入的一个新特性, 泛型提供了编译时类型安全检测机制，该机制允许程序员在编译时检测到非法的类型。
虚拟机没有泛型类型对象，所有的对象都属于普通类。泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。

#### 泛型的定义
类型变量使用大写形式，在java中使用E表示集合的元素类型，K和V分别表示表的关键字与值的类型。 T(需要时还可以用临近的字母U和S)表示任意类型。
泛型类型的限定：如`T extends Comparable & Serializable`, &用于分隔多个限定。

#### 不能使用泛型的情况
1. 不能用基本类型实例化类型参数
如没有`Pair<double>`，只有`Pair<Double>`。`Pair<String>[] table` 也是不合法的
1. 运行时类型检查只能检查原始类型，不能检查出参数化类型。      
如`if(a instanceof Pair<String>)`，只能检查出a是一个Pair，getClass也是同样的，只能得到原始类型。
1. 在类或者接口的继承层级关系中，要注意类型擦除引起的冲突。
```java
class Calendar implements Comparable<Calendar>{...}
//error
class GregorianCalendar extends Calendar implements Comparable<GregorianCalendar> 
```
GregorianCalendar会实现Calendar的Comparable<Calendar>和Comparable<GregorianCalendar>
这是不同参数化。在类型擦除后会冲突。

#### 类型擦除
类型擦除（erased）----由于jvm没有对应的泛型类型， 所以在编译时，jvm就会去掉类型变量，替换为对应的限定类型，如<? extends Comparable>的
限定类型为ComParable， 如果没有限定，则使用Object。

当程序调用泛型方法时，如果擦除返回类型，编译器插入强制类型转换。例如，下面这个语句序列 
```java
	Pair<Employee> buddies=..;
	Employee buddy=buddies.getFirst();
```

类型擦除也会出现在泛型方法中。如下面方法 
```java
public static <T extends Comparable> T min(T[] a)
 ```
在类型擦除后，只剩下一个方法： 
```java
public static  Comparable min(Comparable[] a)
 ```
类型擦除虽然解决了类型限定的问题，但是这样会带来两个复杂的问题。
```java
Class DateInterval extends Pair<Date>
{
 	public void setSecond(Date second){
		super.setSecond(second);
	}
}
```

如果Pair有方法setSecond(Object second),那么在类型擦除后会有两个相同的setSecond方法，这时类型擦除和多态方法
发生冲突。要解决这个问题，就需要编译器在DataInteral中生成一个桥方法（bridge method）
```java
public void setSecond(Object second){
	setSecond((Date)second);
}
```

桥方法变得很奇怪。如果DateInterval覆盖了getSecond方法，那么在类型擦除后，会有两个getSecond方法 
```java
	Date getSecond()  //在DateInterval中定义的
	Object getSecond() //在Pair中定义的。
```

桥方法只是虚拟机在处理类型擦除时的机制，不能用于正式的代码。

总之，java泛型转换有4个要素：
1. 虚拟机没有泛型，只有普通的类和方法。
2. 所有的类型参数都用它们的限定类型替换
3. 奇怪的桥方法被合成来保持多态，是中间态，不能用于正式代码。
4. 为保持类型安全性，必要时插入强制类型转换。

