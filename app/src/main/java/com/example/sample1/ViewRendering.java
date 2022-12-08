//package com.example.sample1;
//
//import android.app.Activity;
//import android.view.View;
//import android.widget.Toast;
//
//import com.google.ar.sceneform.rendering.ViewRenderable;
//
//import java.lang.ref.WeakReference;
//import java.util.concurrent.atomic.AtomicReference;
//
//public class ViewRendering {
//    public void renderingView(Activity activity, View inflater, WeakReference<MainActivity> weakReference, ViewRenderable sideViewModel) {
//      AtomicReference<ViewRenderable> view= new AtomicReference<>(sideViewModel);
//       try{
//           ViewRenderable.builder()
//                   .setView(activity,inflater)
//                   .build()
//                   .thenAccept(viewRenderable -> {
//                       MainActivity mainActivity = weakReference.get();
////                       if (mainActivity != null) {
//                       view.set(viewRenderable);
////                       }
//                   })
//                   .exceptionally(throwable -> {
//                       Toast.makeText(activity, "Unable to load model", Toast.LENGTH_LONG).show();
//                       return null;
//                   });
//       }catch (Exception e){
//           e.printStackTrace();
//       }
//
//    }
//}
