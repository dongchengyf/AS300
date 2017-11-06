package com.zz.eshuo.as30;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.util.List;

/**
 * @author ADMIN
 */
public class TestActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Exit");
            setListFooter(button);
        }

    }


    @Override
    protected boolean isValidFragment(String fragmentName) {
        System.out.println(fragmentName);
        return true;
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_home, target);
    }


    public static class Prefs1Fragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_frag1);
        }
    }

    public static class Prefs2Fragment extends PreferenceFragment {

    }

}
