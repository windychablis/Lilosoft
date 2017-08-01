package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.MapActivity;
import com.chablis.lilosoft.model.MapAddress;
import com.chablis.lilosoft.widget.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chablis on 2017/3/3.
 */

public class MapAddressAdapter extends BaseAdapter implements SectionIndexer,
        PinnedHeaderListView.PinnedHeaderAdapter, AbsListView.OnScrollListener {
    private Context mContext;
    private int mLocationPosition = -1;
    private ArrayList<MapAddress> mDatas;
    // 首字母集
    private List<String> mAddressSections;
    private List<Integer> mAddressPositions;
    private LayoutInflater inflater;

    public MapAddressAdapter(Context context, ArrayList<MapAddress> datas, List<String> addressSections,
                             List<Integer> addressPositions) {
        // TODO Auto-generated constructor stub
        this.mContext=context;
        inflater = LayoutInflater.from(context);
        mDatas = datas;
        mAddressSections = addressSections;
        mAddressPositions = addressPositions;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public MapAddress getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        int section = getSectionForPosition(position);
        final MapAddress mapAddress=getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);
        }
        LinearLayout mHeaderParent = (LinearLayout) convertView
                .findViewById(R.id.friends_item_header_parent);
        TextView mHeaderText = (TextView) convertView
                .findViewById(R.id.friends_item_header_text);
        if (getPositionForSection(section) == position) {
            mHeaderParent.setVisibility(View.VISIBLE);
            mHeaderText.setText(mAddressSections.get(section));
        } else {
            mHeaderParent.setVisibility(View.GONE);
        }
        TextView textView = (TextView) convertView
                .findViewById(R.id.friends_item);
        textView.setText(mapAddress.getArea_name());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=mapAddress.getPosition();
                if(str!=""||!str.equals("")) {
                    String[] p = mapAddress.getPosition().split(",");
                    double lat = Double.parseDouble(p[1]);
                    double lng = Double.parseDouble(p[0]);
                    ((MapActivity) mContext).serchMap(lat, lng);
                    ((MapActivity) mContext).mapToggle();
                }
            }
        });
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    @Override
    public int getPinnedHeaderState(int position) {
        int realPosition = position;
        if (realPosition < 0
                || (mLocationPosition != -1 && mLocationPosition == realPosition)) {
            return PINNED_HEADER_GONE;
        }
        mLocationPosition = -1;
        int section = getSectionForPosition(realPosition);
        int nextSectionPosition = getPositionForSection(section + 1);
        if (nextSectionPosition != -1
                && realPosition == nextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        // TODO Auto-generated method stub
        int realPosition = position;
        int section = getSectionForPosition(realPosition);
        String title = (String) getSections()[section];
        ((TextView) header.findViewById(R.id.friends_list_header_text))
                .setText(title);
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return mAddressSections.toArray();
    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0 || section >= mAddressSections.size()) {
            return -1;
        }
        return mAddressPositions.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
        if (position < 0 || position >= getCount()) {
            return -1;
        }
        int index = Arrays.binarySearch(mAddressPositions.toArray(), position);
        return index >= 0 ? index : -index - 2;
    }

}
