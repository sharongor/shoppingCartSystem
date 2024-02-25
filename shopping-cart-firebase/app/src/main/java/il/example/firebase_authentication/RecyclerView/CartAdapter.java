package il.example.firebase_authentication.RecyclerView;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import il.example.firebase_authentication.R;
import il.example.firebase_authentication.data.DataCart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    static ArrayList<DataCart> dataset;
    static double amount;

    private OnAmountChangedListener listener;

    private Context context;

    public interface OnAmountChangedListener {
        void onAmountChanged(Double amount);
    }

    //private static ItemListener callback;
    public CartAdapter(ArrayList<DataCart> dataSet, Context context ,OnAmountChangedListener listener) {
        this.dataset = dataSet;
        this.amount = 0;
        this.listener = listener;
        this.context = context;
        //this.callback = callback;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageCart.setImageResource(dataset.get(position).getPicArray());
        holder.nameGame.setText(dataset.get(position).getNameArray());
        holder.priceText.setText(dataset.get(position).getPriceArray()+"$");
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageCart;
        TextView nameGame;
        TextView amountText;
        TextView priceText;

        ImageView plusButton;
        ImageView minusButton;

        FrameLayout minusButtonFrameLayOut;

        FrameLayout plusButtonFrameLayOut;



        public MyViewHolder(@NonNull View view) {
            super(view);
            imageCart = view.findViewById(R.id.image_cart);
            nameGame = view.findViewById(R.id.item_name_cart);
            amountText = view.findViewById(R.id.amount_item);
            priceText = view.findViewById(R.id.price_cart);
            plusButton = view.findViewById(R.id.plus_cart);
            minusButton = view.findViewById(R.id.minus_cart);
            minusButtonFrameLayOut = view.findViewById(R.id.minus_framelayout);
            plusButtonFrameLayOut = view.findViewById(R.id.plus_framelayout);


            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = Integer.parseInt(amountText.getText().toString());
                    currentQuantity++;
                    amountText.setText(String.valueOf(currentQuantity));
                    amount=amount + Double.parseDouble(dataset.get(getAdapterPosition()).priceArray);

                    // Apply the glow animation to the minus button's FrameLayout
                    Animation glowAnimation = AnimationUtils.loadAnimation(context, R.anim.glowing_effect);
                    plusButtonFrameLayOut.startAnimation(glowAnimation);

                    if(listener!=null){
                        listener.onAmountChanged(amount);
                    }
//                    notifyDataSetChanged();
                }
            });

            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current quantity
                    int currentQuantity = Integer.parseInt(amountText.getText().toString());
                    // Decrement the quantity
                    currentQuantity--;
                    // Ensure quantity doesn't go below 0
                    currentQuantity = Math.max(currentQuantity, 0);
                    // Update the quantity displayed
                    amountText.setText(String.valueOf(currentQuantity));
                   // Log.d("amount_x_minus_before",""+amount);

                    // Apply the glow animation to the minus button's FrameLayout
                    Animation glowAnimation = AnimationUtils.loadAnimation(context, R.anim.glowing_effect);
                    minusButtonFrameLayOut.startAnimation(glowAnimation);


                    amount=amount - (Double.parseDouble(dataset.get(getAdapterPosition()).priceArray));
                    amount = Math.max(amount,0);

//                    Log.d("amount_x_minus_after",""+amount);
//                    Log.d("amount_x_minus_adapter_position",""+dataset.get(getAdapterPosition()).priceArray);

                    if(listener!=null){
                        listener.onAmountChanged(amount);
                    }

//                    notifyDataSetChanged();
                }
            });
        }


    }
}
