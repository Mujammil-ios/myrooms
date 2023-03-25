package com.mj.myrooms.utils;

import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class GlideUtils extends AppGlideModule {
    private static String TAG = "GlideUtils";

    @Override
    public void registerComponents(@NonNull Context mContext, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(mContext, glide, registry);
    }

    /**
     * disable manifest parsing to avoid adding similar modules twice.
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
