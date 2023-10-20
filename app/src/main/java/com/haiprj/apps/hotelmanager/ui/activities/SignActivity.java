package com.haiprj.apps.hotelmanager.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.haiprj.apps.hotelmanager.App;
import com.haiprj.apps.hotelmanager.R;
import com.haiprj.apps.hotelmanager.databinding.ActivitySignBinding;
import com.haiprj.apps.hotelmanager.models.AccountManager;
import com.haiprj.apps.hotelmanager.models.Hotel;
import com.haiprj.apps.hotelmanager.ui.adapters.HotelSpinnerAdapter;
import com.haiprj.apps.hotelmanager.ui.dialogs.WarningDialog;
import com.haiprj.apps.hotelmanager.utils.SharePreferenceUtil;

public class SignActivity extends BaseActivity<ActivitySignBinding>{

    private State state = State.LOGIN;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initUI() {
        HotelSpinnerAdapter hotelSpinnerAdapter = new HotelSpinnerAdapter(this, R.layout.item_hotel_picker, App.database.getDataHotel().getAll());
        this.binding.storeCode.setAdapter(hotelSpinnerAdapter);
        Log.d("TAG_Sign", "initUI: " + App.database.getDataHotel().getAll().size());
        hotelSpinnerAdapter.clear();
        hotelSpinnerAdapter.addAll(App.database.getDataHotel().getAll());
        this.binding.storeCode.setSelection(0);
        if (state == State.LOGIN) {
            String username = SharePreferenceUtil.getInstance().getSharedPreferences().getString(getString(R.string.username), "");
            if (!username.isEmpty()) {
                this.binding.username.setText(username);
                String pass = SharePreferenceUtil.getInstance().getSharedPreferences().getString(getString(R.string.pass), "");
                if (!pass.isEmpty()) {
                    this.binding.pass.setText(pass);
                    int code = SharePreferenceUtil.getInstance().getSharedPreferences().getInt(getString(R.string.store_code), -1);
                    if (code != -1) {
                        Hotel hotel = null;
                        for (Hotel hotel1 : App.database.getDataHotel().getAll()) {
                            if (hotel1.id == code) {
                                hotel = hotel1;
                                break;
                            }
                        }
                        if (hotel != null) {
                            this.binding.storeCode.setSelection(hotelSpinnerAdapter.getPosition(hotel));
                        }

                    }
                }
            }
        }
    }

    @Override
    protected void addEvents() {
        binding.btnLogin.setOnClickListener(v -> {
            this.scaleAnimation(v, 0.9f);
            AccountManager acc = new AccountManager();
            acc.setUsername(binding.username.getText().toString().trim());
            acc.setPassword(binding.pass.getText().toString().trim());
            acc.setStoreCode(((Hotel)binding.storeCode.getSelectedItem()).id);
            if (this.binding.username.getText().toString().trim().isEmpty()
                    || binding.pass.getText().toString().trim().isEmpty()
                    || binding.storeCode.getSelectedItem() == null) {
                showMessage(getString(R.string.these_field_not_empty));
//                Toast.makeText(this, R.string.these_field_not_empty, Toast.LENGTH_LONG).show();
                return;
            }
            if (this.state == State.LOGIN) {
                AccountManager accountManager = App.database.getDataAccount().getAccount(
                        acc.getUsername(),
                        acc.getPassword(),
                        acc.getStoreCode()
                );
                if (accountManager == null) {
                    showMessage(getString(R.string.info_is_fail));
//                    Toast.makeText(this, R.string.info_is_fail, Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else if (this.state == State.REGISTER) {
                AccountManager accountManager = App.database.getDataAccount().getAccountFromUsername(
                        acc.getUsername()
                );
                if (accountManager != null) {
                    showMessage(getString(R.string.account_is_exist));
//                    Toast.makeText(this, R.string.account_is_exist, Toast.LENGTH_LONG).show();
                    this.binding.username.setText("");
                }
                else {
                    if (acc.getUsername().contains(" ") || acc.getPassword().contains(" ")){
                        showMessage(getString(R.string.username_and_pass_validate_fail));
//                        Toast.makeText(this, R.string.username_and_pass_validate_fail, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (acc.getPassword().length() > 15 && acc.getPassword().length() < 6) {
                        showMessage(getString(R.string.pass_must_good_len));
//                        Toast.makeText(this, R.string.pass_must_good_len, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (App.database.getDataHotel().loadAllByIds(acc.getStoreCode()).isEmpty()) {
                        showMessage(getString(R.string.hotel_not_exist));
//                        Toast.makeText(this, R.string.hotel_not_exist, Toast.LENGTH_LONG).show();
                        return;
                    }


                    App.database.getDataAccount().insert(acc);
                }
            }
            this.showLoading();
            new Handler().postDelayed(() -> {
                if (state == State.LOGIN) {
                    startActivity(new Intent(SignActivity.this, MainActivity.class));
                    App.account = acc;
                    SharePreferenceUtil.getInstance().getEditor().putString(getString(R.string.username), acc.getUsername());
                    SharePreferenceUtil.getInstance().getEditor().putString(getString(R.string.pass), acc.getPassword());
                    SharePreferenceUtil.getInstance().getEditor().putInt(getString(R.string.store_code), acc.getStoreCode());
                    SharePreferenceUtil.getInstance().getEditor().apply();
                    this.hideLoading();
                    this.finish();
                }
                else if (state == State.REGISTER) {
                    this.hideLoading();
                    this.changeViewLogin();
                }

            }, 2000);
        });
        binding.register.setOnClickListener(v -> {
            this.scaleAnimation(v, 0.9f);
            this.showLoading();
            new Handler().postDelayed(()-> {
                if (this.state == State.LOGIN) {
                    this.hideLoading();
                    this.changeViewRegister();
                }
                else if (this.state == State.REGISTER) {
                    this.hideLoading();
                    this.changeViewLogin();
                }
            }, 500);
        });
    }

    public void showMessage(String message) {
        WarningDialog.getInstance(this).showMessage(message);
    }
    private void changeViewRegister() {
        this.state = State.REGISTER;
        this.binding.title.setText(this.getString(R.string.register));
        this.binding.btnLogin.setText(this.getString(R.string.register));
        this.binding.register.setText(this.getString(R.string.login));
    }

    private void changeViewLogin() {
        this.state = State.LOGIN;
        this.binding.title.setText(this.getString(R.string.login));
        this.binding.btnLogin.setText(this.getString(R.string.login));
        this.binding.register.setText(this.getString(R.string.register));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_sign;
    }

    public enum State {
        LOGIN,
        REGISTER
    }
}
