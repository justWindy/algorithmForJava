package org.ray.algorithm.tree;

import java.util.Iterator;

/**
 * 定义树的遍历接口, 包括前序, 中序和后序遍历
 * 同时定义层次遍历
 *
 * @author lanzhou.hlz
 * created by ray
 * Date: 21/02/2018
 * Time: 16:49
 */
public interface TreeIteratorInterface<T> {

    Iterator<T> getPreorderIterator();

    Iterator<T> getPostorderIterator();

    Iterator<T> getInorderIterator();

    Iterator<T> getLevelOrderIterator();
}
