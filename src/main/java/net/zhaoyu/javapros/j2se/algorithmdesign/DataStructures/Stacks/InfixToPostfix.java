package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Stacks;


import java.util.Stack;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2021/1/25
 */
public class InfixToPostfix {

    public static void main(String[] args) {
        System.out.println(infix2PostFix("(3+4)*5-6"));
        System.out.println(infix2PostFix("3+5*6/3"));

    }

    public static String infix2PostFix(String infixExpression){
        //提取表达式中的括弧。
        String brackets=infixExpression.replaceAll("[^\\(\\)\\{\\}\\[\\]]", "");
        System.out.println(brackets);

        if (!BalancedBrackets.isBalanced(brackets)) {
            throw new RuntimeException("invalid expression");
        }

        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        //表达式中的字符分三种情况处理，1:数字,2:(),3:操作符。
        for (Character c:infixExpression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                //处理数字
                sb.append(c);
            } else if (c == '(') {
                //将(放入栈中
                stack.push(c);
            } else if (c == ')') {
                //碰到)，从栈中将(之上的操作符取出。
                while (!stack.isEmpty() && stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                stack.pop();
            } else {
                //如果栈中的操作符优先级大于当前操作符优先级，取出栈中的操作符。
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    sb.append(stack.pop());
                }
                //将操作符放入到栈中。
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    private static int precedence(char operator){
        switch (operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            default:
                return -1;
        }
    }
}
