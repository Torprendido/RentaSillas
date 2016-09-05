package com.torpre.rentasillas.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.torpre.rentasillas.R;
import com.torpre.rentasillas.RentaService;
import com.torpre.rentasillas.control.Control;
import com.torpre.rentasillas.model.Historical;
import com.torpre.rentasillas.model.Orders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static MainActivity ma;
    private Control control;
    private ListView ordersListViewToBring;
    private ListView ordersListViewToCollege;
    private Orders order;
    public static final byte FROM_TO_BRING = 0;
    public static final byte FROM_TO_COLLEGE = 1;

    public MainActivity() {
        ma = this;
    }

    public static MainActivity getInstance() {
        return ma;
    }

    private void init() {
        startService(new Intent(this, RentaService.class));
        control = new Control(this);
        ordersListViewToCollege = (ListView) findViewById(R.id.ordersListViewToCollege);
        ordersListViewToBring = (ListView) findViewById(R.id.ordersListViewToBring);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        prepareNavigationView();
        prepareListViewToCollege();
        prepareListViewToBring();
        prepareTabHost();
    }

    private void prepareNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void prepareMenuToBring(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        forceShowIcon(popup);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_to_bring, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        try {
                            control.deleteOrder(order);
                            prepareListViewToBring();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.update_menu_bring:
                        openUdateActivity(order, FROM_TO_BRING);
                        return true;
                    case R.id.bring:
                        try {
                            control.changeOrderStatus(order, Orders.STATUS_TO_COLLEGE);
                            prepareListViewToBring();
                            prepareListViewToCollege();
                            Historical his = new Historical();
                            his.setId(order.getId());
                            his.setHistoricalTag(order.toString());
                            control.insert(his);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void prepareMenuToCollege(View view) {
        //TODO agregar notificaciones, agregar notificaciones de estado a las listas con iconos, ordeenar las fechas por hora
        PopupMenu popup = new PopupMenu(this, view);
        forceShowIcon(popup);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_to_college, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update_menu_college:
                        openUdateActivity(order, FROM_TO_COLLEGE);
                        return true;
                    case R.id.liberate:
                        try {
                            control.deleteOrder(order);
                            prepareListViewToCollege();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void forceShowIcon(PopupMenu popup) {
        try {
            Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
            mPopup.setForceShowIcon(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void prepareListViewToBring() {
        final List<Orders> orders = control.orders(Orders.STATUS_TO_BRING);
        String[] array = control.ordersArray(orders);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        ordersListViewToBring.setAdapter(adapter);
        ordersListViewToBring.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int adapterItemIndex, long id) {
                order = orders.get(adapterItemIndex);
                prepareMenuToBring(view);
            }
        });
    }

    private void prepareListViewToCollege() {
        final List<Orders> orders = control.orders(Orders.STATUS_TO_COLLEGE);
        String[] array = control.ordersArray(orders);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        ordersListViewToCollege.setAdapter(adapter);
        ordersListViewToCollege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int adapterItemIndex, long id) {
                order = orders.get(adapterItemIndex);
                prepareMenuToCollege(view);
            }
        });
    }

    public void openInsertActivity(View view) {
        Intent intent = new Intent(this, InsertUpdate.class);
        startActivityForResult(intent, FROM_TO_BRING);
    }

    public void openUdateActivity(Orders order, int requestCode) {
        Intent intent = new Intent(MainActivity.this, InsertUpdate.class);
        intent.putExtra("title", "Actualiza pedido");
        intent.putExtra("order", order);
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Sender) {
        super.onActivityResult(requestCode, resultCode, Sender);
        if (resultCode == RESULT_OK & requestCode == FROM_TO_BRING)
            prepareListViewToBring();
        if (resultCode == RESULT_OK & requestCode == FROM_TO_COLLEGE)
            prepareListViewToCollege();
    }

    private void prepareTabHost() {
        TabHost th = (TabHost) findViewById(R.id.tabHost);
        th.setup();
        final String toBringTag = "toBring";
        final String toCollegeTag = "toCollege";
        TabHost.TabSpec toBring = th.newTabSpec(toBringTag);
        TabHost.TabSpec toCollege = th.newTabSpec(toCollegeTag);
        toBring.setIndicator("Por Llevar");
        toCollege.setIndicator("Por Recoger");
        toBring.setContent(R.id.toBring);
        toCollege.setContent(R.id.toCollege);
        th.addTab(toBring);
        th.addTab(toCollege);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(toBringTag))
                    fab.setVisibility(FloatingActionButton.VISIBLE);
                else if (tabId.equals(toCollegeTag))
                    fab.setVisibility(FloatingActionButton.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            // Handle the camera action
        } else if (id == R.id.nav_inventario) {
            Intent intent = new Intent(this, Inventary.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
