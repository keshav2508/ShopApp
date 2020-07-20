package com.example.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

public class CustomAdaptor  extends ArrayAdapter<Shops> implements Filterable {


    private ArrayList<Shops> itemsModelsl;
    private ArrayList<Shops> itemsModelListFiltered;

    public CustomAdaptor(Activity context, ArrayList<Shops> android) {
        super(context, 0, android);

        this.itemsModelsl = android;
        this.itemsModelListFiltered = itemsModelsl;

    }


    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public Shops getItem(int position) {
        return itemsModelListFiltered.get(position);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_view, parent, false);
        }
        Shops currentShop = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.shop_name);
        assert currentShop != null;
        nameTextView.setText(currentShop.getMshopname());

        TextView statusTextView = listItemView.findViewById(R.id.shop_status);
        statusTextView.setText(currentShop.getMshopStatus());
        if(currentShop.getMshopStatus().equals("shop Opened")){
            statusTextView.setTextColor(Color.rgb(0,200,0));
        }else if(currentShop.getMshopStatus().equals("shop Closed")){
            statusTextView.setTextColor(Color.rgb(200,0,0));
        }

        TextView idTextView = listItemView.findViewById(R.id.LV_email);
        idTextView.setText(currentShop.getMshopid());

        TextView mobileTextView = listItemView.findViewById(R.id.LV_mobile);
        mobileTextView.setText(currentShop.getMshopmobile());

        TextView addressTextView = listItemView.findViewById(R.id.LV_address);
        addressTextView.setText(currentShop.getMshopaddress());

        LinearLayout LL = listItemView.findViewById(R.id.Profile_shown);
        LL.setVisibility(View.GONE);

        ImageView iv = listItemView.findViewById(R.id.details);
        iv.setOnClickListener(v -> {

            if(LL.getVisibility() == View.VISIBLE){
                LL.setVisibility(View.GONE);
            }else{
                LL.setVisibility(View.VISIBLE);
            }
        });

        ImageView iconView = listItemView.findViewById(R.id.list_view_image);
        //iconView.setImageResource(R.drawable.click);
        Glide.with(getContext()).load(currentShop.getImageResourceId()).centerCrop().into(iconView);
        iconView.setOnClickListener(v ->{
            Dialog settingsDialog = new Dialog(getContext());
            Objects.requireNonNull(settingsDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            //settingsDialog.setContentView(getApplicationContext().getLayoutInflater().inflate(R.layout.image_dialog, null));
            ImageView showImage = new ImageView(getContext());
            Glide.with(getContext()).load(currentShop.getImageResourceId()).centerCrop().into(showImage);

            //Uri uri = Uri.parse(currentShop.getImageResourceId());
            //iv.setImageURI(uri);

            //showImage.setImageURI(uri);
            final FrameLayout layout = new FrameLayout(getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layout.setLayoutParams(layoutParams);
            layout.addView(showImage);

            //showImage.setImageResource(R.drawable.arrow_icon);

            settingsDialog.setContentView(layout);
            //settingsDialog.setContentView(LayoutInflater.from(getContext()).inflate(R.layout.image_dialog, null));

            settingsDialog.show();

        });





        return listItemView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //return null;
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemsModelsl.size();
                    filterResults.values = itemsModelsl;
                }else{
                    ArrayList<Shops> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Shops itemsModel:itemsModelsl){

                        if(itemsModel.getMshopname().toLowerCase().contains(searchStr) || itemsModel.getMshopaddress().toLowerCase().contains(searchStr)  ){
                            //String name = itemsModel.getMshopname().toLowerCase();
                            //int startPos = name.indexOf(searchStr);
                            //int endPos = startPos + searchStr.length();
                            //Spannable spanText = Spannable.Factory.getInstance().newSpannable(itemsModel.getMshopname().toLowerCase());
                            //spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            //String ans= name.substring(0,startPos);// + name.substring(endPos);
                            //itemsModel.setMshopname(ans);
                                    //.setText(spanText, TextView.BufferType.SPANNABLE);



                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsModelListFiltered = (ArrayList<Shops>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }



}

