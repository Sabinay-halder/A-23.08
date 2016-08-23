package com.widevision.avartan.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by Mercury-five on 18/09/15.
 */
public class BackgroundBlurDialog extends BlurDialogFragment {
    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        bas_in = new FlipVerticalSwingEnter();
        bas_out = new FlipHorizontalExit();

        CustomListDialog mCustomDialog = new CustomListDialog(getActivity());

        mCustomDialog.showAnim(bas_in)
                .dismissAnim(bas_out)
                .show();
        return mCustomDialog;
    }
}
