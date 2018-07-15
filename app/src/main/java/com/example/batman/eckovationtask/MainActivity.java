package com.example.batman.eckovationtask;

import android.animation.Animator;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batman.eckovationtask.animation.CustomAnimation;
import com.example.batman.eckovationtask.entities.ImageData;
import com.example.batman.eckovationtask.entities.ImagesDataModel;
import com.example.batman.eckovationtask.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, ImageDownloader.ImageDataCall, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String baseUrl = "https://api.flickr.com/services/";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;
    @BindView(R.id.join_group_layout)
    LinearLayout joinGroupLayout;
    @BindView(R.id.main_screen)
    LinearLayout mainScreen;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.message)
    TextView message;

    @BindView(R.id.profile)
    ImageView profileImage;
    @BindView(R.id.designation)
    TextView designation;
    @BindView(R.id.display_name)
    TextView displayName;
    @BindView(R.id.toggler)
    ImageView toggler;

    @BindView(R.id.icon_arrow)
    ImageView showjoinAs;
    @BindView(R.id.dashboard)
    RelativeLayout dashboard;
    @BindView(R.id.create_group)
    RelativeLayout createGroup;
    @BindView(R.id.join_group)
    RelativeLayout joingroup;
    @BindView(R.id.add_profile)
    RelativeLayout addProfile;
    @BindView(R.id.join_as_teacher)
    RelativeLayout joinAsTeacher;
    @BindView(R.id.join_as_student)
    RelativeLayout joinAsStudent;
    @BindView(R.id.join_as_parent)
    RelativeLayout joinAsParent;
    @BindView(R.id.explore)
    RelativeLayout explore;
    @BindView(R.id.settings)
    RelativeLayout settings;
    @BindView(R.id.wallet)
    RelativeLayout wallet;
    @BindView(R.id.rate_us)
    RelativeLayout rateUs;
    @BindView(R.id.feedback)
    RelativeLayout sendfeedback;
    @BindView(R.id.help)
    RelativeLayout help;
    @BindView(R.id.terms_and_condition)
    RelativeLayout termsAndCondition;

    @BindView(R.id.my_profile)
    RelativeLayout myProfile;
    @BindView(R.id.manage_profile)
    RelativeLayout manageProfile;

    @BindView(R.id.add_profile_child)
    LinearLayout addProfileChild;
    @BindView(R.id.primary_menu)
    ScrollView primaryMenu;
    @BindView(R.id.secondary_menu)
    LinearLayout secondaryMenu;

    private Animator.AnimatorListener animListener;
    private boolean isExpanded;
    private ImagesAdapter mImagesAdapter;
    private boolean isProfileChildVisible, secondaryMenuOpen;
    private List<View> viewList;
    private Window activityWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
        activityWindow = getWindow();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mImagesAdapter = new ImagesAdapter(this, new ArrayList<ImageData>(), getLoaderManager());

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(mImagesAdapter);

        registerListener();
        registerAnimListener();
        registerDrawerListener();

        mainScreen = findViewById(R.id.main_screen);
        mainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "MainAcreen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ImageDownloader.fetchImageData(baseUrl, query, this);
        showProgressbar();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }


    @OnClick(R.id.join_group_layout)
    public void joinGroup() {
        Toast.makeText(this, "Join group", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_button)
    public void animate() {

        animateJoinGroup();

        if (!isExpanded) {
            showJoinGroupFab();
        } else {
            hideJoinGroupFab();
        }
    }


    public void hideJoinGroupFab() {
        CustomAnimation.getFabAnimator(fabButton, 0f, 400).start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainScreen.setForeground(null);
        }
        isExpanded = false;
    }


    public void showJoinGroupFab() {
        CustomAnimation.getFabAnimator(fabButton, 225f, 300).start();
        joinGroupLayout.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainScreen.setForeground(getResources().getDrawable(R.drawable.screen_film));
            mainScreen.requestFocus();
        }
        isExpanded = true;
    }


    private void animateJoinGroup() {
        if (!isExpanded) {
            CustomAnimation.getJoinGroupAnimator(joinGroupLayout, isExpanded, 1f, 400)
                    .setListener(null).start();
            CustomAnimation.springAnimateFab(joinGroupLayout);
        } else {
            CustomAnimation.getJoinGroupAnimator(joinGroupLayout, isExpanded, 0f, 300)
                    .setListener(animListener).start();
        }
    }

    private void registerAnimListener() {

        animListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                joinGroupLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        if (sm != null) {
            searchView.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        }
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.image_2:
                resetListLayout(2);
                break;
            case R.id.image_3:
                resetListLayout(3);
                break;
            case R.id.image_4:
                resetListLayout(4);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetListLayout(int count) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, count));
        mImagesAdapter.notifyDataSetChanged();
    }


    private void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onDataFetched(ImagesDataModel imagesData) {
        if (imagesData != null) message.setVisibility(View.GONE);
        mImagesAdapter.swapList(null);
        mImagesAdapter = new ImagesAdapter(this, imagesData.getPhotos().getPhoto(), getLoaderManager());
        recyclerView.setAdapter(mImagesAdapter);
        hideProgressbar();
    }

    @Override
    public void onError(String error) {
        hideProgressbar();
        if (error != null) {
            Snackbar.make(mainScreen, error, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void showPrimaryMenu() {
        primaryMenu.setVisibility(View.VISIBLE);
        secondaryMenu.setVisibility(View.GONE);
        toggler.setImageDrawable(getResources().getDrawable(R.drawable.ic_header_arrow_up));
    }

    private void showSecondaryMenu() {
        primaryMenu.setVisibility(View.GONE);
        secondaryMenu.setVisibility(View.VISIBLE);
        toggler.setImageDrawable(getResources().getDrawable(R.drawable.ic_header_arrow_down));
    }

    private void addProfileChild() {
        if (!isProfileChildVisible) {
            addProfileChild.setVisibility(View.VISIBLE);
            showjoinAs.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_arrow));
            isProfileChildVisible = true;
            CustomAnimation.getAddChilsAnimate(addProfileChild);
            return;
        }
        showjoinAs.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_arrow));
        addProfileChild.setVisibility(View.GONE);
        isProfileChildVisible = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {


        if (view.getId() != R.id.toggler) {
            view.setBackgroundColor(getResources().getColor(R.color.medium_grey));

            for (View viewItem : viewList) {
                if (viewItem.getId() != view.getId()) {
                    viewItem.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            }
        }

        if (view.getId() != R.id.add_profile && view.getId() != R.id.join_as_teacher &&
                view.getId() != R.id.join_as_student && view.getId() != R.id.join_as_parent) {
            isProfileChildVisible = true;
            addProfileChild();
        }

        CustomAnimation.circularRevealAnimate(view);

        switch (view.getId()) {
            case R.id.toggler:
                if (secondaryMenuOpen) {
                    showPrimaryMenu();
                    secondaryMenuOpen = false;
                } else {
                    showSecondaryMenu();
                    secondaryMenuOpen = true;
                }
                break;

            case R.id.dashboard:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                break;

            case R.id.add_profile:
                addProfileChild();
                break;

            case R.id.my_profile:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                break;
        }


        if (view.getId() != R.id.add_profile && view.getId() != R.id.toggler) {
            drawerLayout.closeDrawer(Gravity.START);
        }

    }

    private void registerListener() {
        for (View view : getDrawerViewList()) {
            view.setOnClickListener(this);
        }
    }

    private List<View> getDrawerViewList() {
        viewList = new ArrayList<>();
        viewList.add(toggler);
        viewList.add(dashboard);
        viewList.add(createGroup);
        viewList.add(joingroup);
        viewList.add(addProfile);
        viewList.add(joinAsTeacher);
        viewList.add(joinAsStudent);
        viewList.add(joinAsParent);
        viewList.add(explore);
        viewList.add(settings);
        viewList.add(wallet);
        viewList.add(rateUs);
        viewList.add(sendfeedback);
        viewList.add(help);
        viewList.add(termsAndCondition);
        viewList.add(myProfile);
        viewList.add(manageProfile);
        return viewList;
    }

    private void registerDrawerListener() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if (slideOffset > 0f) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activityWindow.setStatusBarColor(getResources().getColor(android.R.color.transparent));
                    }

                    if (isProfileChildVisible) {
                        addProfileChild.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }
}
