package com.jun.dialogqueue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jun.dialogqueue.R;
import com.jun.dialogqueue.util.DialogQueue;

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
                DialogQueue.Element element = new DialogQueue.Element(queue.types[0], null);
                queue.add(element);
            }
        }, 3000);
    }

    private void getType2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[1], null);
                queue.add(element);
            }
        }, 3000);
    }

    private void getType3() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[2], null);
                queue.add(element);
            }
        }, 6000);
    }

    private void getType4() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[3], null);
                queue.add(element);
            }
        }, 1000);
    }

    private void getType5() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[4], null);
                queue.add(element);
            }
        }, 4000);
    }

    private void getType6() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[5], null);
                queue.add(element);
            }
        }, 11000);
    }

    private void getType7() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogQueue.Element element = new DialogQueue.Element(queue.types[6], null);
                queue.add(element);
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
                final DialogQueue.Type type = element.getType();
                if (type.orderValue == queue.types[0].orderValue) {
                    showDialog("1").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                } else if (type.orderValue == queue.types[1].orderValue) {
                    showDialog("2").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(element.getType());
                        }
                    });
                } else if (type.orderValue == queue.types[2].orderValue) {
                    showDialog("3").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                } else if (type.orderValue == queue.types[3].orderValue) {
                    showDialog("4").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                } else if (type.orderValue == queue.types[4].orderValue) {
                    showDialog("5").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                } else if (type.orderValue == queue.types[5].orderValue) {
                    showDialog("6").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                } else if (type.orderValue == queue.types[6].orderValue) {
                    showDialog("7").setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            queue.finish(type);
                        }
                    });
                }
            }
        }, 500);
    }
}