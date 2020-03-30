package com.jun.dialogqueue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jun.dialogqueue.R;
import com.jun.dialogqueue.util.DialogSequenceQueue;

import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE1;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE2;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE3;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE4;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE5;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE6;
import static com.jun.dialogqueue.util.DialogSequenceQueue.TYPE.TYPE7;

public class DialogSequenceQueueActivity extends AppCompatActivity implements DialogSequenceQueue.DialogPopUpListener {

    private DialogSequenceQueue sequenceQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_queue);
        sequenceQueue = new DialogSequenceQueue(this);
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
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE1, null));
            }
        }, 5000);
    }

    private void getType2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE2, true, null));
            }
        }, 15000);
    }

    private void getType3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE3, true, null));
            }
        }, 6000);
    }

    private void getType4() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE4, null));
            }
        }, 5000);
    }

    private void getType5() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE5, true, null));
            }
        }, 4000);
    }

    private void getType6() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE6, true, null));
            }
        }, 10000);
    }

    private void getType7() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sequenceQueue.add(new DialogSequenceQueue.Element(TYPE7, true, null));
            }
        }, 3000);
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
    public void onPopUp(DialogSequenceQueue.Element element) {
        switch (element.getType()) {
            case TYPE1:
                showDialog("1").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE1);
                    }
                });
                break;
            case TYPE2:
                showDialog("2").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE2);
                    }
                });
                break;
            case TYPE3:
                showDialog("3").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE3);
                    }
                });
                break;
            case TYPE4:
                showDialog("4").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE4);
                    }
                });
                break;
            case TYPE5:
                showDialog("5").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE5);
                    }
                });
                break;
            case TYPE6:
                showDialog("6").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE6);
                    }
                });
                break;
            case TYPE7:
                showDialog("7").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sequenceQueue.next(TYPE7);
                    }
                });
                break;
        }
    }
}