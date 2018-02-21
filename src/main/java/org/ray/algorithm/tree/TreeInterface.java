package org.ray.algorithm.tree;

/**
 * 定义树的普通接口
 *
 * @author lanzhou.hlz
 * created by ray
 * Date: 21/02/2018
 * Time: 16:45
 */
public interface TreeInterface<T> {

    T getRootData();

    int getHeight();

    int getNumberOfNodes();

    boolean isEmpty();

    void clear();

}
