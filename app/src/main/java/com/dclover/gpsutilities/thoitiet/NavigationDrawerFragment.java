package com.dclover.gpsutilities.thoitiet;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dclover.gpsutilities.BuildConfig;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.thoitiet.fragment.Fragment1;
import com.dclover.gpsutilities.thoitiet.fragment.Fragment2;
import com.dclover.gpsutilities.thoitiet.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class NavigationDrawerFragment extends Fragment {
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private View.OnClickListener clickListener;
    private View containerView;
    Context context;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    ArrayList<NavMenu> nav_menu_lst;
    RecyclerView recyclerView;
    TextView tv;

    /* renamed from: com.sundroid.myapplication.NavigationDrawerFragment.1 */
    class C02041 implements View.OnClickListener {
        C02041() {
        }

        public void onClick(View v) {
            Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + NavigationDrawerFragment.this.context.getPackageName()));
            goToMarket.addFlags(1208483840);
            try {
                NavigationDrawerFragment.this.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                NavigationDrawerFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + NavigationDrawerFragment.this.context.getPackageName())));
            }
        }
    }

    /* renamed from: com.sundroid.myapplication.NavigationDrawerFragment.3 */
    class C02053 implements Runnable {
        C02053() {
        }

        public void run() {
            NavigationDrawerFragment.this.mDrawerToggle.syncState();
        }
    }

    class NavMenu {
        int photoId;
        String title;

        NavMenu(String title, int photoId) {
            this.title = title;
            this.photoId = photoId;
        }
    }

    class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
        List<NavMenu> infolst;

        /* renamed from: com.sundroid.myapplication.NavigationDrawerFragment.RVAdapter.1 */
        class C02061 implements View.OnClickListener {
            C02061() {
            }

            public void onClick(View view) {
                int position = ((PersonViewHolder) view.getTag()).getPosition();
                NavMenu feedItem = (NavMenu) RVAdapter.this.infolst.get(position);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case ItemTouchHelper.ACTION_STATE_IDLE /*0*/:
                        fragmentTransaction.replace(R.id.containerView, new Fragment1());
                        fragmentTransaction.commit();
                        break;
                    case ItemTouchHelper.UP /*1*/:
                        fragmentTransaction.replace(R.id.containerView, new Fragment2());
                        fragmentTransaction.commit();
                        break;
                    case ItemTouchHelper.DOWN /*2*/:
                        fragmentTransaction.replace(R.id.containerView, new Fragment3());
                        fragmentTransaction.commit();
                        break;
                }
                NavigationDrawerFragment.this.mDrawerLayout.closeDrawers();
            }
        }

        public class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            ImageView personPhoto;
            TextView title;

            PersonViewHolder(View itemView) {
                super(itemView);
                this.cv = (CardView) itemView.findViewById(R.id.nav_menu_cv);
                this.title = (TextView) itemView.findViewById(R.id.nav_menu_cv_tv);
                this.personPhoto = (ImageView) itemView.findViewById(R.id.nav_menu_cv_iv);
            }
        }

        RVAdapter(List<NavMenu> infolst) {
            this.infolst = infolst;
        }

        public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_menu_rv_card_row, parent, false));
        }

        public void onBindViewHolder(PersonViewHolder holder, int i) {
            holder.title.setText(((NavMenu) this.infolst.get(i)).title);
            holder.personPhoto.setImageResource(((NavMenu) this.infolst.get(i)).photoId);
            View.OnClickListener clickListener = new C02061();
            holder.title.setOnClickListener(clickListener);
            holder.cv.setOnClickListener(clickListener);
            holder.title.setTag(holder);
            holder.cv.setTag(holder);
        }

        public int getItemCount() {
            return this.infolst.size();
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    /* renamed from: com.sundroid.myapplication.NavigationDrawerFragment.2 */
    class C03992 extends ActionBarDrawerToggle {
        C03992(Activity arg0, DrawerLayout arg1, Toolbar arg2, int arg3, int arg4) {
            super(arg0, arg1, arg2, arg3, arg4);
        }

        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            if (!NavigationDrawerFragment.this.mUserLearnedDrawer) {
                NavigationDrawerFragment.this.mUserLearnedDrawer = true;
                NavigationDrawerFragment.this.saveToPreference(NavigationDrawerFragment.this.getActivity(), NavigationDrawerFragment.KEY_USER_LEARNED_DRAWER, NavigationDrawerFragment.this.mUserLearnedDrawer + BuildConfig.FLAVOR);
            }
            NavigationDrawerFragment.this.getActivity().invalidateOptionsMenu();
        }

        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            NavigationDrawerFragment.this.getActivity().invalidateOptionsMenu();
        }
    }

    public NavigationDrawerFragment() {
        this.clickListener = new C02041();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUserLearnedDrawer = Boolean.valueOf(readToPreferece(getActivity(), KEY_USER_LEARNED_DRAWER, "false")).booleanValue();
        if (savedInstanceState != null) {
            this.mFromSavedInstanceState = true;
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.navigation_drawer, container, false);
        this.recyclerView = (RecyclerView) layout.findViewById(R.id.nav_menu_rv);
        ((ImageView) layout.findViewById(R.id.nav_rate_us)).setOnClickListener(this.clickListener);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        initializeData();
        this.recyclerView.setAdapter(new RVAdapter(this.nav_menu_lst));
        return layout;
    }

    private List<NavMenu> initializeData() {
        this.nav_menu_lst = new ArrayList();
        this.nav_menu_lst.add(new NavMenu("OverView", R.drawable.overview));
        this.nav_menu_lst.add(new NavMenu("Daily ForeCast", R.drawable.ic_calendar));
        this.nav_menu_lst.add(new NavMenu("About", R.drawable.ic_about));
        return this.nav_menu_lst;
    }

    public void setUp(int frag_nav, DrawerLayout drawerLayout, Toolbar toolbar) {
        this.containerView = getActivity().findViewById(frag_nav);
        this.mDrawerLayout = drawerLayout;
        this.mDrawerToggle = new C03992(getActivity(), drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        if (!(this.mUserLearnedDrawer || this.mFromSavedInstanceState)) {
            this.mDrawerLayout.openDrawer(this.containerView);
        }
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
        this.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        this.mDrawerLayout.post(new C02053());
    }

    public void saveToPreference(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences.Editor editor = context.getSharedPreferences("spfile", 0).edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public String readToPreferece(Context context, String preferenceName, String defaultValue) {
        return context.getSharedPreferences("spfile", 0).getString(preferenceName, defaultValue);
    }
}

