package Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.utils.Group;
import com.example.utils.R;
import Activity.BuyTreatmentActivity;


public class PremiumMyAdapter extends BaseExpandableListAdapter {

    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;
    private ImageButton whatsapp;

    public PremiumMyAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        ImageButton btn = null;
        Button buyButton = null;

        //String nickname = groups.get(groupPosition).children.get(5);
        convertView = inflater.inflate(R.layout.listrow_details_premium, null);
        if (isLastChild)
            convertView = inflater.inflate(R.layout.whatsapp_button_premium, null);

        btn = (ImageButton) convertView.findViewById(R.id.whatsappButtonnn);
        buyButton = convertView.findViewById(R.id.buyButton);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( childPosition == 6) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://wa.me/972" + children));
                    activity.startActivity(intent);
                }
            }
        });

        buyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( childPosition == 6) {
                    activity.startActivity(new Intent(v.getContext(), BuyTreatmentActivity.class));
                }
            }
        });

        if(childPosition != 6) {
            text = (TextView) convertView.findViewById(R.id.textView1);
            text.setText(children);

        }
        System.out.println("PHONE: " + children);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}