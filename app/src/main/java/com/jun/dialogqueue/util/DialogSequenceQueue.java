package com.jun.dialogqueue.util;

import java.util.ArrayList;

public class DialogSequenceQueue {

    private final int count = 7;

    /**
     * 弹窗类型同时控制弹窗弹出顺序
     */
    public final static int TYPE0 = 0;
    public final static int TYPE1 = 1;
    public final static int TYPE2 = 2;
    public final static int TYPE3 = 3;
    public final static int TYPE4 = 4;
    public final static int TYPE5 = 5;
    public final static int TYPE6 = 6;

    private ArrayList<Element> queue;

    private DialogPopUpListener popUpListener;

    public DialogSequenceQueue(DialogPopUpListener popUpListener) {
        queue = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            queue.add(null);
        }
        this.popUpListener = popUpListener;
    }

    /**
     * 弹窗入队
     */
    public void add(Element element) {
        synchronized (queue) {
            queue.set(element.getType(), element);
            boolean show = true;
            if (element.isNeedPop()) {
                for (int i = 0; i < element.getType(); i++) {
                    if (null == queue.get(i) || null != queue.get(i) && queue.get(i).isNeedPop() && !queue.get(i).isPopUp()) {
                        show = false;
                        break;
                    }
                }
                if (show) {
                    popUpListener.onPopUp(element);
                }
            }
        }
    }

    /**
     * 获取下一个弹窗信息
     */
    public void next(int type) {
        synchronized (queue) {
            Element element = queue.get(type);
            if (null != element) element.setPopUp(true);
            for (int i = type; i < count; i++) {
                if (null == queue.get(i)) return;
                if (queue.get(i).isNeedPop() && !queue.get(i).isPopUp()) {
                    popUpListener.onPopUp(queue.get(i));
                    return;
                }
            }
        }
    }

    /**
     * 判断队列非空
     */
    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    /**
     * 获取队列长度
     */
    public int size() {
        return queue.size();
    }

    /**
     * 判断队列里是否已有type类型的Dialog
     */
    public boolean hasType(int type) {
        if (isNotEmpty()) {
            for (int i = 0; i < size(); i++) {
                if (null != queue.get(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 弹出弹窗监听
     */
    public interface DialogPopUpListener {
        void onPopUp(Element element);
    }

    /**
     * 弹窗元素
     */
    public static class Element {
        private int type;
        private Object data;

        private boolean isPopUp;
        private boolean needPop;

        public Element(int type, boolean needPop, Object data) {
            this.type = type;
            this.data = data;
            this.needPop = needPop;
        }

        public int getType() {
            return type;
        }

        public Object getData() {
            return data;
        }

        public boolean isPopUp() {
            return isPopUp;
        }

        public void setPopUp(boolean popUp) {
            isPopUp = popUp;
        }

        public boolean isNeedPop() {
            return needPop;
        }
    }
}