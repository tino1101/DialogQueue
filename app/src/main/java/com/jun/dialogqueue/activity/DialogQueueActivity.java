package com.jun.dialogqueue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jun.dialogqueue.R;
import com.jun.dialogqueue.util.DialogQueue;
import com.jun.dialogqueue.util.DialogQueue.Element;
import com.jun.dialogqueue.util.DialogQueue.Type;

import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE1;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE2;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE3;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE4;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE5;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE6;
import static com.jun.dialogqueue.util.DialogQueue.Type.TYPE7;

public class DialogQueueActivity extends AppCompatActivity implements DialogQueue.PopupListener {

    private DialogQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_queue);
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
                queue.enqueue(new Element(TYPE1, null));
            }
        }, 4000);
    }

    private void getType2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE2, false, null));
            }
        }, 7000);
    }

    private void getType3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE3, null));
            }
        }, 3000);
    }

    private void getType4() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE4, false,null));
            }
        }, 8000);
    }

    private void getType5() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE5, null));
            }
        }, 6000);
    }

    private void getType6() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE6, null));
            }
        }, 1000);
    }

    private void getType7() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.enqueue(new Element(TYPE7, null));
            }
        }, 10000);
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
    public void onPopup(Element element) {
        final Type type = element.type;
        switch (type) {
            case TYPE1:
                showDialog("1").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE2:
                showDialog("2").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE3:
                showDialog("3").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE4:
                showDialog("4").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE5:
                showDialog("5").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE6:
                showDialog("6").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
            case TYPE7:
                showDialog("7").setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        queue.dequeue(type);
                    }
                });
                break;
        }
    }
}