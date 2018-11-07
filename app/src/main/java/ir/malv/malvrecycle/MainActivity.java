package ir.malv.malvrecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.malv.malvrecycler.Listo;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.list);
        List<String> list = new ArrayList<>();
        list.add("First item");
        list.add("Second item");
        list.add("Third item");
        list.add("Forth item");
        list.add("Fifth item");
        list.add("Sixth item");
        list.add("Seventh item");
        list.add("Eighth item");

        new Listo.Builder<String>()
                .load(recyclerView)
                .withListOf(list)
                .layout(android.R.layout.select_dialog_item)
                .setHasFixedSize(true)
                .layoutManager(new LinearLayoutManager(this))
                .withEveryItem(new Listo.WithEveryItemListener<String>() {
                    @Override
                    public void doOnCallback(String data, View itemView, int position) {
                        TextView t = itemView.findViewById(android.R.id.text1);
                        t.setText("Data: " + data + " Pos: " + position);
                    }
                })
                .onClick(new Listo.ListItemClick() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Hello " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
}
