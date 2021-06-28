package com.jun.dialogqueue.util;

import android.util.SparseArray;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class DialogQueue2 {


    /**
     * 弹窗队列
     */
    private LinkedList<Element> mList = new LinkedList();

    /**
     * 队列元素变动监听
     */
    private DialogQueueChangeListener mListener;

    public DialogQueue2(DialogQueueChangeListener listener) {
        mListener = listener;
    }

    /**
     * 进队
     */
    public synchronized void enqueue(Element element) {
        mList.add(element);
        Collections.sort(mList, new DialogQueueComparator());
    }

    /**
     * 出队
     */
    public synchronized void dequeue() {
        if (isNotEmpty()) {
            Element element = mList.get(0);
            mList.removeFirst();
            if (null != mListener) mListener.onDialogQueueChanged(element);
        }
    }

    /**
     * 获取队首元素
     */
    public Element first() {
        try {
            return mList.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断队列非空
     */
    public boolean isNotEmpty() {
        return !mList.isEmpty();
    }

    /**
     * 获取队列长度
     */
    public int size() {
        return mList.size();
    }

    /**
     * 判断队列里是否已有type类型的Dialog
     */
    public boolean exists(int type) {
        if (isNotEmpty()) {
            for (int i = 0; i < size(); i++) {
                if (type == mList.get(i).type) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取到配置信息后修正数据
     */
    public void correctOrder(SparseArray<Integer> array) {
        for (int i = 0; i < size(); i++) {
            Element element = mList.get(i);
            if (element != null && array.get(element.type) != null) {
                element.order = array.get(element.type);
            }
        }
        Collections.sort(mList, new DialogQueueComparator());
    }

    public interface DialogQueueChangeListener {
        void onDialogQueueChanged(Element element);
    }

    private class DialogQueueComparator implements Comparator<Element> {
        @Override
        public int compare(Element o1, Element o2) {
            return o1.order - o2.order;
        }
    }

    /**
     * 弹窗元素
     */
    public static class Element {
        public int type;//类型
        public int order;//顺序
        public Object data;//数据

        public Element(int type, int order, Object data) {
            this.type = type;
            this.order = order;
            this.data = data;
        }
    }
}