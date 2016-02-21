package jp.roga7zl.canvaspractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WaveAnimationView logoView = (WaveAnimationView) findViewById(R.id.waveanimationview);

        Button mIsMovingToggleButton = (Button) findViewById(R.id.togglebutton_ismoving);
        mIsMovingToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoView.moveLogo();
            }
        });
    }
}
