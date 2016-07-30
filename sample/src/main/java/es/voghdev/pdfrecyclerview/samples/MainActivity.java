package es.voghdev.pdfrecyclerview.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import es.voghdev.pdfrecyclerview.library.adapter.PdfRecyclerViewAdapter;
import es.voghdev.pdfrecyclerview.library.view.PdfRecyclerView;

public class MainActivity extends AppCompatActivity {


    PdfRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PdfRecyclerView recyclerView = new PdfRecyclerView(this);
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
}
