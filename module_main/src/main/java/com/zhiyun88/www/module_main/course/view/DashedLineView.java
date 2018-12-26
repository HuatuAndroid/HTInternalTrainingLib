package com.zhiyun88.www.module_main.course.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/12/22.
 */
public class DashedLineView extends View {

 public DashedLineView(Context context, AttributeSet attrs) {
 super(context, attrs);

 }

 @Override
 protected void onDraw(Canvas canvas) {
 // TODO Auto-generated method stub
 super.onDraw(canvas);
 Paint paint = new Paint();
 paint.setStyle(Paint.Style.STROKE);
 paint.setColor(-10066329);//颜色可以自己设置
 Path path = new Path();
 path.moveTo(0, 0);//起始坐标
 path.lineTo(0,500);//终点坐标
 PathEffect effects = new DashPathEffect(new float[]{8,8,8,8},1);//设置虚线的间隔和点的长度
 paint.setPathEffect(effects);
 canvas.drawPath(path, paint);
 }
}