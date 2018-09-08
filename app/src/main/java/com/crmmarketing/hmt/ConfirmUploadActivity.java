package com.crmmarketing.hmt;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.crmmarketing.hmt.fragment.UploadDocumentFragment;

public class ConfirmUploadActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            String inq_id = getIntent().getStringExtra("inq_id");
            String cu_id = getIntent().getStringExtra("cu_id");

            Bundle bundle = new Bundle();
            bundle.putString("inq_id", inq_id);
            bundle.putString("cu_id", cu_id);
            Fragment fragment = new UploadDocumentFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_home, fragment, fragment.getClass().getSimpleName())
                    .commit();


        }
    }


}
