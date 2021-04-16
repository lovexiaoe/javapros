package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Heaps;


/**
 * 堆，一种特殊的二叉树，用于排序，有最大堆和最小堆，最大堆的root元素为最大值，树种所有子节点元素都要小于父节点元素，且这个树
 * 是一个完整树。堆的操作，只对顶部元素进行操作。
 * 所有堆相关的操作（插入，删除，抽取最大最小值等的时间复杂度都是为O(log n)）
 * @Author: zhaoyu
 * @Date: 2020/12/29
 */
public interface Heap {
    /**
     * 返回顶部元素
     * @param
     * @return HeapElement
     * @throws EmptyHeapException 如果堆为空
     */
    HeapElement getElement() throws EmptyHeapException;

    /**
     * 插入顶部元素，并调整堆重新形成新堆。
     * @param element
     * @return void
     */
    void insertElement(HeapElement element);

    /**
     * 删除顶部元素，并调整堆，
     * @param elementIndex
     * @return void
     */
    void deleteElement(int elementIndex);
}
