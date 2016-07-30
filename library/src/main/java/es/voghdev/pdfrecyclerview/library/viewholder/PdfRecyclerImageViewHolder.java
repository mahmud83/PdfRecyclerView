package es.voghdev.pdfrecyclerview.library.viewholder;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * Created by olmo on 27/07/16.
 */
public class PdfRecyclerImageViewHolder extends RecyclerView.ViewHolder {
    final ImageView imageView;

    public PdfRecyclerImageViewHolder(ImageView itemView) {
        super(itemView);
        this.imageView = itemView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }
}
