package com.jun.dialogqueue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jun.dialogqueue.R;
import com.jun.dialogqueue.util.DialogQueue;

import static com.jun.dialogqueue.util.DialogQueue.TYPE4;
import static com.jun.dialogqueue.util.DialogQueue.TYPE2;
import static com.jun.dialogqueue.util.DialogQueue.TYPE5;
import static com.jun.dialogqueue.util.DialogQueue.TYPE1;
import static com.jun.dialogqueue.util.DialogQueue.TYPE6;
import static com.jun.dialogqueue.util.DialogQueue.TYPE7;
import static com.jun.dialogqueue.util.DialogQueue.TYPE3;

public class DialogQueueActivity extends AppCompatActivity implements DialogQueue.PopUpListener {

    private DialogQueue queue;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_queue);
        handler = new Handler();
        queue = new DialogQueue(this);
        getType1();
        getType2();
        getType3();
        getType4();
        getType5();
        getType6();
        getType7();
    }

    private void getType1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE1, null);
                queue.addOrderExe(element);
                queue.deleteOrderExe();
            }
        }, 3000);
    }

    private void getType2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE2, null);
                queue.addOrderExe(element);
                queue.deleteOrderExe();
            }
        }, 3000);
    }

    private void getType3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE3, null);
                queue.addUnOrderExe(element);
            }
        }, 6000);
    }

    private void getType4() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE4, null);
                queue.addUnOrderExe(element);
            }
        }, 1000);
    }

    private void getType5() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE5, null);
                queue.addUnOrderExe(element);
            }
        }, 4000);
    }

    private void getType6() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE6, null);
                queue.addUnOrderExe(element);
            }
        }, 11000);
    }

    private void getType7() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(TYPE7, null);
                queue.addUnOrderExe(element);
            }
        }, 5000);
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
    public void onPopUp(final DialogQueue.Element element) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (element.getType()) {
                    case TYPE1:
                        showDialog("1").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeOrderExe();
                            }
                        });
                        break;
                    case TYPE2:
                        showDialog("2").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeOrderExe();
                            }
                        });
                        break;
                    case TYPE3:
                        showDialog("3").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeUnOrderExe();
                            }
                        });
                        break;
                    case TYPE4:
                        showDialog("4").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeUnOrderExe();
                            }
                        });
                        break;
                    case TYPE5:
                        showDialog("5").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeUnOrderExe();
                            }
                        });
                        break;
                    case TYPE6:
                        showDialog("6").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeUnOrderExe();
                            }
                        });
                        break;
                    case TYPE7:
                        showDialog("7").setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                queue.completeUnOrderExe();
                            }
                        });
                        break;
                }
            }
        }, 500);
    }
}