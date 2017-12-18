package octapult.com.sazmaan.Activities;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import octapult.com.sazmaan.Fragments.InstoreFragment;
import octapult.com.sazmaan.Fragments.ProductionFragment;
import octapult.com.sazmaan.Fragments.SalesFragment;
import octapult.com.sazmaan.R;

public class Dashboard extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;



    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
       // drawerLayout.closeDrawer(Gravity.RIGHT);



        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setTitle("Dashboard");
        nav_view = (NavigationView) findViewById(R.id.nav_view);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

              switch (item.getItemId()){

                  case (R.id.openMenu):
                      drawerLayout.openDrawer(Gravity.END);
                      break;


              }

                return true;
            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {

                case R.id.dashboard:
                    drawerLayout.closeDrawer(Gravity.END);


                    break;
                case R.id.addSale:
                    drawerLayout.closeDrawer(Gravity.END);
                   // Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AddSaleActivity.class);
                    startActivity(intent);
                    break;

                case R.id.graph:
                    drawerLayout.closeDrawer(Gravity.END);
                   // Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                    Intent graphIntent = new Intent(getApplicationContext(), GraphActivity.class);
                    startActivity(graphIntent);

                    break;
                case R.id.salesReport:
                //    drawerLayout.closeDrawer(Gravity.END);
                 //   Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                    Intent salesReportIntent = new Intent(getApplicationContext(), SalesReportActivity.class);
                    startActivity(salesReportIntent);

                    break;




            }
                return true;
            }
        });

        ////////////////////////////////////////////private Toolbar toolbar;



            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new SalesFragment(), "Sales");
            adapter.addFragment(new InstoreFragment(), "Instore SKU");
            adapter.addFragment(new ProductionFragment(), "Production");
            viewPager.setAdapter(adapter);
        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }








    }

