package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by sagun on 8/10/2017.
 */

public interface DrawableObject {

    void initialize(Context context, Canvas canvas);

    void load(Canvas canvas, Context context);
}
