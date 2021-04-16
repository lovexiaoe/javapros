package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Lists;

/**
 * 单链表实现
 */
public class SinglyLinkedList {
  private Node head;

  private int size;

  public SinglyLinkedList() {
    head = null;
    size = 0;
  }


  public SinglyLinkedList(Node head, int size) {
    this.head = head;
    this.size = size;
  }

  public void insertHead(int x) {
    insertNth(x, 0);
  }

  public void insert(int data) {
    insertNth(data, size);
  }

  /**
   * 在指定的位置插入一个元素
   *
   * @param data
   * @param position
   */
  public void insertNth(int data, int position) {
    checkBounds(position, 0, size);
    Node newNode = new Node(data);
    if (head == null) {
      /* the list is empty */
      head = newNode;
      size++;
      return;
    } else if (position == 0) {
      /* insert at the head of the list */
      newNode.next = head;
      head = newNode;
      size++;
      return;
    }
    Node cur = head;
    for (int i = 0; i < position - 1; ++i) {
      cur = cur.next;
    }
    newNode.next = cur.next;
    cur.next = newNode;
    size++;
  }

  public void deleteHead() {
    deleteNth(0);
  }

  public void delete() {
    deleteNth(size - 1);
  }

  /** 删除在位置position的元素 */
  public void deleteNth(int position) {
    checkBounds(position, 0, size - 1);
    if (position == 0) {
      Node destroy = head;
      head = head.next;
      //清除
      destroy = null;
      size--;
      return;
    }
    Node cur = head;
    for (int i = 0; i < position - 1; ++i) {
      cur = cur.next;
    }

    Node destroy = cur.next;
    cur.next = cur.next.next;
    destroy = null; // clear to let GC do its work

    size--;
  }

  /**
   * 检查是否越界
   * @param position to check position
   * @param low low index
   * @param high high index
   * @throws IndexOutOfBoundsException if {@code position} not in range {@code low} to {@code high}
   */
  public void checkBounds(int position, int low, int high) {
    if (position > high || position < low) {
      throw new IndexOutOfBoundsException(position + "");
    }
  }

  /** Clear all nodes in the list */
  public void clear() {
    Node cur = head;
    while (cur != null) {
      Node prev = cur;
      cur = cur.next;
      prev = null; // clear to let GC do its work
    }
    head = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public Node getHead() {
    return head;
  }

  public int count() {
    int count = 0;
    Node cur = head;
    while (cur != null) {
      cur = cur.next;
      count++;
    }
    return count;
  }

  /**
   * 是否存在元素key
   */
  public boolean search(int key) {
    Node cur = head;
    while (cur != null) {
      if (cur.value == key) {
        return true;
      }
      cur = cur.next;
    }
    return false;
  }

  /**
   * 返回特定位置的元素的值
   */
  public int getNth(int index) {
    checkBounds(index, 0, size - 1);
    Node cur = head;
    for (int i = 0; i < index; ++i) {
      cur = cur.next;
    }
    return cur.value;
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    Node cur = head;
    while (cur != null) {
      builder.append(cur.value).append("->");
      cur = cur.next;
    }
    return builder.replace(builder.length() - 2, builder.length(), "").toString();
  }

  /** Driver Code */
  public static void main(String[] arg) {
    SinglyLinkedList list = new SinglyLinkedList();
    assert list.isEmpty();
    assert list.size() == 0 && list.count() == 0;
    assert list.toString().equals("");

    /* Test insert function */
    list.insertHead(5);
    list.insertHead(7);
    list.insertHead(10);
    list.insert(3);
    list.insertNth(1, 4);
    assert list.toString().equals("10->7->5->3->1");

    /* Test search function */
    assert list.search(10) && list.search(5) && list.search(1) && !list.search(100);

    /* Test get function */
    assert list.getNth(0) == 10 && list.getNth(2) == 5 && list.getNth(4) == 1;

    /* Test delete function */
    list.deleteHead();
    list.deleteNth(1);
    list.delete();
    assert list.toString().equals("7->3");

    assert list.size == 2 && list.size() == list.count();

    list.clear();
    assert list.isEmpty();

    try {
      list.delete();
      /* this should not happen */
      assert false;
    } catch (Exception e) {
      /* this should happen */
      assert true;
    }
  }
}


class Node {
  int value;
  Node next;
  Node() {}

  Node(int value) {
    this(value, null);
  }

  Node(int value, Node next) {
    this.value = value;
    this.next = next;
  }
}
