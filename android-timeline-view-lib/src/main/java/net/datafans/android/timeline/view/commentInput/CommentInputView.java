package net.datafans.android.timeline.view.commentInput;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import net.datafans.android.timeline.R;

import java.util.Map;

/**
 * Created by zhonganyun on 15/10/12.
 */
public class CommentInputView extends FrameLayout {

    private Context context;

    private EditText editText;
    private Button sendButton;
    private View mask;

    private long commentId;

    private Delegate delegate;

    public CommentInputView(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        View inputView = LayoutInflater.from(context).inflate(R.layout.comment_input, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(inputView, params);

        mask = inputView.findViewById(R.id.mask);
        editText = (EditText) inputView.findViewById(R.id.input);
        sendButton = (Button) inputView.findViewById(R.id.sendButton);


        mask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                if (delegate == null) return;

                String text = editText.getText().toString();

                editText.setText("");

                if (text.equals("")) return;

                delegate.onCommentCreate(commentId, text);
            }
        });

    }

    public void show() {
        setVisibility(VISIBLE);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);

    }


    public void hide() {
        setVisibility(GONE);
        setPlaceHolder("");
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public void setPlaceHolder(String text){
        editText.setHint(text);
    }



    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }


    public static interface Delegate {

        void onCommentCreate(long commentId, String text);
    }


}
