package com.example.sample1;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.FixedHeightViewSizer;
import com.google.ar.sceneform.rendering.FixedWidthViewSizer;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ar_functionality {
//    TransformableNode model;
//    ar_functionality(TransformableNode model1){
//        this.model=model1;
//    }

    public void autoRotation(TransformableNode model, CardView cardHeart, CardView cardView){
        new Handler().post(new Runnable() {
            public void run() {
                // count down timer start
                CountDownTimer countDownTimer= new CountDownTimer(1000000000, 100) {
                    int i=0;
                    public void onTick(long millisUntilFinished) {
                        //mTimeLeftInMillis=millisUntilFinished;
                        model.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), i+10f));
                        i=i+10;
                    }

                    public void onFinish() {
                        model.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0), 0f));
                    }
                }.start();
                cardHeart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countDownTimer.cancel();
                    }
                });
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countDownTimer.start();
                    }
                });

                model.setOnTapListener(new Node.OnTapListener() {
                    boolean start=false;
                    @Override
                    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                        if(!start){
                            countDownTimer.start();
                            start=true;
                        }else {
                            countDownTimer.cancel();
                            start=false;
                        }
                    }
                });
            }
        });
    }
    public void addNode(HitResult hitResult, TransformableNode model1, ViewRenderable viewRenderable, Vector3 vector3, FixedHeightViewSizer fixedHeightViewSizer,
                        FixedWidthViewSizer fixedWidthViewSizer, String nodeName, ArFragment arFragment){
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());

        model.getScaleController().setMinScale(0.03f);
        model.getScaleController().setMaxScale(0.04f);
        model.setLocalScale(new Vector3(0.02f,0.07f,0.02f));
        model.setLocalPosition(vector3);
        model.setParent(anchorNode);

        Node titleNode = new Node();
        titleNode.setParent(anchorNode);
        model.setRenderable(viewRenderable);
        titleNode.setName(nodeName);
//        titleNode.setLocalPosition(vector3);
        titleNode.setEnabled(false);
//        titleNode.setOnTouchListener(new Node.OnTouchListener() {
//            @Override
//            public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
//                String name=hitTestResult.getNode().getName();
//                System.out.print("Node clicked "+name);
//                Toast.makeText(arFragment.getContext(), name, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        viewRenderable.setSizer(fixedHeightViewSizer);
        viewRenderable.setSizer(fixedWidthViewSizer);
        viewRenderable.setShadowCaster(false);
        titleNode.setEnabled(true);
    }

}
