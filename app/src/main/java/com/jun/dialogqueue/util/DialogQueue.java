package com.jun.dialogqueue.util;

import java.util.ArrayList;
import java.util.List;

public class DialogQueue {

    public enum Type {

        TYPE1(true),
        TYPE2(true),
        TYPE3(true),
        TYPE4(true),
        TYPE5,
        TYPE6,
        TYPE7;


        public boolean isOrder;//是否是顺序弹窗,顺序弹窗定义顺序决定弹出顺序

        Type(boolean isOrder) {
            this.isOrder = isOrder;
        }

        Type() {
        }
    }

    public DialogQueue(PopupListener popupListener) {
        for (Type type : Type.values()) {
            if (type.isOrder) {
                orderedQueue.add(null);//初始状态,以区分element不为空,element不为空表明已确定是否需要弹出
                orderedCount++;
            }
        }
        this.popupListener = popupListener;
    }

    /**
     * 弹窗弹出监听
     */
    private PopupListener popupListener;
    /**
     * 顺序弹窗列表
     */
    private List<Element> orderedQueue = new ArrayList<>();
    /**
     * 非顺序弹窗列表
     */
    private List<Element> unorderedQueue = new ArrayList<>();
    /**
     * 当前正在显示的弹窗元素(防止正在显示弹窗还未关闭,enqueue后重复显示)
     */
    private Element currentElement;
    /**
     * 非顺序弹窗列表是否可以开始弹出
     */
    private boolean canUnorderedPopup = false;
    /**
     * 顺序弹窗数量
     */
    private int orderedCount;

    /**
     * 添加弹窗到队列
     *
     * @param element 弹窗元素
     */
    public void enqueue(Element element) {
        if (element.type.isOrder) {//顺序弹窗需要弹出时添加
            if (orderedQueue.size() > element.type.ordinal()) {
                synchronized (orderedQueue) {
                    orderedQueue.set(element.type.ordinal(), element);
                    if (element.needPopup) {
                        boolean show = true;
                        for (int i = 0; i < element.type.ordinal(); i++) {
                            if (null == orderedQueue.get(i) || null != orderedQueue.get(i) && orderedQueue.get(i).needPopup && !orderedQueue.get(i).isPopup) {
                                show = false;
                                break;
                            }
                        }
                        if (element.needPopup && show) {
                            popupListener.onPopup(element);
                        }
                    } else {
                        if (orderedQueue.size() > element.type.ordinal() + 1)
                            enqueue(orderedQueue.get(element.type.ordinal() + 1));
                    }
                }
            }
        } else {//非顺序弹窗需要弹出时添加
            if (!exists(element.type)) {
                synchronized (unorderedQueue) {
                    unorderedQueue.add(element);
                    if (canUnorderedPopup && currentElement == null) {
                        popupUnOrder();
                    }
                }
            }
        }
    }

    /**
     * 弹窗关闭处理
     *
     * @param type 弹窗类型
     */
    public void dequeue(Type type) {
        if (type.isOrder) {//顺序弹窗关闭
            if (orderedQueue.size() > type.ordinal()) {
                synchronized (orderedQueue) {
                    Element element = orderedQueue.get(type.ordinal());
                    if (null != element) element.isPopup = true;
                    for (int i = type.ordinal() + 1; i < orderedCount; i++) {
                        if (null == orderedQueue.get(i)) return;
                        if (orderedQueue.get(i).needPopup && !orderedQueue.get(i).isPopup) {
                            popupListener.onPopup(orderedQueue.get(i));
                            return;
                        }
                    }
                    for (Element e : orderedQueue) {
                        if (e == null) return;//后边还有未请求回来的顺序弹窗
                    }
                    canUnorderedPopup = true;
                    popupUnOrder();
                }
            }
        } else {//非顺序弹窗关闭
            synchronized (unorderedQueue) {
                unorderedQueue.remove(currentElement);
                currentElement = null;
                popupUnOrder();
            }
        }
    }

    /**
     * 非顺序弹窗弹出
     */
    private void popupUnOrder() {
        if (unorderedQueue.size() > 0) {
            currentElement = unorderedQueue.get(0);
            popupListener.onPopup(currentElement);
        }
    }

    /**
     * 判断非顺序弹窗里是否已有type类型的元素
     */
    private boolean exists(Type type) {
        if (unorderedQueue.size() > 0) {
            for (int i = 0; i < unorderedQueue.size(); i++) {
                if (unorderedQueue.get(i).type.ordinal() == type.ordinal()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 弹出弹窗监听
     */
    public interface PopupListener {
        void onPopup(Element element);
    }

    /**
     * 弹窗元素
     */
    public static class Element {
        public Type type;//弹窗类型
        public boolean needPopup = true;//弹窗是否需要弹出
        public Object data;//弹窗数据
        public boolean isPopup;//弹窗是否已经弹出过

        /**
         * 顺序弹窗不需要弹窗是调用此构造方法添加元素,needPop传入false,其他情况均使用{@link Element#Element(Type, Object)}
         */
        public Element(Type type, boolean needPopup, Object data) {
            this.type = type;
            this.needPopup = needPopup;
            this.data = data;
        }

        public Element(Type type, Object data) {
            this.type = type;
            this.data = data;
        }
    }
}