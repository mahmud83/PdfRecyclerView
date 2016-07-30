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
}
