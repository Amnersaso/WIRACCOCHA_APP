package w.kipu.kipu.login.Produccion.ui.mainProduccion;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.ui.mainProduccion.FragmentListFormato.FragmentPendientes;
import w.kipu.kipu.login.Produccion.ui.mainProduccion.FragmentListFormato.FragmentListaOfisis;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TitleAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_p1, R.string.tab_text_p2};
    private final Context mContext;

    public TitleAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0){
            fragment = new FragmentListaOfisis();
        }else if (position == 1){
            fragment = new FragmentPendientes();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}