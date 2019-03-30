package com.example.module_employees_world.utils;

import android.view.animation.Interpolator;

/**
 * author:LIENLIN
 * date:2019/3/30
 */
public class MyInterpolator implements Interpolator {

    private float factor;

    public MyInterpolator(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }

}
