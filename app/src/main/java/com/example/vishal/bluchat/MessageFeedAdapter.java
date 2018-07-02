package com.example.vishal.bluchat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageFeedAdapter extends ArrayAdapter<MessageBox> {

    Context mContext;
    ClipboardManager clipboard;

    public MessageFeedAdapter(Context context, ArrayList<MessageBox> messages) {
        super(context, R.layout.message_row_receive, messages);
        mContext = context;
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MessageBox message = getItem(position);
        if(convertView == null) {
            if(message.isSelf())
                convertView = LayoutInflater.from(mContext).inflate(R.layout.message_row_send, null);
            else convertView = LayoutInflater.from(mContext).inflate(R.layout.message_row_receive, null);
        }
        final TextView messageView = (TextView) convertView.findViewById(R.id.message);
        TextView timeView = (TextView) convertView.findViewById(R.id.time);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.body);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        if (!message.isImage()) {
            messageView.setText(message.getMessage());
            imageView.setImageDrawable(null);
            relativeLayout.setBackgroundResource(R.drawable.rounded_rectangle_orange);
        } else {
            messageView.setText("");
            imageView.setImageBitmap(message.getImage());
            relativeLayout.setBackground(null);
        }
        timeView.setText(message.getTime());

        if (!message.isImage() && message.getMessage().length() > 0) {
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipData clip = ClipData.newPlainText("message", message.getMessage());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, mContext.getString(R.string.message_copied_to_clipboard), Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
        } else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // do nothing.
                }
            });
        }
        return convertView;
    }
}