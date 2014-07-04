package in.championswimmer.camerapreviewsample.app;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class MainActivity extends Activity implements SurfaceHolder.Callback {

    public SurfaceView sv;
    public SurfaceHolder sh;

    public Camera c;
    public Camera.Parameters p;

    public TextView tv1;

    public Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SurfaceView) findViewById(R.id.surfaceview1);
        tv1 = (TextView) findViewById(R.id.tv1);
        sh = sv.getHolder();
        sh.addCallback(this);

        b = (Button) findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Camera.open(1);
                p = c.getParameters();
                Log.d("Champ", p.getPreviewSize().toString());
                Log.d("Champ", p.toString());

                p.setPreviewSize(640,480);
                c.setDisplayOrientation(90);
                c.setParameters(p);

                try {
                    c.setPreviewDisplay(sh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Camera.Size> previewSizes = c.getParameters().getSupportedPreviewSizes();
                Camera.Size cs[] = new Camera.Size[20];
                previewSizes.toArray(cs);
                Log.d("Champ", " " + cs[0].height + cs[0].width);
                Log.d("Champ", " " + cs[1].height + cs[1].width);
                Log.d("Champ", " " + cs[2].height + cs[2].width);
                Log.d("Champ", " " + cs[3].height + cs[3].width);
                Log.d("Champ", " " + cs[4].height + cs[4].width);
                c.startPreview();
                c.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                    @Override
                    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                        Log.e("Champ", "we have " + faces.length + " faces");
                        tv1.setText("Faces = " + faces.length);
                    }
                });
                c.startFaceDetection();
            }
        });




    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Champ", "releasing camera");
        c.stopPreview();
        c.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
