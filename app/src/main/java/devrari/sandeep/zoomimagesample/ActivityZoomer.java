package devrari.sandeep.zoomimagesample;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import javax.inject.Inject;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import devrari.sandeep.zoomimagesample.helper.sZoomer;

/**
 * Author: Sandeep Devrari**/

public class ActivityZoomer extends AppCompatActivity {

    @Inject
    SampleApp sampleApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((SampleApp)getApplication()).getComponentSample().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomer);
        setUpUI();
    }

    private void setUpUI() {
        setImage();
        findViewById(R.id.iv_cross_full_zoomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.iv_full_zoomer).setOnTouchListener(new sZoomer(this,((AppCompatImageView)findViewById(R.id.iv_cross_full_zoomer))));
    }

    private void setImage() {
        AppCompatImageView imageView=findViewById(R.id.iv_full_zoomer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setBackgroundDrawable(getDrawable(R.drawable.fire));
        }else{
            imageView.setBackgroundDrawable(sampleApp.getResources().getDrawable(R.drawable.fire));
        }
    }
}
