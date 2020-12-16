package com.example.ksinfo.Adapters;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ksinfo.Model.Item;
import com.example.ksinfo.R;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

class MyViewHolderWithoutChild extends RecyclerView.ViewHolder{

    public TextView textView;

    // Trying to add image
    public ImageView iconView;


    public MyViewHolderWithoutChild(@NonNull View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.textView);

        // Trying to add image
        iconView = (ImageView)itemView.findViewById(R.id.iconView);

    }
}


class MyViewHolderWithChild extends RecyclerView.ViewHolder{

    public TextView textView,textViewChild;
    public RelativeLayout button;
    public ExpandableLinearLayout expandableLayout;

    // Trying to add image
    public ImageView iconView;

    public MyViewHolderWithChild(@NonNull View itemView) {
        super(itemView);

        textView = (TextView)itemView.findViewById(R.id.textView);
        textViewChild = (TextView)itemView.findViewById(R.id.textViewChild);
        button = (RelativeLayout) itemView.findViewById(R.id.button);
        expandableLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandableLayout);

        // Trying to add image
        iconView = (ImageView)itemView.findViewById(R.id.iconView);

    }
}


public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Trying to add multiple children
    TextView viewTest1;
    TextView viewTest2;


    List<Item> items;
    Context context;
    SparseBooleanArray expandState = new SparseBooleanArray();



    public MyAdapter(List<Item> items) {
        this.items = items;

        for (int i = 0; i < items.size(); i++) {
            expandState.append(i,false);
        }



    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).isExpandable()){
            return 1;
        }else{
            return 0;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        // Trying to add multiple children
        viewTest1 = new TextView(context);
        viewTest2 = new TextView(context);



        if(viewType ==0){ // Without item
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_without_child,parent,false);
            return new MyViewHolderWithoutChild(view);
        }else{

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_with_child,parent,false);
            return new MyViewHolderWithChild(view);

        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        switch(holder.getItemViewType()){

            case 0:{
                MyViewHolderWithoutChild viewHolder = (MyViewHolderWithoutChild)holder;
                Item item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getText());


                //Trying to add image
                viewHolder.iconView.setImageResource(item.getDrawableID());


                if(item.getFunctionID() == 1){
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"noChildFunction1",Toast.LENGTH_LONG).show();
                        }
                    });
                }else if(item.getFunctionID() == 2){
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"noChildFunction2",Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
            break;
            case 1:{
                final MyViewHolderWithChild viewHolder = (MyViewHolderWithChild)holder;
                Item item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getText());

                viewHolder.expandableLayout.setInRecyclerView(true);


                if(item.getFunctionID() == 5){

                    final float scale = context.getResources().getDisplayMetrics().density;


                    //Первая кнопка
                    int pixels = (int) (400 * scale + 0.5f);
                    viewTest1.setWidth(pixels);
                    pixels = (int) (60 * scale + 0.5f);
                    viewTest1.setHeight(pixels);
                    viewTest1.setTextColor(context.getResources().getColor(R.color.KsWhite));
                    viewTest1.setBackgroundColor(context.getResources().getColor(R.color.KsBlue));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    pixels = (int) (40 * scale + 0.5f);
                    //lp.setMargins(pixels, 0, 0, 0);
                    //viewTest1.setLayoutParams(lp);
                    viewTest1.setText("Мои справки");
                    viewTest1.setTextSize(30);
                    viewTest1.setGravity(Gravity.CENTER_VERTICAL);
                    viewTest1.setPadding(pixels,0,0,0);


                    // Вторая кнопка
                    pixels = (int) (400 * scale + 0.5f);
                    viewTest2.setWidth(pixels);
                    pixels = (int) (60 * scale + 0.5f);
                    viewTest2.setHeight(pixels);
                    viewTest2.setTextColor(context.getResources().getColor(R.color.KsWhite));
                    viewTest2.setBackgroundColor(context.getResources().getColor(R.color.KsBlue));
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    pixels = (int) (40 * scale + 0.5f);
                    //lp.setMargins(pixels, 0, 0, 0);
                    //viewTest2.setLayoutParams(lp);
                    viewTest2.setText("Заказать справку");
                    viewTest2.setTextSize(30);
                    viewTest2.setGravity(Gravity.CENTER_VERTICAL);
                    viewTest2.setPadding(pixels,0,0,0);

                    viewHolder.textViewChild.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO переход на страницу с документами


                        }
                    });


                    viewTest1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO переход на страницу со справками

                        }
                    });

                    viewTest2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO переход на страницу с заказом справок

                        }
                    });


                    viewHolder.expandableLayout.addView(viewTest1);
                    viewHolder.expandableLayout.addView(viewTest2);

                }


                // Trying to add multiple children


                //Trying to add image
                viewHolder.iconView.setImageResource(item.getDrawableID());

                //Trying to add on click listener
                if(item.getFunctionID() == 1){
                    viewTest1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"function1Button1",Toast.LENGTH_SHORT).show();
                        }
                    });

                    viewTest2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"function1Button2",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else if(item.getFunctionID() == 2){
                    viewTest1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"function2Button1",Toast.LENGTH_SHORT).show();
                        }
                    });

                    viewTest2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"function2Button2",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



                viewHolder.expandableLayout.setExpanded(expandState.get(position));
                viewHolder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {



                    @Override
                    public void onPreOpen() {

                        changeRotate(viewHolder.button,0f,90f).start();
                        expandState.put(position,true);

                    }

                    @Override
                    public void onPreClose() {
                        changeRotate(viewHolder.button,90f,0f).start();
                        expandState.put(position,false);
                    }


                });


                viewHolder.button.setRotation(expandState.get(position) ? 90f:0f);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.expandableLayout.toggle();
                    }
                });

                viewHolder.textViewChild.setText(items.get(position).getSubText());



            }
            break;
            default:
                break;
        }

    }

    private ObjectAnimator changeRotate(RelativeLayout button, float from, float to) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(button,"rotation",from,to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
