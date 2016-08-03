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
package es.voghdev.pdfrecyclerview.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import es.voghdev.pdfrecyclerview.library.R;
import es.voghdev.pdfrecyclerview.library.adapter.PdfRecyclerViewAdapter;
import es.voghdev.pdfrecyclerview.library.adapter.PdfScale;

public class PdfRecyclerView extends RecyclerView {
    protected static final float DEFAULT_SCALE = PdfScale.DEFAULT_SCALE;

    public PdfRecyclerView(Context context) {
        super(context);
    }

    public PdfRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public PdfRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a;

            a = getContext().obtainStyledAttributes(attrs, R.styleable.PDFRecyclerView);
            String assetName = a.getString(R.styleable.PDFRecyclerView_assetFileName);
            float scale = a.getFloat(R.styleable.PDFRecyclerView_scale, DEFAULT_SCALE);
            boolean pathIsvalid = assetName != null && !assetName.isEmpty();

            if (pathIsvalid || scale != DEFAULT_SCALE) {
                setAdapter(new PdfRecyclerViewAdapter.Builder(getContext())
                        .setScale(scale)
                        .setPdfPath(assetName)
                        .create());
                setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    }

    public static class Builder {
        String pdfPath = "";
        float scale = DEFAULT_SCALE;
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
