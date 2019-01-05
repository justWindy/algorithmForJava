package org.ray.zoj;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * created by xiuyuan
 * Date: 2018-12-23
 * Time: 14:57
 */
public class ArrayTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);


        System.out.println();

        queue.forEach(obj -> {
            System.out.println("value " +obj);
        });
    }

}
