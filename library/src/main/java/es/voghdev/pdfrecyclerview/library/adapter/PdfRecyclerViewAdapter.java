/*
 * Copyright (C) 2016 Olmo Gallegos Hernandez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.pdfrecyclerview.library.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import es.voghdev.pdfrecyclerview.library.view.EmptyClickListener;
import es.voghdev.pdfrecyclerview.library.viewholder.PdfRecyclerImageViewHolder;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PdfRecyclerViewAdapter extends RecyclerView.Adapter<PdfRecyclerImageViewHolder>
        implements PhotoViewAttacher.OnMatrixChangedListener {
    protected static final int FIRST_PAGE = 0;
    protected static final float DEFAULT_QUALITY = 2f;

    Context context;
    PdfRenderer renderer;
    String pdfPath;
    LayoutInflater inflater;
    PdfRendererParams params;
    View.OnClickListener pageClickListener = new EmptyClickListener();
    PdfScale scale = new PdfScale();

    public PdfRecyclerViewAdapter(Context context, String pdfPath) {
        this.context = context;
        this.pdfPath = pdfPath;
        init();
    }

    @SuppressWarnings("NewApi")
    protected void init() {
        try {
            renderer = new PdfRenderer(getSeekableFileDescriptor(pdfPath));
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            params = extractPdfParamsFromFirstPage(renderer, DEFAULT_QUALITY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("NewApi")
    private PdfRendererParams extractPdfParamsFromFirstPage(PdfRenderer renderer, float renderQuality) {
        PdfRenderer.Page samplePage = getPDFPage(renderer, FIRST_PAGE);
        PdfRendererParams params = new PdfRendererParams();

        params.setRenderQuality(renderQuality);
        params.setWidth((int) (samplePage.getWidth() * renderQuality));
        params.setHeight((int) (samplePage.getHeight() * renderQuality));

        samplePage.close();

        return params;
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

        if (renderer == null) {
            return null;
        }

        iv.setLayoutParams(createCenteredLayoutParams());

        return new PdfRecyclerImageViewHolder(iv);
    }

    protected ViewGroup.LayoutParams createCenteredLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        return lp;
    }

    @SuppressWarnings("NewApi")
    @Override
    public void onBindViewHolder(PdfRecyclerImageViewHolder holder, int position) {
        PdfRenderer.Page page = getPDFPage(renderer, position);

        Bitmap bitmap = Bitmap.createBitmap(params.getWidth(), params.getHeight(), params.getConfig());
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

//        PhotoViewAttacher attacher = new PhotoViewAttacher(holder.getImageView());
//        attacher.setScale(scale.getScale(), scale.getCenterX(), scale.getCenterY(), true);
//        attacher.setOnMatrixChangeListener(this);
//        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(View view, float x, float y) {
//                pageClickListener.onClick(view);
//            }
//        });
//        attacher.update();

        holder.setImageBitmap(bitmap);
    }

    @SuppressWarnings("NewApi")
    protected PdfRenderer.Page getPDFPage(PdfRenderer renderer, int position) {
        return renderer.openPage(position);
    }

    @SuppressWarnings("NewApi")
    public void close() {
        releaseAllBitmaps();
        if (renderer != null) {
            renderer.close();
        }
    }

    protected void releaseAllBitmaps() {

    }

    @SuppressWarnings("NewApi")
    @Override
    public int getItemCount() {
        return renderer != null ? renderer.getPageCount() : 0;
    }

    @Override
    public void onMatrixChanged(RectF rect) {

    }

    public static class Builder {
        Context context;
        String pdfPath = "";
        float scale = PdfScale.DEFAULT_SCALE;
        float centerX = 0f, centerY = 0f;
        View.OnClickListener pageClickListener = new EmptyClickListener();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder setScale(PdfScale scale) {
            this.scale = scale.getScale();
            this.centerX = scale.getCenterX();
            this.centerY = scale.getCenterY();
            return this;
        }

        public Builder setCenterX(float centerX) {
            this.centerX = centerX;
            return this;
        }

        public Builder setCenterY(float centerY) {
            this.centerY = centerY;
            return this;
        }

        public Builder setPdfPath(String path) {
            this.pdfPath = path;
            return this;
        }

        public Builder setOnPageClickListener(View.OnClickListener listener) {
            if (listener != null) {
                pageClickListener = listener;
            }
            return this;
        }

        public PdfRecyclerViewAdapter create() {
            PdfRecyclerViewAdapter adapter = new PdfRecyclerViewAdapter(context, pdfPath);
            adapter.scale.setScale(scale);
            adapter.scale.setCenterX(centerX);
            adapter.scale.setCenterY(centerY);
            adapter.pageClickListener = pageClickListener;
            return adapter;
        }
    }
}
