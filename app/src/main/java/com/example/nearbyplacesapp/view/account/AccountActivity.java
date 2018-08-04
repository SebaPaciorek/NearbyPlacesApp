package com.example.nearbyplacesapp.view.account;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.contract.account.AccountInterface;
import com.example.nearbyplacesapp.presenter.account.AccountPresenter;
import com.example.nearbyplacesapp.view.LoginActivity;
import com.example.nearbyplacesapp.view.MainActivity;

import io.realm.Realm;

public class AccountActivity extends AppCompatActivity implements AccountInterface.View, View.OnClickListener {

    private ListView listView;
    private String[] options = {"Mój profil", "Pokaż statystyki", "Wyloguj się"};
    private Integer[] imageID = {R.mipmap.ic_account_foreground, R.mipmap.ic_icon_statistics_foreground, R.mipmap.ic_icon_logout_foreground};

    private AccountInterface.Presenter accountPresenter;
    private String[] url = {"http://timwat.azurewebsites.net/Manage/Index", "http://timwat.azurewebsites.net/Statistics/Bar"};

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbar)));
        getSupportActionBar().setTitle("Konto");

        accountPresenter = new AccountPresenter();
        accountPresenter.getView(this);

        realm = Realm.getDefaultInstance();
        accountPresenter.getRealm(realm);
        Intent intent = getIntent();

        listView = findViewById(R.id.account_listView);
        onClickItemAccountListView();
        createAccountListView();

    }

    private void onClickItemAccountListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);

                switch (position) {
                    case 0:
                        browseWebsite(url[position]);
                        break;

                    case 1:
                        browseWebsite(url[position]);
                        break;

                    case 2:
                        logOut();
                        break;
                }
            }
        });
    }

    private void browseWebsite(String link){
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void createAccountListView() {
        listView.setAdapter(new AccountListViewAdapter(AccountActivity.this, options, imageID));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void logOut() {
        accountPresenter.clearCredentials();
    }

    @Override
    public void displayLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        MainActivity.getInstance().finish();
        finish();
    }

}
