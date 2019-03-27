package com.example.module_employees_world.utils;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.graphics.Palette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class Rgba implements Parcelable {
    public int r;
    public int g;
    public int b;
    public int a;

    public Rgba(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Rgba(int color) {
        this(Color.red(color),Color.green(color),Color.blue(color),255);
    }

    public int toColor(){
        return Color.argb(a,r,g,b);
    }

    public Rgba addDiff(Rgba diff){
        this.r += diff.r;
        this.r = this.r < 0 ? 0 : this.r > 255 ? 255 : this.r;
        this.g += diff.g;
        this.g = this.g < 0 ? 0 : this.g > 255 ? 255 : this.g;
        this.b += diff.b;
        this.b = this.b < 0 ? 0 : this.b > 255 ? 255 : this.b;
        return this;
    }

    public static Rgba fromSwitch(Palette.Swatch swatch){
        return swatch == null ? null : new Rgba(Color.red(swatch.getRgb()),
                Color.green(swatch.getRgb()),
                Color.blue(swatch.getRgb()),
                255);
    }

    public static final List<Rgba> defaults = new ArrayList<>();

    static {
        defaults.add(new Rgba(Color.parseColor("#D1E5D5")));
        defaults.add(new Rgba(Color.parseColor("#F4EBE2")));
        defaults.add(new Rgba(Color.parseColor("#D1E4E9")));
        defaults.add(new Rgba(Color.parseColor("#E4DAB4")));
        defaults.add(new Rgba(Color.parseColor("#E3C2C2")));
        defaults.add(new Rgba(Color.parseColor("#E4E7E9")));
    }

    public static Rgba fromSwitch(Palette.Swatch swatch,Rgba diff){
        Rgba rgba = swatch == null ? null : new Rgba(Color.red(swatch.getRgb()),
                Color.green(swatch.getRgb()),
                Color.blue(swatch.getRgb()),
                255);
        return rgba == null ? defaults.get(new Random(System.nanoTime()).nextInt(defaults.size())) : rgba.addDiff(diff);
    }

    @Override
    public String toString() {
        return "Rgba{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.r);
        dest.writeInt(this.g);
        dest.writeInt(this.b);
        dest.writeInt(this.a);
    }

    protected Rgba(Parcel in) {
        this.r = in.readInt();
        this.g = in.readInt();
        this.b = in.readInt();
        this.a = in.readInt();
    }

    public static final Parcelable.Creator<Rgba> CREATOR = new Parcelable.Creator<Rgba>() {
        @Override
        public Rgba createFromParcel(Parcel source) {
            return new Rgba(source);
        }

        @Override
        public Rgba[] newArray(int size) {
            return new Rgba[size];
        }
    };
}
