package com.jun.dialogqueue.util;

import java.util.ArrayList;

public class DialogSequenceQueue {

    /**
     * 弹窗类型同时控制弹窗弹出顺序
     * 注意：不需要弹出的类型一定要移除，否则后续类型弹窗无法弹出
     * 注意：不需要弹出的类型一定要移除，否则后续类型弹窗无法弹出
     * 注意：不需要弹出的类型一定要移除，否则后续类型弹窗无法弹出
     * 添加或删除请谨慎操作
     */
    public enum Type {
        TYPE1, TYPE2, TYPE3, TYPE4, TYPE5, TYPE6, TYPE7
    }

    private ArrayList<Element> queue = new ArrayList<>(Type.values().length);

    private DialogPopUpListener popUpListener;

    public DialogSequenceQueue(DialogPopUpListener listener) {
        for (int i = 0; i < Type.values().length; i++) {
            queue.add(null);//初始状态，以区分element不为空，element不为空表明已确定是否需要弹出
        }
        popUpListener = listener;
    }

    /**
     * 弹窗入队
     * 注意：不论是否要显示弹窗都要添加入队，否则后续类型无法弹出
     * 不显示弹窗使用{@link Element#Element(Type, Object)}入队
     * 显示弹窗使用{@link Element#Element(Type, boolean, Object)}入队
     *
     * @param element 入队元素
     */
    public void add(Element element) {
        if (queue.size() > element.getType().ordinal() && queue.get(element.getType().ordinal()) == null) {
            synchronized (queue) {
                queue.set(element.getType().ordinal(), element);
                boolean show = true;
                if (element.isNeedPop()) {
                    for (int i = 0; i < element.getType().ordinal(); i++) {
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
    }

    /**
     * 获取下一个弹窗信息
     *
     * @param currentType 当前弹窗类型(注意不是下一个要显示的弹窗类型)
     */
    public void next(Type currentType) {
        if (queue.size() > currentType.ordinal()) {
            synchronized (queue) {
                Element element = queue.get(currentType.ordinal());
                if (null != element) element.setPopUp(true);
                for (int i = currentType.ordinal() + 1; i < Type.values().length; i++) {
                    if (null == queue.get(i)) return;
                    if (queue.get(i).isNeedPop() && !queue.get(i).isPopUp()) {
                        popUpListener.onPopUp(queue.get(i));
                        return;
                    }
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
    public boolean hasType(Type type) {
        if (isNotEmpty()) {
            for (int i = 0; i < size(); i++) {
                if (null != queue.get(type.ordinal())) {
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
        private Type type;
        private Object data;

        private boolean isPopUp;
        private boolean needPop;

        public Element(Type type, Object data) {
            this.type = type;
            this.data = data;
        }

        public Element(Type type, boolean needPop, Object data) {
            this.type = type;
            this.needPop = needPop;
            this.data = data;
        }

        public Type getType() {
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