package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 最大值堆，每个节点都大于等于父节点，小于等于子节点。
 * @Author: zhaoyu
 * @Date: 2020/12/29
 */
public class MaxHeap implements Heap{

    private final List<HeapElement> maxHeap;

    public MaxHeap(List<HeapElement> listElements) {
        this.maxHeap = new ArrayList<>();
        for (HeapElement listElement : listElements) {
            if (listElement == null) {
                System.out.println("空元素，不会添加到堆中");
            } else {
                insertElement(listElement);
            }
        }
    }

    public HeapElement get(int index) {
        if (0 > index || maxHeap.size() <= index) {
            throw new IndexOutOfBoundsException("index超过堆范围");
        }
        return maxHeap.get(index);
    }

    private double getElementKey(int index) {
        return get(index).getKey();
    }

    private void swap(int index1,int index2){
        HeapElement temp = maxHeap.get(index1);
        maxHeap.set(index1, maxHeap.get(index2));
        maxHeap.set(index2, temp);
    }

    /**
     * 当一个元素的key小于其父元素时，将该元素向上转换，到正确的位置。
     * @param index
     */
    private void toggleUp(int index){
        while (index>0&&maxHeap.get(index).getKey() < maxHeap.get((index - 1) / 2).getKey()) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 当一个元素的key大于其子元素的key时，将该元素向下转换，到正确的位置。
     * @param index
     */
    private void toggleDown(int index){
        int tempSwapIndex;
        int tempLeftIndex,tempRightIndex;
        while (index * 2 + 1 < maxHeap.size()) {
            tempSwapIndex = index * 2 + 1;
            tempLeftIndex=tempSwapIndex;
            tempRightIndex=tempSwapIndex+1;
            tempRightIndex=tempRightIndex>= maxHeap.size()?tempLeftIndex:tempRightIndex;
            if (maxHeap.get(tempLeftIndex).getKey() >= maxHeap.get(tempRightIndex).getKey()) {
                tempSwapIndex=tempLeftIndex;
            } else {
                tempSwapIndex = tempRightIndex;
            }
            if (maxHeap.get(index).getKey() > maxHeap.get(tempSwapIndex).getKey()) {
                swap(index, tempSwapIndex);
            } else {
                break;
            }
            index=tempSwapIndex;
        }
    }

    @Override
    public HeapElement getElement() throws EmptyHeapException {
        try {
            HeapElement element = maxHeap.get(0);
            maxHeap.remove(0);
            return element;
        } catch (Exception e) {
            throw new EmptyHeapException("空堆，获取错误");
        }
    }

    @Override
    public void insertElement(HeapElement element) {
        maxHeap.add(element);
        toggleUp(maxHeap.size() - 1);
    }

    @Override
    public void deleteElement(int elementIndex) {
        if (maxHeap.isEmpty()) {
            return;
        }
        if ((elementIndex > maxHeap.size()) || (elementIndex <= 0)) {
            throw new IndexOutOfBoundsException("Index out of heap range");
        }

        HeapElement element = maxHeap.get(elementIndex);
        maxHeap.set(elementIndex, maxHeap.get(maxHeap.size() - 1));
        maxHeap.remove(maxHeap.size() - 1);

        if (elementIndex>0&&maxHeap.get(elementIndex).getKey() < maxHeap.get((elementIndex - 1) / 2).getKey()) {
            toggleUp(elementIndex);
        } else {
            toggleDown(elementIndex);
        }
    }

    /**
     * 堆的节点
     */
    private static class Node{
        private HeapElement element;
        private Node parent;
        private Node left;
        private Node right;

        public Node(HeapElement element) {
            this.element = element;
        }
    }
}
