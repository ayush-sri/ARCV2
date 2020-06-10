package com.arc.arcv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.arc.arcv2.Utils.GraphicOverlay;
import com.arc.arcv2.Utils.RectOverlay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.util.List;

public class FaceDetection extends AppCompatActivity implements View.OnClickListener {
    private Button detect;
    private GraphicOverlay graphicOverlay;
    private CameraView cameraView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_face_det);
        initUi();
        detect.setOnClickListener(this);

       /* cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                progressDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();

                processFaceDetection(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });*/
    }

    private void processFaceDetection(Bitmap bitmap) {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions.Builder().build();
        FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance().getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                Toast.makeText(FaceDetection.this, "working", Toast.LENGTH_SHORT).show();
                getFaceResults(firebaseVisionFaces);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FaceDetection.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFaceResults(List<FirebaseVisionFace> firebaseVisionFaces) {
        int counter = 0;
        for(FirebaseVisionFace f:firebaseVisionFaces)
        {
            //Toast.makeText(this, String.valueOf(f.getTrackingId()), Toast.LENGTH_SHORT).show();
            Rect rect = f.getBoundingBox();
            RectOverlay rectOverlay = new RectOverlay(graphicOverlay,rect);
            graphicOverlay.add(rectOverlay);
            counter++;
        }
        progressDialog.dismiss();
    }

    private void initUi()
    {
        detect =findViewById(R.id.detect_btn);
        graphicOverlay = findViewById(R.id.graphic_overlay);
        cameraView = findViewById(R.id.camera_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("detecting faces please wait");
        progressDialog.setIndeterminate(true);
        cameraView.setLifecycleOwner(this);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);
                Toast.makeText(FaceDetection.this, "on pic taken", Toast.LENGTH_SHORT).show();
                byte[] data = result.getData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                ImageView i = findViewById(R.id.pic_);
                i.setImageBitmap(bitmap);
                //processFaceDetection(bitmap);
            }
        });
    }
   @Override
    public void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.open();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.detect_btn:
                cameraView.takePicture();
                break;
        }
    }
}
