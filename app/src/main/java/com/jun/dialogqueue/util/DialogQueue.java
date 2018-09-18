package com.jun.dialogqueue.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DialogQueue {

    /**
     * 弹窗类型
     */
    public final static int TYPE1 = 1;
    public final static int TYPE2 = 2;
    public final static int TYPE3 = 3;
    public final static int TYPE4 = 4;
    public final static int TYPE5 = 5;
    public final static int TYPE6 = 6;
    public final static int TYPE7 = 7;

    public DialogQueue(PopUpListener popUpListener) {
        this.popUpListener = popUpListener;
    }

    /**
     * 弹窗弹出监听
     */
    private PopUpListener popUpListener;
    /**
     * 顺序弹窗数量
     */
    volatile private int orderExecuteNum = 2;
    /**
     * 顺序弹窗列表
     */
    private List<Element> orderExecuteList = new ArrayList<>();
    /**
     * 顺序弹窗排序
     */
    private OrderListComparator comparator = new OrderListComparator();
    /**
     * 非顺序弹窗列表
     */
    private List<Element> unOrderExecuteList = new ArrayList<>();
    /**
     * 当前正在显示的弹窗元素
     */
    private Element currentElement;
    /**
     * 非顺序弹窗列表是否可以开始弹出
     */
    boolean canUnOrderExe = false;

    /**
     * 顺序弹窗需要弹出时添加
     */
    public void addOrderExe(Element element) {
        synchronized (orderExecuteList) {
            orderExecuteList.add(element);
            if (orderExecuteList.size() == orderExecuteNum) {
                Collections.sort(orderExecuteList, comparator);
                executeOrder();
            }
        }
    }

    /**
     * 顺序弹窗不需要弹出时移除
     */
    public void deleteOrderExe() {
        synchronized (orderExecuteList) {
            orderExecuteNum--;
            if (orderExecuteList.size() == orderExecuteNum) {
                Collections.sort(orderExecuteList, comparator);
                executeOrder();
            }
        }
    }

    /**
     * 顺序弹窗关闭
     */
    public void completeOrderExe() {
        synchronized (orderExecuteList) {
            orderExecuteList.remove(currentElement);
            currentElement = null;
            executeOrder();
        }
    }

    /**
     * 顺序弹窗弹出
     */
    private void executeOrder() {
        if (orderExecuteList.size() > 0) {
            currentElement = orderExecuteList.get(0);
            popUpListener.onPopUp(currentElement);
        } else {
            canUnOrderExe = true;
            executeUnOrder();
        }
    }

    /**
     * 非顺序弹窗需要弹出时添加
     */
    public void addUnOrderExe(Element element) {
        synchronized (unOrderExecuteList) {
            unOrderExecuteList.add(element);
            if (canUnOrderExe && currentElement == null) {
                executeUnOrder();
            }
        }
    }

    /**
     * 非顺序弹窗关闭
     */
    public void completeUnOrderExe() {
        synchronized (unOrderExecuteList) {
            unOrderExecuteList.remove(currentElement);
            currentElement = null;
            executeUnOrder();
        }
    }

    /**
     * 非顺序弹窗弹出
     */
    private void executeUnOrder() {
        if (unOrderExecuteList.size() > 0) {
            currentElement = unOrderExecuteList.get(0);
            popUpListener.onPopUp(currentElement);
        }
    }

    /**
     * 判断非顺序弹窗里是否已有type类型的元素
     */
    public boolean hasType(int type) {
        if (unOrderExecuteList.size() > 0) {
            for (int i = 0; i < unOrderExecuteList.size(); i++) {
                if (unOrderExecuteList.get(i).type == type) {
                    return true;
                }
            }
        }
        return false;
    }

    class OrderListComparator implements Comparator<Element> {
        @Override
        public int compare(Element o1, Element o2) {
            return o1.type - o2.type;
        }
    }

    /**
     * 弹出弹窗监听
     */
    public interface PopUpListener {
        void onPopUp(Element element);
    }

    /**
     * 弹窗元素
     */
    public static class Element {
        private int type;
        private Object data;

        public Element(int type, Object data) {
            this.type = type;
            this.data = data;
        }

        public int getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }
}