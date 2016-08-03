package es.voghdev.pdfrecyclerview.samples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.voghdev.pdfrecyclerview.library.adapter.PdfRecyclerViewAdapter;
import es.voghdev.pdfrecyclerview.library.view.PdfRecyclerView;

public class RecyclerOnXMLActivity extends AppCompatActivity {
    PdfRecyclerView pdfRecyclerView;
    PdfRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_xml);

        pdfRecyclerView = (PdfRecyclerView) findViewById(R.id.recyclerView);
        adapter = (PdfRecyclerViewAdapter) pdfRecyclerView.getAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null)
            adapter.close();
    }

    public static void open(Context context){
        Intent i = new Intent(context, RecyclerOnXMLActivity.class);
        context.startActivity(i);
    }
}
