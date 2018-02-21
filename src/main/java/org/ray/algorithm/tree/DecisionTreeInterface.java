package org.ray.algorithm.tree;

/**
 * created by ray
 * Date: 21/02/2018
 * Time: 18:38
 */
public interface DecisionTreeInterface<T> extends BinaryTreeInterface<T> {

    T getCurrentData();

    void setCurrentData();

    void setAnswers(T answerForNo, T answerForYes);

    boolean isAnswer();

    void advanceToNo();

    void advanceToYes();

    void reset();

}
