package gallery.dogs.doggallery.presenter.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import gallery.dogs.doggallery.R;
import gallery.dogs.doggallery.contract.MainContract;
import gallery.dogs.doggallery.view.MainView;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_PROGRESSBAR = 0;
    private final int VIEW_TYPE_PROGRESSBAR_BOTTOM = 2;

    MainContract.OnBottomReachedListener onBottomReachedListener;
    String imageURL;
    private List<String> imageUrlList;
    private Context context;
    private boolean isFooterEnabled = true;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public HomeAdapter(List<String> imageUrlList, RecyclerView rv, Context context) {
        this.imageUrlList = imageUrlList;
        this.staggeredGridLayoutManager = (StaggeredGridLayoutManager) rv.getLayoutManager();
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        context = parent.getContext();

        if (viewType == VIEW_TYPE_ITEM) {

            View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_card_item, parent, false);
            vh = new ImageViewHolder(itemView);
        } else {

            View loadingView;
            loadingView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false);
            vh = new ProgressViewHolder(loadingView);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProgressViewHolder) {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

        } else if (imageUrlList.size() > 0 && position < imageUrlList.size()) {

            if (position == imageUrlList.size() - 1) {
                onBottomReachedListener.onBottomReached(position);
            } else {

                imageURL = MainView.imagesList.get(position);
                final RequestOptions requestOptions = new RequestOptions();

                Glide.with(context)
                        .load(imageURL)
                        .apply(requestOptions.placeholder(R.drawable.placeholder_white))
//                        .apply(requestOptions.error(R.drawable.placeholder_unavailable_white))
                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL))
                        .apply(requestOptions.centerCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.d("FAILED", e.getMessage());

                                //Remove image if it fails to load
                                removeAt(model);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("GLIDE", "READY");
                                return false;
                            }
                        })
                        .into(((ImageViewHolder) holder).imageView);
            }

        }
    }


    public void removeAt(Object failed) {
        imageUrlList.remove(failed);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (isFooterEnabled && position >= imageUrlList.size()) ? VIEW_TYPE_PROGRESSBAR : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (isFooterEnabled) ? imageUrlList.size() + 1 : imageUrlList.size();
    }

    public void enableFooter(boolean isEnabled) {
        this.isFooterEnabled = isEnabled;
    }

    public void setOnBottomReachedListener(MainContract.OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View v) {

            super(v);
            imageView = v.findViewById(R.id.cardImage);
        }
    }


}
