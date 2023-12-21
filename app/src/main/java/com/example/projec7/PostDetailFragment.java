package com.example.projec7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class PostDetailFragment extends BottomSheetDialogFragment {

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private EditText commentEditText;
    private Button postCommentButton;

    private List<Comment> commentList;
    private int currentPostId = -1;
    private DBHelper dbHelper;


    public PostDetailFragment() {
        // Required empty public constructor
    }

    public static PostDetailFragment newInstance(int postId) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putInt("post_id", postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPostId = getArguments().getInt("post_id", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        commentRecyclerView = view.findViewById(R.id.commentRecyclerView);
        commentEditText = view.findViewById(R.id.CommentText);
        postCommentButton = view.findViewById(R.id.submitButton);
        currentPostId = getCurrentPostId();
        commentList = getCommentsForPost(currentPostId);
        dbHelper = new DBHelper(getContext());


        commentList = dbHelper.getCommentsForPost(currentPostId);

        commentAdapter = new CommentAdapter(commentList,getActivity());
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentRecyclerView.setAdapter(commentAdapter);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCommentText = commentEditText.getText().toString();

                dbHelper.insertComment(currentPostId, newCommentText);

                commentList.clear();
                commentList.addAll(dbHelper.getCommentsForPost(currentPostId));

                commentAdapter.notifyDataSetChanged();

                commentEditText.setText("");
            }
        });


        return view;
    }

    private List<Comment> getCommentsForPost(int postId) {

        //현재 post_id에 해당하는 댓글 데이터 가져오기
        List<Comment> comments = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Comment comment = new Comment(postId, "Comment " + i);
            comments.add(comment);
        }

        return comments;
    }
    private int getCurrentPostId() {
        //현재 post에 해당하는 id를 가져오는 메소드
        if (getArguments() != null) {
            return getArguments().getInt("post_id", -1);
        } else {
            return -1; // 기본값
        }
    }
}