package com.jun.dialogqueue.util;

import android.os.Handler;
import android.util.SparseArray;

/**
 * 弹窗队列管理
 */
public class DialogQueueManager {

    /**
     * 弹窗类型
     */
    public final static int TYPE_SELECT_DOCTOR_ROLE = 1; //选择用户角色弹窗(不占弹窗次数)
    public final static int TYPE_REMIND_AUTH = 2; //提醒认证弹窗(不占弹窗次数)
    public final static int TYPE_COMPLETE_DATA = 3; //完善专业信息弹窗(不占弹窗次数)
    public final static int TYPE_ADVISE_UPGRADE = 4; //建议升级弹窗
    public final static int TYPE_CUSTOMER = 5; //运营弹窗
    public final static int TYPE_LIVE = 6; //直播提醒弹窗
    public final static int TYPE_ACTIVITY = 7; //活动提醒弹窗
    public final static int TYPE_MEETING = 8;//线上会议弹窗
    public final static int TYPE_UNION_INVITE = 9;//医患之家邀请函弹窗
    public final static int TYPE_UNREAD_CARDS = 10; //未读卡卷弹窗(不占弹窗次数)
    public final static int TYPE_ME_FRAGMENT_GUIDE1 = 11; //医患之家入口提示浮层(不占弹窗次数)
    public final static int TYPE_ME_FRAGMENT_GUIDE2 = 12; //切换医患之家引导浮层(不占弹窗次数)

    /**
     * 弹窗队列
     */
    private DialogQueue2 mQueue;

    /**
     * 剩余弹窗次数(初始值为最大可弹窗数)
     */
    private int mRemainingPopUpTimes;

    /**
     * 最长等待时间
     */
    private int mMaxWaitingTime;

    /**
     * 达到最长等待时间
     */
    private boolean mReachMaxWaitingTime;

    /**
     * 是否有弹窗正在显示
     */
    private boolean mIsShowing;

    /**
     * 是否有阻断APP流程的弹窗出现(如强制升级弹窗)
     */
    private boolean mIsBlock;

    /**
     * 标识是否在医生圈
     */
    private boolean mNotCircle;

    /**
     * 弹窗配置(type-order映射)
     */
    private SparseArray<Integer> mTypeOrderArray = new SparseArray<>();

    private Runnable mRunnable = null;

    private Handler mHandler = new Handler();

    private final int DEFAULT_ORDER = 1000;

    private DialogQueueManager() {
    }

    private static class Holder {
        static DialogQueueManager instance = new DialogQueueManager();
    }

    public static DialogQueueManager getInstance() {
        return Holder.instance;
    }

    public void init(DialogQueue2 queue) {
        mQueue = queue;
        initDefaultOrder();
    }

    /**
     * 配置弹窗
     */
    public void config(int remainingPopUpTimes, int maxWaitingTime) {
        mRemainingPopUpTimes = remainingPopUpTimes;
        mMaxWaitingTime = maxWaitingTime;
        mTypeOrderArray.append(TYPE_REMIND_AUTH, 10);
        mTypeOrderArray.append(TYPE_COMPLETE_DATA, 20);
        mTypeOrderArray.append(TYPE_ADVISE_UPGRADE, 30);
        mTypeOrderArray.append(TYPE_CUSTOMER, 40);
        mTypeOrderArray.append(TYPE_LIVE, 50);
        mTypeOrderArray.append(TYPE_ACTIVITY, 1000);
        mTypeOrderArray.append(TYPE_MEETING, 1000);
        mTypeOrderArray.append(TYPE_UNION_INVITE, 1000);
        correctOrder();
    }

    /**
     * 显示弹窗时，记录显示状态
     */
    public void showing() {
        mIsShowing = true;
    }

    /**
     * 阻断弹窗流程
     */
    public void block() {
        mIsBlock = true;
    }

    /**
     * 标识是否在医生圈
     */
    public void setNotCircle(boolean notCircle) {
        mNotCircle = notCircle;
    }

    public void release() {
        mQueue = null;
        mRemainingPopUpTimes = 0;
        mMaxWaitingTime = 0;
        mReachMaxWaitingTime = false;
        mIsShowing = false;
        mIsBlock = false;
        mTypeOrderArray = new SparseArray<>();
        if (null != mRunnable) mHandler.removeCallbacks(mRunnable);
    }

    public void countDown() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mReachMaxWaitingTime = true;
                DialogQueueManager.this.next();
            }
        };
        mHandler.postDelayed(mRunnable, mMaxWaitingTime);
    }

    /**
     * 注册进入弹窗队列
     */
    public void register(int type, Object data) {
        if (null != mQueue && !mQueue.exists(type)) {
            if (mTypeOrderArray.get(type) != null) {
                mQueue.enqueue(new DialogQueue2.Element(type, mTypeOrderArray.get(type), data));
            } else {
                mQueue.enqueue(new DialogQueue2.Element(type, DEFAULT_ORDER, data));
            }
            next();
        }
    }

    public void dismissAndNext() {
        mIsShowing = false;
        next();
    }

    public void next() {
        if (null != mQueue && !mNotCircle && !mIsBlock && !mIsShowing) {//判断是否正在显示强升弹窗
            DialogQueue2.Element first = mQueue.first();
            if (null != first) {
                if ((!consumeTimes(first.type) || mRemainingPopUpTimes > 0) && (TYPE_SELECT_DOCTOR_ROLE == first.type || mReachMaxWaitingTime)) {
                    mQueue.dequeue();
                    if (consumeTimes(first.type))
                        countDownPopUpTimes();
                }
            }
        }
    }

    /**
     * 初始化本地管理弹窗配置
     */
    private void initDefaultOrder() {
        mTypeOrderArray.append(TYPE_SELECT_DOCTOR_ROLE, 5);
        mTypeOrderArray.append(TYPE_UNREAD_CARDS, 6);
        mTypeOrderArray.append(TYPE_ME_FRAGMENT_GUIDE1, 7);
        mTypeOrderArray.append(TYPE_ME_FRAGMENT_GUIDE2, 8);
    }

    /**
     * 修正数据
     */
    private void correctOrder() {
        if (null != mQueue && mQueue.isNotEmpty()) {
            mQueue.correctOrder(mTypeOrderArray);
        }
    }

    private void countDownPopUpTimes() {
        mRemainingPopUpTimes--;
    }

    /**
     * 判断type类型弹窗是否消耗弹窗次数
     */
    private boolean consumeTimes(int type) {
        if (TYPE_SELECT_DOCTOR_ROLE == type || TYPE_REMIND_AUTH == type || TYPE_COMPLETE_DATA == type || TYPE_UNREAD_CARDS == type || TYPE_ME_FRAGMENT_GUIDE1 == type || TYPE_ME_FRAGMENT_GUIDE2 == type)
            return false;
        return true;
    }

}