package com.example.e_commerce.Admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.ViewHolder.CartViewHolder;
import com.example.e_commerce.model.cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_user_products);
        userid=getIntent ().getStringExtra ("uid");
        productsList=findViewById (R.id.products_list);
        productsList.setHasFixedSize (true);
        layoutManager=new LinearLayoutManager (this);
        productsList.setLayoutManager (layoutManager);
        cartListRef= FirebaseDatabase.getInstance ().getReference ()
                .child ("Cart List").child ("Admin View").child (userid).child ("Products");
    }

    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseRecyclerOptions<cart>options=
                new FirebaseRecyclerOptions.Builder<cart> ()
                .setQuery (cartListRef,cart.class)
                .build ();
        FirebaseRecyclerAdapter<cart, CartViewHolder>adapter=new FirebaseRecyclerAdapter<cart, CartViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull cart model) {
                holder.txtProductQuantity.setText ("Quantity = "+model.getQuantity ());
                holder.txtProductPrice.setText ("Price = "+model.getPrice ()+"$");
                holder.txtProductName.setText ("Name = "+model.getPname ());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.cart_items_layout,viewGroup,false);
                CartViewHolder holder=new CartViewHolder (view);
                return holder;
            }
        };
        productsList.setAdapter (adapter);
        adapter.startListening ();
    }
}
