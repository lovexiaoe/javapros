package net.zhaoyu.javapros.j2se.algorithmdesign.DataStructures.Heaps;

/**
 * @Description: 堆节点
 * @Author: zhaoyu
 * @Date: 2020/12/29
 */
public class HeapElement {
    /** 基本类型的数字 */
    private final double key;
    /** 任意的不可变对象，用户使用的附加信息。 */
    private final Object additionalInfo;

    /**
     * 构造方法
     * @param key
     * @param additionalInfo
     * @return
     */
    public HeapElement(double key, Object additionalInfo) {
        this.key = key;
        this.additionalInfo = additionalInfo;
    }

    public HeapElement(int key, Object info) {
        this.key = key;
        this.additionalInfo = info;
    }

    public HeapElement(Integer key, Object info) {
        this.key = key;
        this.additionalInfo = info;
    }

    public HeapElement(Double key, Object info) {
        this.key = key;
        this.additionalInfo = info;
    }

    public HeapElement(double key) {
        this.key = key;
        this.additionalInfo = null;
    }

    public HeapElement(int key) {
        this.key = key;
        this.additionalInfo = null;
    }

    public HeapElement(Integer key) {
        this.key = key;
        this.additionalInfo = null;
    }

    public HeapElement(Double key) {
        this.key = key;
        this.additionalInfo = null;
    }

    /**
     * Getters
     */
    public Object getInfo() {
        return additionalInfo;
    }

    public double getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Key: " + key + " - " + additionalInfo.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (!(o instanceof HeapElement)) {
                return false;
            }
            HeapElement otherHeapElement = (HeapElement) o;
            return (this.key == otherHeapElement.key)
                    && (this.additionalInfo.equals(otherHeapElement.additionalInfo));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (int) key;
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result;
    }
}
