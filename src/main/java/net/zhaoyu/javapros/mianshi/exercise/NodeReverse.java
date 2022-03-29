package net.zhaoyu.javapros.mianshi.exercise;


import java.io.IOException;
import java.util.Scanner;

/**
 * 链表的反向打印，要求时间复杂度On,空间复杂度O1。
 */
public class NodeReverse {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String input;
        while (in.hasNext()) {
            ListNode node=null,last=null;
            input = in.next();
            if (input.equals("exit")) {
                return;
            }
            String[] arr=input.substring(1,input.length()-1).split(",");
            if (arr.length < 1||arr[0].equals("")) {
                System.out.println("{}");
                continue;
            }
            for (String s : arr) {
                int i= 0;
                try {
                    i = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    System.out.println("输入的"+s+"不是数字，不予解析");
                    break;
                }
                ListNode node1 = new ListNode(i);
                if (last != null) {
                    last.next = node1;
                } else {
                    node=node1;
                }
                last=node1;
            }
            node.reverseList().print();
        }
    }



}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }

    public void print(){
        System.out.print("{");
        System.out.print(this.val);
        ListNode next=this.next;
        while (next != null) {
            System.out.print(",");
            System.out.print(next.val);
            next=next.next;
        }
        System.out.println("}");
    }

    public ListNode reverseList() {
        if(this==null){
            return null;
        }
        ListNode head=this,next=this.next,tmp;
        head.next=null;
        while(next!=null){
            tmp=next;
            next=next.next; //记录新的next
            tmp.next=head;//旧的next指针反转
            head=tmp;//记录新的head
        }
        return head;
    }
}