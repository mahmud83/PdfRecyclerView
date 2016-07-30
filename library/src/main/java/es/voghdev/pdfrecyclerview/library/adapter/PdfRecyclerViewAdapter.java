package es.voghdev.pdfrecyclerview.library.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import es.voghdev.pdfrecyclerview.library.viewholder.PdfRecyclerImageViewHolder;

public class PdfRecyclerViewAdapter extends RecyclerView.Adapter<PdfRecyclerImageViewHolder> {
    Context context;
    PdfRenderer renderer;
    String pdfPath;
    LayoutInflater inflater;

    public PdfRecyclerViewAdapter(Context context, String pdfPath) {
        this.context = context;
        this.pdfPath = pdfPath;
        init();
    }

    @SuppressWarnings("NewApi")
    private void init() {
        try {
            renderer = new PdfRenderer(getSeekableFileDescriptor(pdfPath));
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ParcelFileDescriptor getSeekableFileDescriptor(String path) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;

        File pdfCopy = new File(path);

        if (pdfCopy.exists()) {
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
            return parcelFileDescriptor;
        }

        if (isAnAsset(path)) {
            pdfCopy = new File(context.getCacheDir(), path);
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
        } else {
            URI uri = URI.create(String.format("file://%s", path));
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(Uri.parse(uri.toString()), "r");
        }

        return parcelFileDescriptor;
    }

    private boolean isAnAsset(String path) {
        return !path.startsWith("/");
    }

    @Override
    @SuppressWarnings("NewApi")
    public PdfRecyclerImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv = new ImageView(context);
        iv.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));

        if (renderer == null)
            return null;

        iv.setLayoutParams(createCenteredLayoutParams());

        return new PdfRecyclerImageViewHolder(iv);
    }

    private ViewGroup.LayoutParams createCenteredLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        return lp;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(PdfRecyclerImageViewHolder holder, int position) {
        PdfRenderer.Page page = getPDFPage(renderer, position);

        Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

        holder.setImageBitmap(bitmap);
    }

    @SuppressWarnings("NewApi")
    protected PdfRenderer.Page getPDFPage(PdfRenderer renderer, int position) {
        return renderer.openPage(position);
    }

    @SuppressWarnings("NewApi")
    public void close() {
        releaseAllBitmaps();
        if (renderer != null)
            renderer.close();
    }

    protected void releaseAllBitmaps() {

    }

    @SuppressWarnings("NewApi")
    @Override
    public int getItemCount() {
        return renderer != null ? renderer.getPageCount() : 0;
    }
}
