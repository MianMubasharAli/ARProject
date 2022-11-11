package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//package com.google.ar.sceneform.samples.gltf;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.Earth;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.FixedHeightViewSizer;
import com.google.ar.sceneform.rendering.FixedWidthViewSizer;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.rendering.ViewSizer;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {

    private ArFragment arFragment;
    private Renderable model;
    private Renderable model2;
    private ViewRenderable viewRenderable;
    private ViewRenderable topViewModel;
    private ViewRenderable rightViewModel;
    private  ViewRenderable sideViewModel;
    private ViewRenderable NORMALEHERZFREQUENZ;
    private ViewRenderable MYOKARDINFARKT;
    private ViewRenderable ARTERIELLEHYPERTONIE;
    private ViewRenderable VORHOFFLIMMERN;
    private ViewRenderable ANSICHTEN;
    private ViewRenderable MANWALK;
    private ViewRenderable HEART;
    private ViewRenderable MENU;
    private ViewRenderable TON;
    private ViewRenderable GREENHEART;
    private ViewRenderable SIGNAL;
    private ViewRenderable HUMAN;
    private ViewRenderable MALEN;
    private ViewRenderable PEN;
    private ViewRenderable USB;
    private  ViewRenderable OUTLINEDHEART;
    private ViewRenderable EMPTY;
    private ViewRenderable ELIGHUSTON;
    private ViewRenderable HUMANGROUP;
    private ViewRenderable DOTCIRCLE;
    private  ViewRenderable FILLEDCIRCLE;
    private  ViewRenderable CIRCLE;
    private ViewRenderable BPCARD;
    private ViewRenderable DETAILCARD;
    private  ViewRenderable SIDE1;
    private  ViewRenderable SIDE2;
    private  ViewRenderable SIDE3;
    private  ViewRenderable SIDE4;
    private  ViewRenderable SIDE5;
    private  ViewRenderable SIDE6;
    private  ViewRenderable SIDE7;
    TextView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        loadModels();
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener(null);

        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);
    }

    public void loadModels() {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("models/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.model = model;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });


        ViewRenderable.builder()
                .setView(this, R.layout.view_model_title)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        TextView textView=(TextView) viewRenderable.getView().findViewById(R.id.textViewAnsichten);
                        CardView cardView=(CardView) viewRenderable.getView().findViewById(R.id.cardMWalk);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            int i=0;
                            int j=0;
                            static final long START_TIME_IN_MILLIS=600000;
                            long mTimeLeftInMillis=START_TIME_IN_MILLIS;
                            @Override
                            public void onClick(View view) {

                                new Handler().post(new Runnable() {
                                    public void run() {
                                        // count down timer start
                                        new CountDownTimer(1000000000, 1000) {
                                            public void onTick(long millisUntilFinished) {
                                                //mTimeLeftInMillis=millisUntilFinished;
                                                textView.setText(""+i);
                                            i++;
                                            }

                                            public void onFinish() {
                                                textView.setText("Time Up!");
                                            }
                                        }.start();
                                    }
                                });

//                                Toast.makeText(MainActivity.this, "card2 clicked", Toast.LENGTH_LONG).show();

                            }
                        });
                        activity.viewRenderable = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
//          addView(weakActivity,R.layout.normale_herzfrequenz,viewRenderable2);
//        addView(weakActivity,R.layout.myokardinfarkt,MYOKARDINFARKT);

        ViewRenderable.builder()
                .setView(this, R.layout.top_view_model)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.topViewModel = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
        ViewRenderable.builder()
                .setView(this, R.layout.right_view_model)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        TextView textView=(TextView) viewRenderable.getView().findViewById(R.id.textView);
                        TextView textView1=(TextView) viewRenderable.getView().findViewById(R.id.textView6);
                        CardView cardView=(CardView) viewRenderable.getView().findViewById(R.id.cardViewDetail);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            int i=0;
                            int j=0;
                            static final long START_TIME_IN_MILLIS=600000;
                            long mTimeLeftInMillis=START_TIME_IN_MILLIS;
                            @Override
                            public void onClick(View view) {

                                new Handler().post(new Runnable() {
                                    public void run() {
                                        // count down timer start
                                        new CountDownTimer(1000000000, 1000) {
                                            public void onTick(long millisUntilFinished) {
                                                //mTimeLeftInMillis=millisUntilFinished;
                                                textView1.setText(""+i);
                                                i++;
                                            }

                                            public void onFinish() {
                                                textView1.setText("Time Up!");
                                            }
                                        }.start();
                                    }
                                });

//                                Toast.makeText(MainActivity.this, "card2 clicked", Toast.LENGTH_LONG).show();

                            }
                        });

                        activity.rightViewModel = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
        ViewRenderable.builder()
                .setView(this, R.layout.side_view_model)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.sideViewModel = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });







    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (model == null || viewRenderable == null ) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable model and add it to the anchor.
        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());

        model.getScaleController().setMinScale(0.03f);
        model.getScaleController().setMaxScale(0.04f);
        model.setLocalScale(new Vector3(0.02f,0.07f,0.02f));
        model.setLocalPosition(new Vector3(0.02f, 0.89f, 0.02f));
        model.setParent(anchorNode);
        model.setRenderable(this.model).animate(true).start();
