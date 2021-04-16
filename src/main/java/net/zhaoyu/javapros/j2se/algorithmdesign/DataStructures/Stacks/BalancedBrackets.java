package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Stacks;

import java.util.Stack;

/**
 * @Description: 检查括弧是否是正确的，如：“()()[()]”是正确的，“[(()]” 则不是正确的。
 * @Author: zhaoyu
 * @Date: 2021/1/25
 */
public class BalancedBrackets {
    /**
     * 括弧是否成对
     * @param leftBracket
     * @param rightBracket
     * @return boolean
     */
    public static boolean isPaired(char leftBracket,char rightBracket){
        char[][] pairedBrackets={
                {'(',')'},
                {'[',']'},
                {'{','}'},
                {'<','>'}
        };

        for (char[] pair:pairedBrackets) {
            if (pair[0] == leftBracket && pair[1] == rightBracket) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断括弧字符串是否正确
     * @param brackets
     * @return boolean
     */
    public static boolean isBalanced(String brackets){
        if (brackets == null) {
            throw new IllegalArgumentException("brackets is null");
        }
        Stack<Character> bracketsStack=new Stack<>();
        for (char bracket:brackets.toCharArray()) {
            switch (bracket) {
                case '(':
                case '[':
                case '{':
                    bracketsStack.push(bracket);
                    break;
                case ')':
                case ']':
                case '}':
                    if (bracketsStack.isEmpty() || !isPaired(bracketsStack.pop(), bracket)) {
                        return false;
                    }
                    break;
                default: return false;
            }
        }
        return bracketsStack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isBalanced("[()]{}{[()()]()}"));
        System.out.println(isBalanced("[(])"));
    }
}
