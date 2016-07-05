package com.dclover.gpsutilities.khoihanh;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.util.UtilPreferences;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class CustomDialogManager {
    static String selectedLanguage;

    /* renamed from: com.virtualmaze.gpstriptracker.CustomDialogManager.1 */
    static class C05741 implements View.OnClickListener {
        final /* synthetic */ Dialog val$dialog_chooselanguage;

        C05741(Dialog dialog) {
            this.val$dialog_chooselanguage = dialog;
        }

        public void onClick(View arg0) {
            this.val$dialog_chooselanguage.dismiss();
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.CustomDialogManager.2 */
    static class C05752 implements RadioGroup.OnCheckedChangeListener {
        final /* synthetic */ ArrayList val$arraylist_LanguageCode;
        final /* synthetic */ RadioButton[] val$radiobutton_language;
        final /* synthetic */ RadioGroup val$radiogroup_chooselanguage;
        final /* synthetic */ ArrayList val$tLanguageList;

        C05752(RadioGroup radioGroup, ArrayList arrayList, RadioButton[] radioButtonArr, ArrayList arrayList2) {
            this.val$radiogroup_chooselanguage = radioGroup;
            this.val$tLanguageList = arrayList;
            this.val$radiobutton_language = radioButtonArr;
            this.val$arraylist_LanguageCode = arrayList2;
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int selectedId = this.val$radiogroup_chooselanguage.getCheckedRadioButtonId();
            for (int i = 0; i < this.val$tLanguageList.size(); i++) {
                if (this.val$radiobutton_language[i].getId() == selectedId) {
                    CustomDialogManager.selectedLanguage = (String) this.val$arraylist_LanguageCode.get(i);
                    return;
                }
            }
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.CustomDialogManager.3 */
    static class C05763 implements DialogInterface.OnDismissListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ boolean val$isFirstLaunch;

        C05763(Context context, boolean z) {
            this.val$context = context;
            this.val$isFirstLaunch = z;
        }

        public void onDismiss(DialogInterface dialog) {
            if (!CustomDialogManager.selectedLanguage.equals(UtilPreferences.getSelectedLanguage(this.val$context))) {
                UtilPreferences.saveSelectedLanguage(this.val$context, CustomDialogManager.selectedLanguage);
                ActivityKhoiHanh.getInstance().changeSelectedLanguage();
            } else if (this.val$isFirstLaunch) {
                ActivityKhoiHanh.mapObject.checkfirst();
            }
        }
    }

    public static void customdialogchooselanguage(Context context, boolean isFirstLaunch) {
        int i;
        ArrayList<String> tLanguageList1 = new ArrayList(Arrays.asList(context.getResources().getStringArray(R.array.Choose_LanguageList)));
        ArrayList<String> arraylist_LanguageCode1 = new ArrayList(Arrays.asList(context.getResources().getStringArray(R.array.Choose_LanguageCode)));
        ArrayList<String> arraylist_LanguageCode = new ArrayList();
        ArrayList<String> tLanguageList = new ArrayList();
        for (i = 0; i < tLanguageList1.size(); i++) {
            if (GetSuuporteLanguage.isSupported(context, (String) tLanguageList1.get(i))) {
                arraylist_LanguageCode.add(arraylist_LanguageCode1.get(i));
                tLanguageList.add(tLanguageList1.get(i));
            }
        }
        selectedLanguage = UtilPreferences.getSelectedLanguage(context);
        String deviceDefaultName = UtilPreferences.getDeviceLanguageName(context);
        String deviceDefaultCode = UtilPreferences.getDeviceLanguageCode(context);
        if (selectedLanguage == null) {
            selectedLanguage = deviceDefaultCode;
        }
        if (!arraylist_LanguageCode.contains(deviceDefaultCode)) {
            arraylist_LanguageCode.add(0, deviceDefaultCode);
            tLanguageList.add(0, deviceDefaultName);
        }
        Dialog dialog_chooselanguage = new Dialog(context, R.style.AppTheme);
        dialog_chooselanguage.requestWindowFeature(1);
        dialog_chooselanguage.setContentView(R.layout.layout_chooselanguage);
        dialog_chooselanguage.setCancelable(true);
        RadioButton[] radiobutton_language = new RadioButton[tLanguageList.size()];
        RadioGroup radiogroup_chooselanguage = (RadioGroup) dialog_chooselanguage.findViewById(R.id.radiogroup_chooselanguage);
        for (i = 0; i < tLanguageList.size(); i++) {
            radiobutton_language[i] = new RadioButton(context);
            radiobutton_language[i].setText((CharSequence) tLanguageList.get(i));
            radiobutton_language[i].setId(i);
            if (((String) arraylist_LanguageCode.get(i)).equals(selectedLanguage)) {
                radiobutton_language[i].setChecked(true);
            } else {
                radiobutton_language[i].setChecked(false);
            }
            radiogroup_chooselanguage.addView(radiobutton_language[i], new RadioGroup.LayoutParams(-2, -2));
        }
        ((ImageView) dialog_chooselanguage.findViewById(R.id.imageView_close_chooselanguage)).setOnClickListener(new C05741(dialog_chooselanguage));
        radiogroup_chooselanguage.setOnCheckedChangeListener(new C05752(radiogroup_chooselanguage, tLanguageList, radiobutton_language, arraylist_LanguageCode));
        dialog_chooselanguage.setOnDismissListener(new C05763(context, isFirstLaunch));
        dialog_chooselanguage.show();
    }
}
