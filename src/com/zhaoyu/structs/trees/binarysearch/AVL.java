package com.zhaoyu.structs.trees.binarysearch;

/**
 * 平衡二叉树，又称为AVL树，是一颗空树或者左右子树的高度差绝对值不超过1，并且左右子树都是一颗平衡二叉树。他是一种特殊的
 * 二叉查找树。有着二叉查找树的所有特性。
 *
 * 高度为h的AVL树，节点N最多为2的h次方减1，
 *
 * 查找，插入，和删除在平均和最坏情况下都是O(log n)
 *
 * 其他操作主要有左旋，右旋，双向旋转（先左后右，先右后左）。为什么会有这些操作？因为平衡二叉树要保持树的平衡，只有树的结构平衡
 * 才会保持一定的树高，这样才会有高效的操作，减少操作时间。所以在树的插入和删除过程中引入和左旋，右旋等保持树平衡的旋转操作。
 *
 *
 */
public class AVL {
}
