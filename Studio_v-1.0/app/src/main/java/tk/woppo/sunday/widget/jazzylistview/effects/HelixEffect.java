package tk.woppo.sunday.widget.jazzylistview.effects;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import tk.woppo.sunday.widget.jazzylistview.JazzyEffect;

public class HelixEffect implements JazzyEffect {

    private static final int INITIAL_ROTATION_ANGLE = 180;

    @Override
    public void initView(View item, int position, int scrollDirection) {
        ViewHelper.setRotationY(item, INITIAL_ROTATION_ANGLE);
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator.rotationYBy(INITIAL_ROTATION_ANGLE * scrollDirection);
    }

}
