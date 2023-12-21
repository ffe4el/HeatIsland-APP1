package com.example.projec7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private Context context;
    private DBHelper dbHelper;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;

    }
    public void setPosts(List<Post> postList) {
        this.postList = postList;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_item, parent, false);



        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.contentTextView.setText(post.getContent());
        holder.localTextView.setText(post.getLocalCategory());
        holder.themeTextView.setText(post.getThemeCategory());
        holder.CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailFragment commentFragment = new PostDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("post_id", post.getId());
                commentFragment.setArguments(bundle);

                // CommentFragment를 다이얼로그로 표시
                commentFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "comment_dialog");
            }
        });
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 데이터베이스에서 삭제
                dbHelper = new DBHelper(context);
                dbHelper.deletePost(post.getId());

                // 어댑터에서도 삭제
                int deletedPosition = postList.indexOf(post);
                postList.remove(deletedPosition);
                notifyItemRemoved(deletedPosition);
            }
        });
        // 이미지를 표시하는 부분
        String imageData = post.getImageData();
        if (imageData != null && !imageData.isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(convertBase64ToBitmap(imageData));
        } else {
            // 이미지가 없을 경우 기본 이미지 설정
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView localTextView;
        TextView themeTextView;
        ImageView imageView;
        ImageButton CommentButton;
        ImageButton DeleteButton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.postTitleTextView);
            contentTextView = itemView.findViewById(R.id.postContentTextView);
            localTextView=itemView.findViewById(R.id.postLocalCategoryTextView);
            themeTextView=itemView.findViewById(R.id.postThemeCategoryTextView);
            imageView = itemView.findViewById(R.id.postImageView);
            CommentButton= itemView.findViewById(R.id.CommentButton);
            DeleteButton=itemView.findViewById(R.id.deleteButton);

        }
    }

    private Bitmap convertBase64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}