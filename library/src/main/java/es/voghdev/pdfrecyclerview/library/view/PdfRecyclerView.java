package es.voghdev.pdfrecyclerview.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import es.voghdev.pdfrecyclerview.library.adapter.PdfRecyclerViewAdapter;
import es.voghdev.pdfrecyclerview.library.adapter.PdfScale;

public class PdfRecyclerView extends RecyclerView {
    public PdfRecyclerView(Context context) {
        super(context);
    }

    public PdfRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PdfRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static class Builder {
        String pdfPath = "";
        float scale = PdfScale.DEFAULT_SCALE;
        Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public void setPdfPath(String pdfPath) {
            this.pdfPath = pdfPath;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        public PdfRecyclerView create() {
            PdfRecyclerView recyclerView = new PdfRecyclerView(context);
            PdfRecyclerViewAdapter adapter = new PdfRecyclerViewAdapter.Builder(context)
                    .setPdfPath(pdfPath)
                    .setScale(scale)
                    .create();

            recyclerView.setAdapter(adapter);
            return recyclerView;
        }
    }
}
