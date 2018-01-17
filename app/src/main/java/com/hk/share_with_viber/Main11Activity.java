package com.hk.share_with_viber;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.hk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main11Activity extends AppCompatActivity {

    private ImageView ivTEst;
    private VideoView videoView;

    String SrcPath = "/storage/emulated/0/Download/test-1.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        ivTEst = (ImageView) findViewById(R.id.ivTEst);
        videoView = (VideoView) findViewById(R.id.myvideoview);
//        videoView.setVideoPath(SrcPath);
//        videoView.start();
    }


    public void shareViber(View view) {
//        shareVideoWithViber("Hello from tickle", SrcPath);
//        shareVideoWhatsApp("Hello from tickle", SrcPath);
//        shareVideoWithInstagram("Hello from tickle", SrcPath);
        getSharedIntent();


    }

    public void shareVideoWithViber(final String title, String path) {
        MediaScannerConnection.scanFile(this, new String[]{path},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent shareIntent = new Intent(
                                android.content.Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.setPackage("com.viber.voip");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(android.content.Intent.EXTRA_TITLE, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(shareIntent);
                    }
                });
    }

    public void shareVideoWhatsApp(final String title, String path) {
        MediaScannerConnection.scanFile(this, new String[]{path},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent shareIntent = new Intent(
                                android.content.Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.setPackage("com.whatsapp");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(android.content.Intent.EXTRA_TITLE, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(shareIntent);
                    }
                });
    }

    public void shareVideoWithInstagram(final String title, String path) {
        MediaScannerConnection.scanFile(this, new String[]{path},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Intent shareIntent = new Intent(
                                android.content.Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.setPackage("com.instagram.android");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                        shareIntent.putExtra(android.content.Intent.EXTRA_TITLE, title);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        startActivity(shareIntent);
                    }
                });
    }

    private void getSharedIntent() {
        // get available share intents
        final String packageToBeFiltered = "com.hk";
        List<Intent> targets = new ArrayList<Intent>();
        Intent template = new Intent(Intent.ACTION_SEND);
        template.setType("video/*");
        List<ResolveInfo> candidates = this.getPackageManager().queryIntentActivities(template, 0);

// filter package here
        for (ResolveInfo candidate : candidates) {
            String packageName = candidate.activityInfo.packageName;
            Log.d("package_name", packageName);
            if (!packageName.equals(packageToBeFiltered)) {
                Intent target = new Intent(android.content.Intent.ACTION_SEND);
                target.setType("video/*");
                target.putExtra(Intent.EXTRA_TEXT, "Text to share");
                target.setPackage(packageName);
                targets.add(target);
            }
        }
        if (!targets.isEmpty()) {
            Intent chooser = Intent.createChooser(targets.get(0), "Share dialog title goes here");
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[]{}));
            startActivity(chooser);
        }
    }
}
