package com.torpre.rentasillas.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.Toast;

import com.torpre.rentasillas.R;
import com.torpre.rentasillas.control.Control;
import com.torpre.rentasillas.model.Orders;

import java.util.Calendar;
import java.util.Date;

public class InsertUpdate extends AppCompatActivity {

    private NumberPicker numRectangularGames;
    private NumberPicker numRoundGames;
    private NumberPicker numPinkCoverCloths;
    private NumberPicker numBlueCoverCloths;
    private NumberPicker numOrangeCoverCloths;
    private NumberPicker numRectangularTables;
    private NumberPicker numRoundTables;
    private NumberPicker numRectangularCloths;
    private NumberPicker numRoundCloths;
    private NumberPicker numChairs;
    private EditText address;
    private EditText date;
    private EditText payment;
    private Control control;
    private TabHost tabGames;
    private Toolbar toolbar;

    public void init() {
        control = new Control(MainActivity.getInstance());
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabGames = (TabHost) findViewById(R.id.tabGames);
        numRectangularGames = (NumberPicker) findViewById(R.id.numRectangularGames);
        numRectangularGames.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numRoundGames = (NumberPicker) findViewById(R.id.numRoundGames);
        numRoundGames.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numPinkCoverCloths = (NumberPicker) findViewById(R.id.numPinkCoverCloths);
        numPinkCoverCloths.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numBlueCoverCloths = (NumberPicker) findViewById(R.id.numBlueCoverCloths);
        numBlueCoverCloths.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numOrangeCoverCloths = (NumberPicker) findViewById(R.id.numOrangeCoverCloths);
        numOrangeCoverCloths.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numRectangularTables = (NumberPicker) findViewById(R.id.numRectangularTables);
        numRectangularTables.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numRoundTables = (NumberPicker) findViewById(R.id.numRoundTables);
        numRoundTables.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numRectangularCloths = (NumberPicker) findViewById(R.id.numRectangularCloths);
        numRectangularCloths.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numRoundCloths = (NumberPicker) findViewById(R.id.numRoundCloths);
        numRoundCloths.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numChairs = (NumberPicker) findViewById(R.id.numChairs);
        numChairs.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        address = (EditText) findViewById(R.id.address);
        date = (EditText) findViewById(R.id.date);
        payment = (EditText) findViewById(R.id.payment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_update);

        init();
        prepareToolbar();
        prepareNumberPikers();
        prepareTabHost();
        prepareUpdateFields();

    }

    private void prepareNumberPikers() {
        prepareNumberPiker(numRectangularGames, 0, 100);
        prepareNumberPiker(numRoundGames, 0, 100);
        prepareNumberPiker(numPinkCoverCloths, 0, 100);
        prepareNumberPiker(numBlueCoverCloths, 0, 100);
        prepareNumberPiker(numOrangeCoverCloths, 0, 100);
        prepareNumberPiker(numRectangularTables, 0, 100);
        prepareNumberPiker(numRoundTables, 0, 100);
        prepareNumberPiker(numRectangularCloths, 0, 100);
        prepareNumberPiker(numRoundCloths, 0, 100);
        prepareNumberPiker(numChairs, 0, 100);
    }

    private void prepareToolbar() {
        setSupportActionBar(toolbar);
    }

    private void prepareNumberPiker(NumberPicker np, int min, int max) {
        np.setMaxValue(max);
        np.setMinValue(min);
    }

    private void prepareTabHost() {
        tabGames.setup();
        TabHost.TabSpec rectangularGames = tabGames.newTabSpec("rectangularGamesTag");
        TabHost.TabSpec roundGames = tabGames.newTabSpec("roundGamesTag");
        TabHost.TabSpec customGames = tabGames.newTabSpec("customGamesTag");
        rectangularGames.setIndicator("Juegos Rectangulares");
        roundGames.setIndicator("Juegos Redondos");
        customGames.setIndicator("Juegos Personalizados");
        rectangularGames.setContent(R.id.rectangularGames);
        roundGames.setContent(R.id.roundGames);
        customGames.setContent(R.id.customGames);
        tabGames.addTab(rectangularGames);
        tabGames.addTab(roundGames);
        tabGames.addTab(customGames);
    }

    private void prepareUpdateFields() {
        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            tabGames.setCurrentTab(2);
            toolbar.setTitle(bun.getCharSequence("title"));
            Orders order = (Orders) bun.get("order");
            numPinkCoverCloths.setValue(order.getPinkCoverCloths());
            numBlueCoverCloths.setValue(order.getBlueCoverCloths());
            numOrangeCoverCloths.setValue(order.getOrangeCoverCloths());
            numRectangularTables.setValue(order.getRectangularTables());
            numRoundTables.setValue(order.getRoundTables());
            numRectangularCloths.setValue(order.getRectangularCloths());
            numRoundCloths.setValue(order.getRoundCloths());
            numChairs.setValue(order.getChairs());
            address.setText(order.getAddress());
            date.setText(Control.convertTOString(order.getDate()));
            payment.setText(order.getPayment());
        }
    }

    public void showDateDialog(View view) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date dateOrder = Control.convertTODate(dayOfMonth + "/" + monthOfYear + "/" + year);
                System.out.println("inserta fecha del pedido -> " + dateOrder.getTime());
                date.setText(Control.convertTOString(dateOrder));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    public void okOrder(View view) {
        Bundle bun = getIntent().getExtras();
        Orders order = bun == null ? new Orders() : (Orders) bun.get("order");
        order.setChairs(10*numRectangularGames.getValue() + 10*numRoundGames.getValue() + numChairs.getValue());
        order.setRectangularCloths(numRectangularGames.getValue() + numRectangularCloths.getValue());
        order.setRectangularTables(numRectangularGames.getValue() + numRectangularTables.getValue());
        order.setRoundCloths(numRoundGames.getValue() + numRoundCloths.getValue());
        order.setRoundTables(numRoundGames.getValue() + numRoundTables.getValue());
        order.setPinkCoverCloths(numPinkCoverCloths.getValue());
        order.setBlueCoverCloths(numBlueCoverCloths.getValue());
        order.setOrangeCoverCloths(numOrangeCoverCloths.getValue());
        order.setAddress(address.getText().length() == 0 ? null : address.getText().toString());
        order.setDate(Control.convertTODate(date.getText().toString()));
        order.setPayment(payment.getText().length() == 0 ? null : payment.getText().toString());
        try {
            if (bun == null) {
                order.setStatus(Orders.STATUS_TO_BRING);
                control.insert(order);
            } else if (bun.getInt("requestCode") == MainActivity.FROM_TO_BRING) {
                order.setStatus(Orders.STATUS_TO_BRING);
                control.update(order);
            } else if (bun.getInt("requestCode") == MainActivity.FROM_TO_COLLEGE) {
                order.setStatus(Orders.STATUS_TO_COLLEGE);
                control.update(order);
            }
            setResult(RESULT_OK, new Intent());
            finish();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



}
