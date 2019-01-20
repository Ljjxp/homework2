package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>{

    private final ListItemClickListener mOnClickListener;

    private int mNumberItems;

    private List<Message> mData;

    public myAdapter(int numListItems, ListItemClickListener listener, List<Message> myData) {
        mOnClickListener = listener;
        mNumberItems = numListItems;
        mData = myData;
    }

    @NonNull
    @Override
    public myAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {
        Message message = mData.get(position);
        myViewHolder.updateUI(message);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mName;
        private final TextView mTalk;
        private final TextView mTime;
        private final CircleImageView mPicture;
        private final ImageView mKey;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_title);
            mTalk = itemView.findViewById(R.id.tv_description);
            mTime = itemView.findViewById(R.id.tv_time);
            mPicture = itemView.findViewById(R.id.iv_avatar);
            mKey = itemView.findViewById(R.id.robot_notice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }

        public void updateUI(Message message) {
            mName.setText(message.getTitle());
            mTalk.setText(message.getDescription());
            mTime.setText(message.getTime());
            if(message.getIcon().equals("TYPE_ROBOT")){
                mPicture.setImageResource(R.drawable.session_robot);
            }
            else if(message.getIcon().equals("TYPE_GAME")){
                mPicture.setImageResource(R.drawable.icon_micro_game_comment);
            }
            else if(message.getIcon().equals("TYPE_SYSTEM")){
                mPicture.setImageResource(R.drawable.session_system_notice);
            }
            else if(message.getIcon().equals("TYPE_STRANGER")){
                mPicture.setImageResource(R.drawable.session_stranger);
            }
            else if(message.getIcon().equals("TYPE_USER")){
                mPicture.setImageResource(R.drawable.icon_girl);
            }
            if(message.isOfficial()){
                mKey.setVisibility(View.VISIBLE);
            }
            else{
                mKey.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
