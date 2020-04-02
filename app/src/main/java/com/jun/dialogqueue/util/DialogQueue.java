package com.jun.dialogqueue.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DialogQueue {

    public Type[] types = new Type[]{
            new Type("TYPE1", true, 0),
            new Type("TYPE2", true, 1),
            new Type("TYPE3", false, 2),
            new Type("TYPE4", false, 3),
            new Type("TYPE5", false, 4),
            new Type("TYPE6", false, 5),
            new Type("TYPE7", false, 6),
    };


    public DialogQueue(PopUpListener popUpListener) {
        this.popUpListener = popUpListener;
        for (int i = 0; i < types.length; i++) {
            if (types[i].isOrder) orderExecuteNum++;
        }
    }

    /**
     * 弹窗弹出监听
     */
    private PopUpListener popUpListener;
    /**
     * 顺序弹窗数量
     */
    volatile private int orderExecuteNum;
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
    private boolean canUnOrderExe = false;


    /**
     * 添加弹窗到队列
     *
     * @param element 弹窗元素
     */
    public void add(Element element) {
        if (element.type.isOrder) {//顺序弹窗需要弹出时添加
            synchronized (orderExecuteList) {
                orderExecuteList.add(element);
                if (orderExecuteList.size() == orderExecuteNum) {
                    Collections.sort(orderExecuteList, comparator);
                    popupOrder();
                }
            }
        } else {//非顺序弹窗需要弹出时添加
            synchronized (unOrderExecuteList) {
                unOrderExecuteList.add(element);
                if (canUnOrderExe && currentElement == null) {
                    popupUnOrder();
                }
            }
        }
    }

    /**
     * 弹窗关闭处理
     *
     * @param type 弹窗类型
     */
    public void finish(Type type) {
        if (type.isOrder) {//顺序弹窗关闭
            synchronized (orderExecuteList) {
                orderExecuteList.remove(currentElement);
                currentElement = null;
                popupOrder();
            }
        } else {//非顺序弹窗关闭
            synchronized (unOrderExecuteList) {
                unOrderExecuteList.remove(currentElement);
                currentElement = null;
                popupUnOrder();
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
                popupOrder();
            }
        }
    }

    /**
     * 顺序弹窗弹出
     */
    private void popupOrder() {
        if (orderExecuteList.size() > 0) {
            currentElement = orderExecuteList.get(0);
            popUpListener.onPopUp(currentElement);
        } else {
            canUnOrderExe = true;
            popupUnOrder();
        }
    }


    /**
     * 非顺序弹窗弹出
     */
    private void popupUnOrder() {
        if (unOrderExecuteList.size() > 0) {
            currentElement = unOrderExecuteList.get(0);
            popUpListener.onPopUp(currentElement);
        }
    }

    /**
     * 判断非顺序弹窗里是否已有type类型的元素
     */
    public boolean hasType(Type type) {
        if (unOrderExecuteList.size() > 0) {
            for (int i = 0; i < unOrderExecuteList.size(); i++) {
                if (unOrderExecuteList.get(i).type.orderValue == type.orderValue) {
                    return true;
                }
            }
        }
        return false;
    }

    class OrderListComparator implements Comparator<Element> {
        @Override
        public int compare(Element o1, Element o2) {
            return o1.type.orderValue - o2.type.orderValue;
        }
    }

    /**
     * 弹出弹窗监听
     */
    public interface PopUpListener {
        void onPopUp(Element element);
    }

    /**
     * 弹窗类型
     */
    public class Type {
        public String name;
        public boolean isOrder;
        public int orderValue;

        public Type(String name, boolean isOrder, int orderValue) {
            this.name = name;
            this.isOrder = isOrder;
            this.orderValue = orderValue;
        }

    }

    /**
     * 弹窗元素
     */
    public static class Element {
        private Type type;
        private Object data;

        public Element(Type type, Object data) {
            this.type = type;
            this.data = data;
        }

        public Type getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }
}