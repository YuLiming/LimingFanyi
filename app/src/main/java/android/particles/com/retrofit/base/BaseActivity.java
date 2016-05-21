package android.particles.com.retrofit.base;

import android.os.Bundle;
import android.particles.com.retrofit.component.util.GetJson;
import android.particles.com.retrofit.component.util.MyDatabaseHelper;
import android.particles.com.retrofit.modules.main.adapter.HomeAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLM on 2016/5/1.
 */
public class BaseActivity extends AppCompatActivity
{
    protected List<String> data;
    protected List<String> datasrc;
    protected HomeAdapter madapter;
    protected GetJson getJson;
    protected MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


    }
}
