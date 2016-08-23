package com.widevision.avartan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.widevision.avartan.R;
import com.widevision.avartan.model.CustomTextView;

/**
 * Created by Mercury-five on 18/09/15.
 */
public class CustomListDialog extends BaseDialog {
    private ListDialogAdapter adapter;
    private Context context;
    private LayoutAnimationController lac;
    private ListView listView;
    private TextView title;
    private ImageView closebtn;
    private View view;

    public CustomListDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public View onCreateView() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.dialog_view_local, null);
        listView = (ListView) view.findViewById(R.id.list);
        title=(TextView)view.findViewById(R.id.dialogtitleText);
        closebtn=(ImageView)view.findViewById(R.id.dialogclose);
        closebtn.setVisibility(View.VISIBLE);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        title.setText(EventAdapter.eventname);
        /** LayoutAnimation */
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(550);

        lac = new LayoutAnimationController(animation, 0.12f);
        lac.setInterpolator(new DecelerateInterpolator());

        return view;
    }


    @Override
    public boolean setUiBeforShow() {


        if (adapter == null) {
            adapter = new ListDialogAdapter();
        }

        listView.setAdapter(adapter);

        listView.setLayoutAnimation(lac);

        return false;
    }


    class ListDialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return EventAdapter.mSubEvent.size();
        }

        @Override
        public Object getItem(int position) {
            return EventAdapter.mSubEvent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LinearLayout ll_item = new LinearLayout(context);
            ll_item.setOrientation(LinearLayout.HORIZONTAL);
            ll_item.setGravity(Gravity.CENTER_VERTICAL);
            ll_item.setPadding(8,8,0,8);

            final CustomTextView tv_item = new CustomTextView(context);
            tv_item.setPadding(10, 0, 0, 0);
            tv_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tv_item.setSingleLine(true);
            tv_item.setTextColor(Color.BLACK);
            tv_item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            if (!EventAdapter.mSubEvent.get(position).subeventId.trim().equals("4")) {
                ll_item.addView(tv_item);
            }
            tv_item.setText(EventAdapter.mSubEvent.get(position).subeventName);

            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    tv_item.setBackgroundColor(Color.GRAY);
                    Intent intent = new Intent(context, SubEventFragmentActivity.class);
                    intent.putExtra("SubEventName", EventAdapter.mSubEvent.get(position).subeventName);
                    intent.putExtra("SubEventId", EventAdapter.mSubEvent.get(position).subeventId);
                    context.startActivity(intent);

                }
            });
            return ll_item;
        }
    }
}
