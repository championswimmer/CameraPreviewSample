package in.championswimmer.camerapreviewsample.app;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;

import java.io.IOException;


public class MainActivity extends Activity implements SurfaceHolder.Callback {

    public SurfaceView sv;
    public SurfaceHolder sh;

    public Camera c;
    public Camera.Parameters p;

    public Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sv = (SurfaceView) findViewById(R.id.surfaceview1);
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

                p.setPreviewSize(176,144);
                p.setRotation(180);
                c.setParameters(p);

                try {
                    c.setPreviewDisplay(sh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                c.startPreview();
                c.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                    @Override
                    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                        Log.e("Champ", "we have " + faces.length + " faces");
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
