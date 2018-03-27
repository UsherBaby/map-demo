package com.nano.lottery.views;

import android.view.View;

public class CubeOutTransformer extends BaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? view.getWidth() : 0);
        view.setRotationY(20f * position);
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }
}
