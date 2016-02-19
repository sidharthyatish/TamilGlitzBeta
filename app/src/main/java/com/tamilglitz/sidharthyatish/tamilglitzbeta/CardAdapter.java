package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Sidharth Yatish on 07-01-2016.
 */
public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<Article> articleList;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private Context context;
    private OnLoadMoreListener onLoadMoreListener;
    public String contentArticle;
    private int id;
    private ImageLoader imageLoader;
    public  String url;

    public CardAdapter(List<Article> articles,RecyclerView recyclerView,Context context){
        articleList=articles;
        this.context=context;

    }
    @Override
    public int getItemViewType(int position) {
        return articleList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view_item, parent, false);

            vh = new ArticleViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }
    public void setLoaded() {
        loading = false;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArticleViewHolder) {
            Article article = articleList.get(position);
            ArticleViewHolder aHolder= (ArticleViewHolder) holder;
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(article.getThumbUrl(), ImageLoader.getImageListener(aHolder.thumbView, R.drawable.tglogo_circle, android.R.drawable.ic_dialog_alert));

            aHolder.thumbView.setImageUrl(article.getThumbUrl(), imageLoader);
            aHolder.textViewTitle.setText(Html.fromHtml(article.getTitle()));

            aHolder.textViewDate.setText(article.getDate());
            aHolder.thumbView.setTag(aHolder);
            aHolder.currentArticle = article;
            aHolder.textAuthor.setText(article.getAuthor());
        }
        else if(holder instanceof ProgressViewHolder){
            ProgressViewHolder pHolder = (ProgressViewHolder) holder;
            pHolder.progressBar.setIndeterminate(true);

        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public NetworkImageView thumbView;
        public TextView textViewTitle;
        public TextView textViewDate;
        public CardView cardView;
        public Article currentArticle;
        public TextView textAuthor;
        public String bitmap;
        public String attachmentUrl;
        public ArticleViewHolder(View itemView) {
            super(itemView);
            thumbView = (NetworkImageView) itemView.findViewById(R.id.thumbNailView);
            textViewTitle = (TextView) itemView.findViewById(R.id.titleText);
            textViewDate = (TextView) itemView.findViewById(R.id.dateText);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            textAuthor= (TextView) itemView.findViewById(R.id.authorText);
            cardView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            final Bundle args = new Bundle();
            url=articleList.get(getAdapterPosition()).getUrl();
            contentArticle="Outer Error String";
            // getArticleContent(articleList.get(getAdapterPosition()).getId());
            id=articleList.get(getAdapterPosition()).getId();
            contentArticle=articleList.get(getAdapterPosition()).getContent();
             bitmap=articleList.get(getAdapterPosition()).getThumbUrl();
            attachmentUrl=articleList.get(getAdapterPosition()).getAttachmentUrl();
            args.putString("content", contentArticle);
            Intent intent = new Intent(context,ModifiedReader.class);
            intent.putExtra("content", contentArticle);
            intent.putExtra("images", bitmap);
            intent.putExtra("url",url);
            intent.putExtra("attachment",attachmentUrl);
            context.startActivity(intent);
        }


    }
    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public ProgressBar getProgressBar() {
            return progressBar;
        }
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
