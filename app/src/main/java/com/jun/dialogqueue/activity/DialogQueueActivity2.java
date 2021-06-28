package com.jun.dialogqueue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jun.dialogqueue.R;
import com.jun.dialogqueue.util.DialogQueue2;
import com.jun.dialogqueue.util.DialogQueue2.Element;
import com.jun.dialogqueue.util.DialogQueueManager;

import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_ACTIVITY;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_ADVISE_UPGRADE;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_COMPLETE_DATA;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_CUSTOMER;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_LIVE;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_MEETING;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_ME_FRAGMENT_GUIDE1;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_ME_FRAGMENT_GUIDE2;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_REMIND_AUTH;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_SELECT_DOCTOR_ROLE;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_UNION_INVITE;
import static com.jun.dialogqueue.util.DialogQueueManager.TYPE_UNREAD_CARDS;


public class DialogQueueActivity2 extends AppCompatActivity implements DialogQueue2.DialogQueueChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_queue);
        DialogQueueManager.getInstance().init(new DialogQueue2(this));
        getConfig();
        getType1();
        getType2();
        getType3();
        getType4();
        getType5();
        getType6();
        getType7();
        getType8();
        getType9();
        getType10();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogQueueManager.getInstance().release();
    }

    private void getConfig() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getConfig() finish");
                DialogQueueManager.getInstance().config(4, 5000);
                DialogQueueManager.getInstance().countDown();
            }
        }, 0);
    }

    private void getType1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "获取角色接口finish");
                DialogQueueManager.getInstance().register(TYPE_SELECT_DOCTOR_ROLE, null);
            }
        }, 0);
    }

    private void getType2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType2() finish");
                DialogQueueManager.getInstance().register(TYPE_REMIND_AUTH, null);
            }
        }, 0);
    }

    private void getType3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType4() finish");
                DialogQueueManager.getInstance().register(TYPE_COMPLETE_DATA, null);
            }
        }, 0);
    }

    private void getType4() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType6() finish");
                DialogQueueManager.getInstance().register(TYPE_ADVISE_UPGRADE, null);
            }
        }, 0);
    }

    private void getType5() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueueManager.getInstance().register(TYPE_CUSTOMER, null);
            }
        }, 0);
    }

    private void getType6() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType3() finish");
                DialogQueueManager.getInstance().register(TYPE_LIVE, null);
            }
        }, 0);
    }

    private void getType7() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType5() finish");
                DialogQueueManager.getInstance().register(TYPE_ACTIVITY, null);
            }
        }, 0);
    }

    private void getType8() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType7() finish");
                DialogQueueManager.getInstance().register(TYPE_MEETING, null);
            }
        }, 0);
    }

    private void getType9() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType8() finish");
                DialogQueueManager.getInstance().register(TYPE_UNION_INVITE, null);
            }
        }, 0);
    }

    private void getType10() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("iiiiiiiiii", "getType1() finish");
                DialogQueueManager.getInstance().register(TYPE_UNREAD_CARDS, null);
            }
        }, 0);
    }

    private AlertDialog showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle(message).setIcon(R.mipmap.ic_launcher)
                .setMessage(message)
                .setPositiveButton(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }


    @Override
    public void onDialogQueueChanged(Element element) {
        final int type = element.type;
        switch (type) {
            case TYPE_UNION_INVITE:
                DialogQueueManager.getInstance().showing();
                showDialog(String.valueOf(type)).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DialogQueueManager.getInstance().register(TYPE_ME_FRAGMENT_GUIDE1, null);
                        DialogQueueManager.getInstance().dismissAndNext();
                    }
                });
                break;
            case TYPE_ME_FRAGMENT_GUIDE1:
                DialogQueueManager.getInstance().showing();
                showDialog(String.valueOf(type)).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DialogQueueManager.getInstance().register(TYPE_ME_FRAGMENT_GUIDE2, null);
                        DialogQueueManager.getInstance().dismissAndNext();
                    }
                });
                break;
            default:
                DialogQueueManager.getInstance().showing();
                showDialog(String.valueOf(type)).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DialogQueueManager.getInstance().dismissAndNext();
                    }
                });
                break;
        }
    }

//    private void blockApp() {
//        try {
//            DialogQueueManager.getInstance().setHasBlockAppDialog(true);
//            Object currentDialog = DialogQueueManager.getInstance().getCurrentDialog();
//            if (currentDialog != null) {
//                if (currentDialog instanceof Dialog) {
//                    if (((Dialog) currentDialog).isShowing())
//                        ((Dialog) currentDialog).dismiss();
//                } else if (currentDialog instanceof DialogFragment) {
//                    if (((DialogFragment) currentDialog).getDialog() != null && ((DialogFragment) currentDialog).getDialog().isShowing())
//                        ((DialogFragment) currentDialog).dismiss();
//                } else if (currentDialog instanceof ForceLookAliveView) {
//                    ((ForceLookAliveView) currentDialog).dismiss();
//                } else if (currentDialog instanceof ActivityAnimDialog) {
//                    ((ActivityAnimDialog) currentDialog).dismiss();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        DialogQueueManager.getInstance().setDialogQueue(null);
//    }
}