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
package es.voghdev.pdfrecyclerview.library.viewholder;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class PdfRecyclerImageViewHolder extends RecyclerView.ViewHolder {
    final ImageView imageView;

    public PdfRecyclerImageViewHolder(ImageView itemView) {
        super(itemView);
        this.imageView = itemView;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
