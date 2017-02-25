package com.example.administrator.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.askhealth.R;
import com.example.administrator.model.PostModel;
import com.example.administrator.views.TintableImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;



public class PostAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PostModel> postModels;
    private OnLikeClickListener likeClickListener;

    public interface OnLikeClickListener{
        void onLikeClick(int position, boolean isLiked);
    }

    public PostAdapter(Context context, ArrayList<PostModel> postModels) {
        this.context = context;
        this.postModels = postModels;
    }

    @Override
    public int getCount() {
        return postModels.size();
    }

    @Override
    public Object getItem(int position) {
        return postModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.post_item_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final PostModel model = postModels.get(position);

        //ถ้าอมีรูปมาจาก server ก็สามารถเอามาแสดงได้เลย
        if (!model.getImgPath().equals("")){
            Glide.with(context).load(model.getImgPath()).centerCrop().into(viewHolder.post_thumbnail);
        }else {
            Glide.with(context).load(R.drawable.picpro).centerCrop().into(viewHolder.post_thumbnail);
        }
        viewHolder.post_author.setText(model.getName());
        viewHolder.post_title.setText(model.getDescription());
        viewHolder.countlike.setText(model.getLikes());
        viewHolder.post_date.setText(model.getDate_time());
        setLiked(viewHolder, model.isLiked());
        if (likeClickListener != null){
            viewHolder.rlLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isLiked()){
                        postModels.get(position).setLiked(false);
                        likeClickListener.onLikeClick(position, false);
                        setLiked(viewHolder, false);
                        countLike(viewHolder, position, false);
                    }else {
                        postModels.get(position).setLiked(true);
                        likeClickListener.onLikeClick(position, true);
                        setLiked(viewHolder, true);
                        countLike(viewHolder, position, true);
                    }
                }
            });
        }

        return convertView;
    }

    private void setLiked(ViewHolder viewHolder, boolean isLiked){
        if (isLiked){
            viewHolder.imgLike.setColorFilter(ContextCompat.getColor(context, R.color.colorblue));
        }else {
            viewHolder.imgLike.setColorFilter(ContextCompat.getColor(context, R.color.grey_400));
        }
    }

    private void countLike(ViewHolder viewHolder, int position, boolean isLiked){
        int likes = Integer.parseInt(postModels.get(position).getLikes());
        if (isLiked){
            likes++;
            postModels.get(position).setLikes(String.valueOf(likes));
            viewHolder.countlike.setText(String.valueOf(likes));
        }else {
            likes--;
            postModels.get(position).setLikes(String.valueOf(likes));
            viewHolder.countlike.setText(String.valueOf(likes));
        }
    }

    public void setOnLikeClickListener(OnLikeClickListener listener){
        this.likeClickListener = listener;
    }

    public class ViewHolder{
        CircleImageView post_thumbnail;
        TextView post_author;
        TextView post_title;
        RelativeLayout rlLike;
        TintableImageView imgLike;
        TextView countlike;
        TextView post_date;

        public ViewHolder(View view) {
            post_thumbnail = (CircleImageView) view.findViewById(R.id.post_thumbnail);
            post_author = (TextView) view.findViewById(R.id.post_author);
            post_title = (TextView) view.findViewById(R.id.post_title);
            rlLike = (RelativeLayout) view.findViewById(R.id.rlLike);
            imgLike = (TintableImageView) view.findViewById(R.id.imgLike);
            countlike = (TextView) view.findViewById(R.id.countlike);
            post_date = (TextView) view.findViewById(R.id.post_date);
        }
    }
}