//        model.setRenderable(this.model2).animate(true).start();
        model.select();



      //  model.setLocalScale(new Vector3(0.0f, 1.0f, 0.0f));
        CardView cardHeart=viewRenderable.getView().findViewById(R.id.cardHeart);
        CardView menuCard=viewRenderable.getView().findViewById(R.id.cardMenu);
        menuCard.setOnClickListener(new View.OnClickListener() {
            float i=0;
            @Override
            public void onClick(View view) {
                model.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), i+10f));
                i=i+10;
            }
        });

        cardHeart.setOnClickListener(new View.OnClickListener() {
            float i=0;
            @Override
            public void onClick(View view) {
                model.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0), i+10f));
                i=i+10;
//                Toast.makeText(MainActivity.this, "Heart clicked",Toast.LENGTH_LONG).show();
            }
        });
        addNode(hitResult,model,viewRenderable,new Vector3(1.0f, 0.11f, 0.02f),new FixedHeightViewSizer(30),new FixedWidthViewSizer(25),"ViewRenderable 2");
        addNode(hitResult,model,topViewModel,new Vector3(0.015f, 1.56f, 0.02f),new FixedHeightViewSizer(25),new FixedWidthViewSizer(40),"ViewRenderable 2");
        addNode(hitResult,model,rightViewModel,new Vector3(-1.0f, 0.14f, 0.02f),new FixedHeightViewSizer(30),new FixedWidthViewSizer(25),"ViewRenderable 2");
        addNode(hitResult,model,sideViewModel,new Vector3(-0.32f, 0.37f, 0.02f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(20),"ViewRenderable 2");

//        addNode(model,NORMALEHERZFREQUENZ,new Vector3(-23.02f, 20.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ViewRenderable 2");
//        addNode(model,MYOKARDINFARKT,new Vector3(-8.02f, 20.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"MYOKARDINFARKT");
//        addNode(model,ARTERIELLEHYPERTONIE,new Vector3(7.02f, 20.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ARTERIELLEHYPERTONIE");
//        addNode(model,VORHOFFLIMMERN,new Vector3(22.02f, 20.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"VORHOFFLIMMERN");
//
//        addNode(model,ANSICHTEN,new Vector3(21.82f, 14.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ANSICHTEN");
//        addNode(model,MANWALK,new Vector3(16.62f, 9.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"MANWALK");
//        addNode(model,HEART,new Vector3(21.72f, 9.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"HEART");
//        addNode(model,MENU,new Vector3(26.92f, 9.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"MENU");
//        addNode(model,TON,new Vector3(21.82f, 6.21f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"TON");
//        addNode(model,GREENHEART,new Vector3(16.62f, 1.41f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"GREENHEART");
//        addNode(model,SIGNAL,new Vector3(21.72f, 1.41f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"SIGNAL");
//        addNode(model,HUMAN,new Vector3(26.92f, 1.41f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(5),"HUMAN");
//        addNode(model,MALEN,new Vector3(21.82f, -1.71f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"MALEN");
//
//        addNode(model,PEN,new Vector3(15.72f, -4.81f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"PEN");
//        addNode(model,USB,new Vector3(19.72f, -4.81f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"USB");
//        addNode(model,OUTLINEDHEART,new Vector3(23.72f, -4.81f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"OUTLINEDHEART");
//        addNode(model,EMPTY,new Vector3(27.72f, -4.81f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"EMPTY");
//        addNode(model,ELIGHUSTON,new Vector3(21.82f, -8.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ELIGHUSTON");
//
//        addNode(model,HUMANGROUP,new Vector3(15.72f, -11.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"HUMANGROUP");
//        addNode(model,DOTCIRCLE,new Vector3(19.72f, -11.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"DOTCIRCLE");
//        addNode(model,FILLEDCIRCLE,new Vector3(23.72f, -11.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"FILLEDCIRCLE");
//        addNode(model,CIRCLE,new Vector3(27.72f, -11.21f, 0.01f),new FixedHeightViewSizer(8),new FixedWidthViewSizer(3),"CIRCLE");
//
//        //Left View
//        addNode(model,BPCARD,new Vector3(-23.02f, 5.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ViewRenderable 2");
//        addNode(model,DETAILCARD,new Vector3(-23.02f, -10.01f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(15),"ViewRenderable 2");
//        addNode(model,SIDE1,new Vector3(-13.82f, 13.91f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE2,new Vector3(-13.82f, 10.91f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE3,new Vector3(-13.82f, 7.91f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE4,new Vector3(-13.82f, 4.91f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE5,new Vector3(-13.82f, 1.91f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE6,new Vector3(-13.82f, -1.11f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");
//        addNode(model,SIDE7,new Vector3(-13.82f, -4.11f, 0.01f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(3),"ViewRenderable 2");

    }
    public void addNode(HitResult hitResult,TransformableNode model1, ViewRenderable viewRenderable,Vector3 vector3, FixedHeightViewSizer fixedHeightViewSizer,
    FixedWidthViewSizer fixedWidthViewSizer,String nodeName){
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
        titleNode.setOnTouchListener(new Node.OnTouchListener() {
            @Override
            public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                String name=hitTestResult.getNode().getName();
                System.out.print("Node clicked "+name);
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        viewRenderable.setSizer(fixedHeightViewSizer);
     viewRenderable.setSizer(fixedWidthViewSizer);
     viewRenderable.setShadowCaster(false);
        titleNode.setEnabled(true);
    }
//    public void setClickListner(CardView cardView, Consumer consumer){
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                consumer.accept(1);
//            }
//        });
//    }
//
//    public void check(int i){
//        Toast.makeText(MainActivity.this, "check is working: "+i,Toast.LENGTH_LONG).show();
//    }

}
