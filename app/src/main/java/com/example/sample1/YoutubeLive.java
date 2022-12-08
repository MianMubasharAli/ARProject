package com.example.sample1;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

public class YoutubeLive {
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
            startMobileLive(context);
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
//        startActivity(mobileLiveIntent);
    }
}
