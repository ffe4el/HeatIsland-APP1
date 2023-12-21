package com.example.projec7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;
    private OnDeleteClickListener onDeleteClickListener;
    private DBHelper dbHelper;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentText.setText(comment.getCommentText());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 데이터베이스에서 삭제
                dbHelper = new DBHelper(context);
                dbHelper.deleteComment(comment.getId());

                // 어댑터에서도 삭제
                int deletedPosition = commentList.indexOf(comment);
                commentList.remove(deletedPosition);
                notifyItemRemoved(deletedPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentText;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            commentText = itemView.findViewById(R.id.commentText);
            deleteButton = itemView.findViewById(R.id.deleteButton);


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                        onDeleteClickListener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}