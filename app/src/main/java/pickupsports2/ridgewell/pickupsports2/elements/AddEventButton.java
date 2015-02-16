package pickupsports2.ridgewell.pickupsports2.elements;

import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by cameronridgewell on 2/15/15.
 */
public class AddEventButton {
    FloatingActionButton create_event;

    private SpringConfig config = new SpringConfig(350, 8);
    private double minimum_spring_value = 0.8;

    private final BaseSpringSystem mSpringSystem = SpringSystem.create();
    private final AddSpringListener mSpringListener = new AddSpringListener();
    private Spring mScaleSpring;

    public AddEventButton(View v, final Runnable launcher) {
        create_event = (FloatingActionButton) v;

        create_event.setSize(FloatingActionButton.SIZE_NORMAL);
        create_event.setStrokeVisible(false);

        // Create the animation spring.
        mScaleSpring = mSpringSystem.createSpring();
        mScaleSpring.setSpringConfig(config);

        create_event.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent mevent) {
                switch (mevent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // When pressed start solving the spring to 1.
                        mScaleSpring.setEndValue(1);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // When released start solving the spring to 0.
                        mScaleSpring.setEndValue(0);
                        Thread t = new Thread(launcher);
                        t.start();
                        break;
                }
                return true;
            }
        });

        mScaleSpring.addListener(mSpringListener);
    }

    private class AddSpringListener extends SimpleSpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            // On each update of the spring value, we adjust the scale of the image view to match the
            // springs new value. We use the SpringUtil linear interpolation function mapValueFromRangeToRange
            // to translate the spring's 0 to 1 scale to a 100% to 50% scale range and apply that to the View
            // with setScaleX/Y. Note that rendering is an implementation detail of the application and not
            // Rebound itself. If you need Gingerbread compatibility consider using NineOldAndroids to update
            // your view properties in a backwards compatible manner.
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(),
                    0, 1, 1, minimum_spring_value);
            create_event.setScaleX(mappedValue);
            create_event.setScaleY(mappedValue);
        }
    }
}
