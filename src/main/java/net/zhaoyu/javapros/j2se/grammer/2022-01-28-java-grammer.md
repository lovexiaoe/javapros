---
title: java语言规范-语法
published: true
category: java语言规范
---

## 词法结构
### Unicode
java程序使用unicode字符集书写。相关的字符编码可以在`http://www.unicode.org`找到。具体的Unicode编码版本在类Character的文档中规定。
java8使用unicode6.2。

char类型用于表示单个字符。如'A'是编码为64所对应的字符常量。
Unicode编码最初只设计为固定宽度的十六位字符编码，此后随着字符的不断加入被改变，允许超过16位。合法的代码点范围从U+0000到U+10FFFF。使用
U+n和十六进制表示法。超过U+FFFF的代码点被称为补充字符。对于补充字符，使用一对UTF-16表示，高位范围(U+D800~U+DBFF)，低位范围（U+DC00~U+DFFF）。

### Unicode转义
java编译器首先对程序内容识别Unicode转义，将`\u`和后面的四个16进制数字转换为UTF-16的代码单元，用来表示16进制值。而其他的字符保持不变。表示补充字符则需要
两个连续的Unicode转义。经过转义步骤后会产生一个Unicode字符串。
如我们输入：
```
System.out.println("\u9999，你好");
```
输出内容为：`香，你好`。
但是如果在Unicode转义前加入反斜杠`\`。则Unicode转义不会被转义。
```
System.out.println("\\u9999，你好"); //输出为\u9999，你好
```

### 输入内容和符号
java程序经过Unicode转义，换行识别后，会变成一堆输入元素。
* 输入由输入元素加Sub符号组成：`{输入元素}[Sub]`
* 输入元素包括了：
    * 空格
    * 注释
    * 符号
* 符号包括了：
    * 标识符(Identifier)
    * 关键字
    * 文本
    * 分隔符
    * 操作符
* SUB:ASCII SUB符号，也被称为"control-Z"。      
为了兼容某些特殊的操作系统，ASCII SUB字符（\u001a,或control-Z）如果是转义输入流中的最后一个字符，则忽略。

### 标识符（Identifier）
一个由java字母和java数字组成的不限长度的序列，称为标识符。
* java字母        
A-Z和a-z，历史原因，还有_和$。$几乎只用在生成源码的机制中，如匿名类。每个java字母`Character.isJavaIdentifierStart(int)`会返回true。
* java数字：0-9。 对于java字母或者java数字，`Character.isJavaIdentifierPart(int)`返回true。
* 标识符字符串：由java字母开头的多个java字母或java数字组成。
* 标识符，除去关键字，boolean文本和null文本的标识符字符串。