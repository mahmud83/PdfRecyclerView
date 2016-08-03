package es.voghdev.pdfrecyclerview.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import es.voghdev.pdfrecyclerview.library.adapter.PdfRecyclerViewAdapter;
import es.voghdev.pdfrecyclerview.library.view.PdfRecyclerView;

public class MainActivity extends AppCompatActivity {
    PdfRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PdfRecyclerView recyclerView = new PdfRecyclerView(this);
        recyclerView.setId(R.id.recyclerView);
        adapter = new PdfRecyclerViewAdapter(this, "adobe.pdf");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setContentView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null)
            adapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sample2) {
            RecyclerOnXMLActivity.open(this);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
