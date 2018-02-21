package org.ray.algorithm.tree;

/**
 * created by ray
 * Date: 21/02/2018
 * Time: 18:32
 */
public interface BinaryTreeInterface<T> extends TreeInterface<T>, TreeIteratorInterface<T> {

    void setTree(T rootData);

    void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree);

}
