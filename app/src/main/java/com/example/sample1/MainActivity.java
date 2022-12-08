package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

//package com.google.ar.sceneform.samples.gltf;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.List;
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

    private ImageButton heart;
    private ImageButton lungs;
    private ArFragment arFragment;
    private Renderable model;
    private Renderable model2;
    private ViewRenderable viewRenderable;
    private ViewRenderable topViewModel;
    private ViewRenderable rightViewModel;
    private  ViewRenderable sideViewModel;
    AnchorNode anchorNode;
    Anchor anchor;
    TransformableNode transformableNode;
//    private ViewRenderable NORMALEHERZFREQUENZ;
//    private ViewRenderable MYOKARDINFARKT;
//    private ViewRenderable ARTERIELLEHYPERTONIE;
//    private ViewRenderable VORHOFFLIMMERN;
//    private ViewRenderable ANSICHTEN;
//    private ViewRenderable MANWALK;
//    private ViewRenderable HEART;
//    private ViewRenderable MENU;
//    private ViewRenderable TON;
//    private ViewRenderable GREENHEART;
//    private ViewRenderable SIGNAL;
//    private ViewRenderable HUMAN;
//    private ViewRenderable MALEN;
//    private ViewRenderable PEN;
//    private ViewRenderable USB;
//    private  ViewRenderable OUTLINEDHEART;
//    private ViewRenderable EMPTY;
//    private ViewRenderable ELIGHUSTON;
//    private ViewRenderable HUMANGROUP;
//    private ViewRenderable DOTCIRCLE;
//    private  ViewRenderable FILLEDCIRCLE;
//    private  ViewRenderable CIRCLE;
//    private ViewRenderable BPCARD;
//    private ViewRenderable DETAILCARD;
//    private  ViewRenderable SIDE1;
//    private  ViewRenderable SIDE2;
//    private  ViewRenderable SIDE3;
//    private  ViewRenderable SIDE4;
//    private  ViewRenderable SIDE5;
//    private  ViewRenderable SIDE6;
//    private  ViewRenderable SIDE7;
//    TextView view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        heart=(ImageButton) findViewById(R.id.heartButton);
        lungs=(ImageButton) findViewById(R.id.lungsButton);

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
                .setSource(this, Uri.parse("models/Diaphragm.glb"))
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
        ModelRenderable.builder()
                .setSource(this, Uri.parse("models/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.model2 = model;
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
        if (model == null || viewRenderable == null || model2 == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Anchor.
        this.anchor = hitResult.createAnchor();
        this.anchorNode = new AnchorNode(this.anchor);
        this.anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the transformable model and add it to the anchor.
        this.transformableNode = new TransformableNode(arFragment.getTransformationSystem());

        this.transformableNode.getScaleController().setMinScale(0.03f);
        this.transformableNode.getScaleController().setMaxScale(0.04f);
        this.transformableNode.setLocalScale(new Vector3(0.02f,0.07f,0.02f));
        this.transformableNode.setLocalPosition(new Vector3(0.02f, 0.89f, 0.02f));
        this.transformableNode.setParent(this.anchorNode);
        this.transformableNode.setRenderable(this.model2).animate(true).start();
        this.transformableNode.select();

        CardView cardHeart=viewRenderable.getView().findViewById(R.id.cardHeart);
        CardView menuCard=viewRenderable.getView().findViewById(R.id.cardMenu);
        CardView cardSignal=viewRenderable.getView().findViewById(R.id.cardSignal);
        ar_functionality ar=new ar_functionality();
        ar.autoRotation(this.transformableNode,cardHeart,menuCard);
        lungs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAndAddModel(hitResult,model,new Vector3(0.02f, -0.65f, 0.02f));
                ar.autoRotation(transformableNode,cardHeart,menuCard);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAndAddModel(hitResult, model2, new Vector3(0.02f, 0.89f, 0.02f));
                ar.autoRotation(transformableNode,cardHeart,menuCard);
            }
        });
        cardSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoutubeLive youtubeLive= new YoutubeLive();
//                youtubeLive.validateMobileLiveIntent(MainActivity.this);
                validateMobileLiveIntent(MainActivity.this);
            }
        });

        ar.addNode(hitResult,this.transformableNode,viewRenderable,new Vector3(1.0f, 0.11f, 0.02f),new FixedHeightViewSizer(30),new FixedWidthViewSizer(25),"ViewRenderable 2",arFragment);
        ar.addNode(hitResult,this.transformableNode,topViewModel,new Vector3(0.015f, 1.56f, 0.02f),new FixedHeightViewSizer(25),new FixedWidthViewSizer(40),"ViewRenderable 2",arFragment);
        ar.addNode(hitResult,this.transformableNode,rightViewModel,new Vector3(-1.0f, 0.14f, 0.02f),new FixedHeightViewSizer(30),new FixedWidthViewSizer(25),"ViewRenderable 2",arFragment);
        ar.addNode(hitResult,this.transformableNode,sideViewModel,new Vector3(-0.32f, 0.37f, 0.02f),new FixedHeightViewSizer(20),new FixedWidthViewSizer(20),"ViewRenderable 2",arFragment);

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


public void removeAndAddModel(HitResult hitResult, Renderable renderable, Vector3 vector3){
        this.anchor.detach();
        this.anchor=hitResult.createAnchor();
    this.anchorNode = new AnchorNode(this.anchor);
    this.anchorNode.setParent(arFragment.getArSceneView().getScene());
    // Create the transformable model and add it to the anchor.
    this.transformableNode = new TransformableNode(arFragment.getTransformationSystem());

    this.transformableNode.getScaleController().setMinScale(0.03f);
    this.transformableNode.getScaleController().setMaxScale(0.04f);
    this.transformableNode.setLocalScale(new Vector3(0.02f,0.07f,0.02f));
    this.transformableNode.setLocalPosition(vector3);
    this.transformableNode.setParent(this.anchorNode);
    this.transformableNode.setRenderable(renderable).animate(true).start();
    this.transformableNode.select();
}

    private boolean canResolveMobileLiveIntent(Context context) {
        // in this method we are calling a youtube live  intent package name
        // and we are checking if youtube live intent is present or not.
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // returning the result after checking
        // the youtube live stream intent.
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    private void validateMobileLiveIntent(Context context) {
        if (canResolveMobileLiveIntent(context)) {
            // Launch the live stream Activity
            startMobileLive(MainActivity.this);
        } else {
            // on below line displaying a toast message if the intent is not present.
            Toast.makeText(context, "Please Update your Youtube app.", Toast.LENGTH_SHORT).show();
            // Prompt user to install or upgrade the YouTube app
        }
    }

    // method to create our intent for youtube live stream.
    private Intent createMobileLiveIntent(Context context, String description) {

        // on below line we are creating a new intent and we are setting package name to it.
        Intent intent = new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube");

        // on below line we are creating a new uri and setting
        // a scheme to it and appending our path with our package name.
        Uri referrer = new Uri.Builder()
                .scheme("android-app")
                .appendPath(context.getPackageName())
                .build();
        // on above line we are building our intent.
        // on below line we are adding our referer
        // and subject for our live video.
        intent.putExtra(Intent.EXTRA_REFERRER, referrer);
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, description);
        }
        // at last we are returning intent.
        return intent;
    }

    private void startMobileLive(Context context) {

        // calling a method to create an intent.
        Intent mobileLiveIntent = createMobileLiveIntent(context, "Streaming via ...");

        // on below line we are calling
        // our activity to start stream
        startActivity(mobileLiveIntent);
    }

//


}
